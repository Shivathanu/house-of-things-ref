package pt.feup.ads.device;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.factory.HouseFactory;
import pt.feup.ads.factory.JSONConst;
import pt.feup.ads.protocol.Protocol;
import pt.feup.ads.util.LoopConditionCheck;

public abstract class ComplexDevice extends Device implements Observer {
	
	private DeviceState deviceState;
	
	private List<Device> devices = new ArrayList<Device>();
	
	private List<Observable> sensors;
	
	private Protocol protocol;
	
	private Long idRoomPart;
	
	protected ComplexDevice(Long id, String name) {
		
		super(id, name);
	}
	
	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
	
	@Override
	public void registerObservables(List<Observable> sensors) {
		
		this.sensors = sensors;
		
		for (Observable observable : this.sensors) {
			
			observable.register(this);
		}
	}
	
	
	public void add(Device device) {
		
		devices.add(device);
	}
	
	
	public void remove(Device device) {
		
		devices.remove(device);
	}
	
	
	public Device getChild(Integer index) {
		
		return devices.get(index);
	}
	
	
	public DeviceState getDeviceState() {
		
		return deviceState;
	}
	
	
	public void setDeviceState(DeviceState deviceState) {
			
		
		//Checking if rules are in a Loop Condition
		if (HouseFactory.getLoadedHouse() != null && 
			HouseFactory.getLoadedHouse().getRules() != null && 
			HouseFactory.getLoadedHouse().getRules().size() > 0) {
			
			LoopConditionCheck.value++;
			
			if ( (LoopConditionCheck.value) > HouseFactory.getLoadedHouse().getRules().size()) {
				
				System.out.println("Registered Rules are in a loop condition! The update process was stopped.");
					
				return;		
			} 
		}

		
		if (this.getDeviceState() == null) {
			
			this.deviceState = deviceState;
			
		} else {
			
			if (!this.getDeviceState().equals(deviceState)) {
				
				switch (deviceState) {
				
				case ON:
					
					this.turnOn();
					
					break;
					
				case OFF:
					
					this.turnOff();
					
					break;
					
				case STAND_BY:
					
					this.standBy();
					
					break;
					
				default:
					break;
				}
			}
			
			
			//Checking if the new deviceState is in the list of Registered Rules
			if (HouseFactory.getLoadedHouse() != null && HouseFactory.getLoadedHouse().getRules() != null) {
				
				for (Rule rule : HouseFactory.getLoadedHouse().getRules() ) {
					
					rule.dispatch();
					
				}
			}	
		}
	}
	
	
	private void printMessage(String msgState) {
		
		System.out.println(msgState + " actuator " + this.getName() + "(id=" + this.getId() + ")");
	}
	
	private void turnOn() {
		
		this.deviceState = DeviceState.ON;
		
		this.printMessage("Turning ON");
	}
	
	
	private void turnOff() {
		
		this.deviceState = DeviceState.OFF;
		
		this.printMessage("Turning OFF");
	}
	
	
	private void standBy() {
		
		this.deviceState = DeviceState.STAND_BY;
		
		this.printMessage("Standing By");
	}
	

	@Override
	public List<Device> getObjectList() {
		
		return devices;
	}
	
	@Override
	public String getImage() {
		
		return "actuator.png";
	}
	
	@Override
	public String toString() {
		
		return this.getName();

	}
	
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{type: " + this.getClass() + 
				", id: " + this.getId() + 
				", name: " + this.getName() +  
				"}\n");
		
		for (Device device : devices) {
			
			sb.append("\n\t\t\t" + device.toFullString() + "\n");
		} 
		
		return sb.toString();

	}
	
	@Override
	public String getJSONProperties() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n");
		sb.append("\t\"" + JSONConst.ID + "\": \"" + this.getId() + "\",\n");
		sb.append("\t\"" + JSONConst.NAME + "\": \"" + this.getName() + "\",\n");
		sb.append("\t\"" + JSONConst.TYPE + "\": \"" + this.getClass().getCanonicalName() + "\",\n");
		sb.append("\t\"" + JSONConst.PROTOCOL + "\": \"" + this.getProtocol().getClass().getCanonicalName() + "\",\n");
		sb.append("\t\"" + JSONConst.DEVICE_STATE + "\": \"" + this.getDeviceState() + "\"\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		this.setDeviceState( DeviceState.valueOf( obj.getString(JSONConst.DEVICE_STATE) ) );
	}

	public Long getIdRoomPart() {
		return idRoomPart;
	}

	public void setIdRoomPart(Long idRoomPart) {
		this.idRoomPart = idRoomPart;
	}
}
