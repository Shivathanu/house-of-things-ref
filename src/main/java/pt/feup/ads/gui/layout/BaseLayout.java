package pt.feup.ads.gui.layout;


import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.Device;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.House;
import pt.feup.ads.environment.room.EnvironmentRoom;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.ContactState;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.InfraRedState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.SmokeState;
import pt.feup.ads.environment.state.TemperatureState;
import pt.feup.ads.environment.state.TimeState;
import pt.feup.ads.environment.state.WindowState;
import pt.feup.ads.factory.HouseFactory;
import pt.feup.ads.gui.data.HoTTreeView;
import pt.feup.ads.util.Id;
import pt.feup.ads.util.Identifiable;
import pt.feup.ads.util.LoopConditionCheck;


public class BaseLayout {
	
	private TopBaseLayout topBaseLayout = new TopBaseLayout();
	private RightBaseLayout rightBaseLayout = new RightBaseLayout();
	private CenterBaseLayout centerBaseLayout = new CenterBaseLayout();
	private LeftBaseLayout leftBaseLayout = new LeftBaseLayout();
	private BottomBaseLayout bottomBaseLayout = new BottomBaseLayout();
	
	private ObservableList<Identifiable> rooms = FXCollections.observableArrayList();
	private ObservableList<EnvironmentState> triggers = FXCollections.observableArrayList();
	private ObservableList<DeviceState> actions = FXCollections.observableArrayList();
	private ObservableList<ComplexDevice> actuators = FXCollections.observableArrayList();
	
	private ObservableList<Rule> rules = FXCollections.observableArrayList();
	
	
	private Identifiable selectedItem;
	private Identifiable selectedRoom;
	private EnvironmentState selectedEnvironmentState;
	private DeviceState selectedDeviceState;
	private ComplexDevice selectedActuator;
	
	
	private Stage stage;
	
	
	public void setStage(Stage stage) {
		
		this.stage = stage;
	}
	
	
	public BaseLayout() {
		
		leftBaseLayout.getTreeView().getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> this.updateSelectedTreeItem(newValue.getValue()) );

    	rightBaseLayout.getComboBoxRoom().setItems( rooms );
    	rightBaseLayout.getComboBoxRoom().getSelectionModel().selectedItemProperty().addListener( 
    	        (options, oldValue, newValue) -> this.updateSelectedComboBoxRoom(newValue) );
    	
    	rightBaseLayout.getComboBoxTrigger().setItems( triggers );
    	rightBaseLayout.getComboBoxTrigger().getSelectionModel().selectedItemProperty().addListener( 
    	        (options, oldValue, newValue) -> this.updateSelectedComboBoxTrigger(newValue) );

    	rightBaseLayout.getComboBoxAction().setItems( actions );
    	rightBaseLayout.getComboBoxAction().getSelectionModel().selectedItemProperty().addListener( 
    	        (options, oldValue, newValue) -> this.updateSelectedComboBoxAction(newValue) );
    	
    	rightBaseLayout.getComboBoxActuator().setItems( actuators );
    	rightBaseLayout.getComboBoxActuator().getSelectionModel().selectedItemProperty().addListener( 
    	        (options, oldValue, newValue) -> this.updateSelectedComboBoxActuator(newValue) );
    	
    	rightBaseLayout.getListRules().setItems( rules );
    	rightBaseLayout.getButtonRegisterRule().setOnAction(
    			value -> this.updateRegisteredRules());
    	
    	rightBaseLayout.getButtonClearAllRule().setOnAction(
    			value -> rules.clear());
    	
    	topBaseLayout.getButtonOpenProject().setOnAction(
     			value -> this.openFileChooser(stage));
    	
    	centerBaseLayout.getButtonSave().setOnAction(
     			value -> this.updateIdentifiableAttributes());
	}
	
	
	private void updateIdentifiableAttributes() {
		
		String textProperties = centerBaseLayout.getTextArea().getText();

		if (selectedItem != null) {
			
			LoopConditionCheck.value = 0;//Flag Control to manage the situation when the registered rules are in a loop condition.
			
			selectedItem.updateIdentifiableAttributesJSON(textProperties);
			
			//this.updateSelectedTreeItem(selectedItem);
		}
	}


	private void openFileChooser(Stage stage) {
		
		File selectedFile = topBaseLayout.getFileChooser().showOpenDialog(stage);
		
		if (selectedFile != null) {
			
			System.out.println("Project file location: " + selectedFile.getAbsolutePath());
			
			House house = HouseFactory.getHouseFromFile( selectedFile );
			
			house.setRules(rules);
			
			rooms.addAll( house.getObjectList() );

			HoTTreeView hoTTreeView = new HoTTreeView();
			hoTTreeView.fillHouseTree(leftBaseLayout.getTreeView(), house);
			
			
			if (leftBaseLayout.getTreeView().getRoot() != null) {
				
	    		rightBaseLayout.getButtonClearAllRule().setDisable(false);
	    		rightBaseLayout.getButtonRegisterRule().setDisable(false);
	    	}
		}
		
	}
	
	public TextArea getLogTextArea() {
		
		return bottomBaseLayout.getTextArea();
	}

	
	private void updateRegisteredRules() {
		
		if (selectedActuator != null) {
			
			Rule rule = new Rule(Id.getNextId(), (Room)selectedRoom, selectedEnvironmentState, selectedDeviceState, selectedActuator);
			
			rules.add(rule);
			
			if (selectedItem != null) {
				
				this.updateSelectedTreeItem(selectedItem);
			}
		}
	}
	
	private void updateSelectedComboBoxRoom(Identifiable newValue) {
    	
    	selectedRoom = newValue;
    	
    	this.fillTriggers();

    	
    }
	

	private void updateSelectedComboBoxTrigger(EnvironmentState newValue) {
		
		selectedEnvironmentState = newValue;
		
		this.fillActions();
		
	}
	
    
	private void updateSelectedComboBoxAction(DeviceState newValue) {
		
		selectedDeviceState = newValue;
		
		this.fillActuators();
		
    }
	
	private void updateSelectedComboBoxActuator(ComplexDevice newValue) {
		
		selectedActuator = newValue;
		
	}
    
    
    private void updateSelectedTreeItem(Identifiable newValue) {
    	
    	selectedItem = newValue;
    	
    	LoopConditionCheck.value = 0; //Flag Control to manage the situation when the registered rules are in a loop condition.
    	
    	centerBaseLayout.getTextArea().setText( selectedItem.getJSONProperties() );
    	
    	centerBaseLayout.getTextArea().setEditable(true);
		centerBaseLayout.getButtonSave().setDisable(false);
    }

    
    private void fillActuators() {
		
		actuators.clear();
		
		if (selectedRoom != null ) {
				
			EnvironmentRoom er = EnvironmentRoom.getInstance((Room)selectedRoom);
			
			this.fillActuatorsRoom(er);
		}
	}

    
	private void fillActuatorsRoom(EnvironmentRoom er) {
		
		List<ComplexDevice> actuatorsRoom = er.getRoomActuators();
		
		for (ComplexDevice actuator : actuatorsRoom) {
			
			
			for (Device device : actuator.getObjectList()) {
				
				SimpleDevice sensor = (SimpleDevice)device; 
				
				//checking if actuator has a sensor compatible with environment state selected 
				//TimeState admit that all actuators can be selected.
				if (selectedEnvironmentState != null) {
					
					if (sensor.getEnvironmentStateClass().equals( selectedEnvironmentState.getClass() ) ||
							selectedEnvironmentState.getClass().equals(TimeState.class)) {
						
						actuators.add( actuator );
						
						break;
					}
				}
			}
		}
	}
    
    
    private void fillActions() {
		
		actions.clear();
				
		for (DeviceState deviceState : DeviceState.values()) actions.add(deviceState);
	}
    
    
    private void fillTriggers() {
		
		triggers.clear();
    	
    	for (TimeState timeState : TimeState.values()) triggers.add(timeState);
    	for (CO2LevelState cO2LevelState : CO2LevelState.values()) triggers.add(cO2LevelState);
    	for (InfraRedState infraRedState : InfraRedState.values()) triggers.add(infraRedState);
    	for (LightState lightState : LightState.values()) triggers.add(lightState);
    	for (MotionState motionState : MotionState.values()) triggers.add(motionState);
    	for (SmokeState smokeState : SmokeState.values()) triggers.add(smokeState);
    	for (TemperatureState temperatureState : TemperatureState.values()) triggers.add(temperatureState);
    	for (ContactState contactState : ContactState.values()) triggers.add(contactState);
    	for (DoorState doorState : DoorState.values()) triggers.add(doorState);
    	for (WindowState windowState : WindowState.values()) triggers.add(windowState);
	}
    
    
    public HBox buildTOP() {
    	
    	return topBaseLayout.build(stage);
    }

    
    public VBox buidLEFT() {
        
        return leftBaseLayout.build();
    }
    
    public VBox buidCENTER() {
    	
    	return centerBaseLayout.build();
    }
    
    
    public VBox buidRIGHT() {
    	
    	return rightBaseLayout.build();
    }

    
    public VBox buidBOTTOM() {
    	
    	return bottomBaseLayout.build();
    }
}

