package pt.feup.ads.gui.layout;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.util.Identifiable;

class RightBaseLayout {
	
	private ComboBox<Identifiable> comboBoxRoom = new ComboBox<Identifiable>();
	private ComboBox<EnvironmentState> comboBoxTrigger = new ComboBox<EnvironmentState>();
	private ComboBox<DeviceState> comboBoxAction = new ComboBox<DeviceState>();
	private ComboBox<ComplexDevice> comboBoxActuator = new ComboBox<ComplexDevice>();
	
	private ListView<Rule> listRules = new ListView<Rule>();
	
	private Button buttonRegisterRule = new Button("Add Rule");
	private Button buttonClearAllRule = new Button("Clear All");
	
	
	RightBaseLayout() {
		
	}
	
	ListView<Rule> getListRules() {
		
		return listRules;
	}
	
	
	Button getButtonRegisterRule() {
		
		return buttonRegisterRule;
	}
	
	Button getButtonClearAllRule() {
		
		return buttonClearAllRule;
	}
	
	
	ComboBox<Identifiable> getComboBoxRoom() {
		
		return comboBoxRoom;
	}
	
	ComboBox<EnvironmentState> getComboBoxTrigger() {
		
		return comboBoxTrigger;
	}
	
	ComboBox<DeviceState> getComboBoxAction() {
		
		return comboBoxAction;
	}
	
	ComboBox<ComplexDevice> getComboBoxActuator() {
		
		return comboBoxActuator;
	}
	
	
	VBox build() {
		
		VBox vBox = new VBox();
    	vBox.setPadding(new Insets(10)); // Set all sides to 10  
        vBox.setSpacing(8);              // Gap between nodes
    	
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(3);

        
        //RULES
        Text eventText = new Text("Rules");
        eventText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        grid.add(eventText, 0, 0);
        
        
        //// Label and ComboBox Room
        Label lbRoom = new Label("Room:");
        grid.add(lbRoom, 0, 3);
        
        comboBoxRoom.setStyle("-fx-font: 12px \"Arial\";");
        comboBoxRoom.setPrefSize(280, 20);
        grid.add(comboBoxRoom, 2, 3);
        
        
        Label lbTrigger = new Label("Trigger:");
        grid.add(lbTrigger, 0, 4);
        
        Text whenText = new Text("WHEN");
        whenText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        GridPane.setHalignment(whenText, HPos.RIGHT);
        grid.add(whenText, 1, 4);
        
        comboBoxTrigger.setStyle("-fx-font: 12px \"Arial\";");
        comboBoxTrigger.setPrefSize(280, 20);
        //columnIndex, rowIndex, colspan, rowspan
        grid.add(comboBoxTrigger, 2, 4);
        
        Label lbAction = new Label("Action:");
        grid.add(lbAction, 0, 5);
        
        Label lbDevice = new Label("Actuator:");
        grid.add(lbDevice, 0, 6);
       
        Text thenText = new Text("THEN");
        thenText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        GridPane.setHalignment(thenText, HPos.RIGHT);

        grid.add(thenText, 1, 5);      
  
        
        comboBoxAction.setStyle("-fx-font: 12px \"Arial\";");
        comboBoxAction.setPrefSize(280, 20);
        //columnIndex, rowIndex, colspan, rowspan
        grid.add(comboBoxAction, 2, 5);

        
        comboBoxActuator.setStyle("-fx-font: 12px \"Arial\";");
        comboBoxActuator.setPrefSize(280, 20);
        //columnIndex, rowIndex, colspan, rowspan
        grid.add(comboBoxActuator, 2, 6);
        
        //VBox vBoxButton = new VBox();
        //vBoxButton.setSpacing(8);
        
        
        buttonRegisterRule.setPrefSize(75, 20);
        buttonRegisterRule.setDisable(true);
        
        buttonClearAllRule.setPrefSize(75, 20);
        buttonClearAllRule.setDisable(true);
        
        //vBoxButton.setAlignment(Pos.CENTER);
        //vBoxButton.getChildren().add(buttonRegisterRule);
        //vBoxButton.getChildren().add(buttonClearRule);
        //grid.add(vBoxButton, 3, 4, 1, 3);
        grid.add(buttonRegisterRule, 3, 4);
        grid.add(buttonClearAllRule, 3, 5);
        
       
        //REGISTERED RULES
        Text regRuleText = new Text("Registered Rules");
        regRuleText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        //columnIndex, rowIndex, colspan, rowspan
        grid.add(regRuleText, 0, 14, 3, 1);
        

        listRules.setPrefWidth(120);
        listRules.setPrefHeight(340);
        listRules.setStyle("-fx-font: 12px \"Arial\";");
        
        //columnIndex, rowIndex, colspan, rowspan
        grid.add(listRules, 0, 15, 4, 1);
        
        //VBox vBoxButtons = new VBox();
        //vBoxButtons.setPadding(new Insets(1)); // Set all sides to 10
        //vBoxButtons.setSpacing(8);              // Gap between nodes

        
        //vBoxButtons.getChildren().add(buttonClearRule);
        //grid.add(vBoxButtons, 3, 15);
        
        /*Button buttonStepByStep = new Button("Step by Step");
        buttonStepByStep.setStyle("-fx-text-fill: #008000;");
        buttonStepByStep.setPrefSize(100, 20);
        
        Button buttonSimulate = new Button("Simulate All");
        buttonSimulate.setStyle("-fx-text-fill: #336699;");
        buttonSimulate.setPrefSize(100, 20);
        
        vBoxButtons.getChildren().addAll(buttonClearRule, buttonStepByStep, buttonSimulate);
        */
        
        //grid.setGridLinesVisible(true);

        vBox.getChildren().add( grid );
        
        return vBox;
	}
}
