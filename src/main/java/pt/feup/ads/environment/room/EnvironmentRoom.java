package pt.feup.ads.environment.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.Device;
import pt.feup.ads.environment.room.part.RoomPart;
import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.InfraRedState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.SmokeState;
import pt.feup.ads.environment.state.TemperatureState;
import pt.feup.ads.factory.JSONConst;

/**
 * 
 * Singleton class in a HashMap
 *
 */
public class EnvironmentRoom {

	private static Map<Long, EnvironmentRoom> erList = new HashMap<Long, EnvironmentRoom>();
	
	private TemperatureState temperature;
	private LightState light;
	private MotionState motion;
	private SmokeState smoke;
	private CO2LevelState cO2Level;
	private InfraRedState infraRed;
	
	private Room room;
	
	private EnvironmentRoom(Room room) {
		
		this.room = room;
	}
	
	
	public static EnvironmentRoom getInstance(Room room) {
		
		if (erList.get(room.getId()) == null) {
			
			erList.put(room.getId(), new EnvironmentRoom(room));
		}
		
		return erList.get(room.getId());
	}
	
	
	public Room getRoom() {
		
		return room;
	}
	
	
	private void printMessage(String jSonConst, String oldEnvStateName, String newEnvStateName, String roomName) {
		
		System.out.println("Changing " + jSonConst + 
				" from " + oldEnvStateName + 
				" to " + newEnvStateName +
				" in " + roomName );
	}
	
	public TemperatureState getTemperature() {
		
		return temperature;
	}

	
	public void setTemperatureState(TemperatureState newTemperatureState) {
		
		if (this.getTemperature() != null && !this.getTemperature().equals(newTemperatureState)) {
			
			this.printMessage(JSONConst.TEMPERATURE_STATE, this.getTemperature().getName(), newTemperatureState.getName(), room.getName());
		}
		
		this.temperature = newTemperatureState;
		
		
		this.processRules(newTemperatureState);
	}

	
	public LightState getLight() {
		
		return light;
	}

	
	public void setLightState(LightState newLightState) {
		
		if (this.getLight() != null && !this.getLight().equals(newLightState)) {
			
			this.printMessage(JSONConst.LIGHT_STATE, this.getLight().getName(), newLightState.getName() , room.getName());
		}
		
		this.light = newLightState;
		
		this.processRules(newLightState);
	}

	
	public MotionState getMotion() {
		
		return motion;
	}

	
	public void setMotionState(MotionState newMotionState) {
		
		if (this.getMotion() != null && !this.getMotion().equals(newMotionState)) {
			
			this.printMessage(JSONConst.MOTION_STATE, this.getMotion().getName(), newMotionState.getName() , room.getName());
		}
		
		this.motion = newMotionState;
		
		this.processRules(newMotionState);
	}

	
	public SmokeState getSmoke() {
		
		return smoke;
	}

	
	public void setSmokeState(SmokeState newSmokeState) {
		
		if (this.getSmoke() != null && !this.getSmoke().equals(newSmokeState)) {
			
			this.printMessage(JSONConst.SMOKE_STATE, this.getSmoke().getName(), newSmokeState.getName(), room.getName());
		}
		
		this.smoke = newSmokeState;
		
		this.processRules(newSmokeState);
		
	}

	
	public CO2LevelState getcO2Level() {
		
		return cO2Level;
	}

	
	public void setcO2LevelState(CO2LevelState newCO2Level) {
		
		if (this.getcO2Level() != null && !this.getcO2Level().equals(newCO2Level)) {
			
			this.printMessage(JSONConst.CO2_LEVEL_STATE, this.getcO2Level().getName(), newCO2Level.getName(), room.getName());
		}
		
		this.cO2Level = newCO2Level;
		
		this.processRules(newCO2Level);
	}


	public InfraRedState getInfraRed() {
		
		return infraRed;
	}


	public void setInfraRedState(InfraRedState newInfraRedState) {
		
		if (this.getInfraRed() != null && !this.getInfraRed().equals(newInfraRedState)) {
		
			this.printMessage(JSONConst.INFRA_RED_STATE, this.getInfraRed().getName(), newInfraRedState.getName(), room.getName());
		}
		
		this.infraRed = newInfraRedState;
		
		this.processRules(newInfraRedState);
	}

	
	private void processRules(EnvironmentState newEnvironmentState) {
		
		if (room.getHouse().getRules() != null) {
			
			for (Rule rule : room.getHouse().getRules() ) {
				
				if (rule.getRuleEnvironmentState().equals(newEnvironmentState)) {

					newEnvironmentState.doAction(rule);
					
					rule.dispatch();
				}
			}
		}
	}
	
	
	
	public List<ComplexDevice> getRoomActuators() {
		
		List<ComplexDevice> allActuators = new ArrayList<ComplexDevice>();
		
		List<Device> actuators = room.getObjectList();
		
		for (Device actuator : actuators) {
			
			if (actuator instanceof ComplexDevice) {
				
				allActuators.add((ComplexDevice)actuator);
			}
		}
		
		this.addRoomPartsActuators(allActuators);
		
		return allActuators;
	}
	
	
	private void addRoomPartsActuators(List<ComplexDevice> allActuators) {
		
		List<RoomPart> roomParts = room.getRoomParts();
		
		for (RoomPart roomPart : roomParts) {
			
			for (Device actuator : roomPart.getObjectList() ) {
				
				if (actuator instanceof ComplexDevice) {
					
					allActuators.add((ComplexDevice)actuator);
				}
			}	
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n\ttemperature: " + temperature + ",\n" +
				"\tlight: " + light + ",\n" +
				"\tmotion: " + motion + ",\n" + 
				"\tsmoke: " + smoke + ",\n" +
				"\tcO2Level: " + cO2Level + ",\n" +
				"\tinfraRed: " + infraRed + "\n");
		
		return sb.toString();
				
	}
	
	public String toFullString() {
		
		return toString();
	}
}

