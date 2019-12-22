package pt.feup.ads.environment.rule;


import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.command.device.StandByCommand;
import pt.feup.ads.command.device.TurnOffCommand;
import pt.feup.ads.command.device.TurnOnCommand;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.TimeState;

public class Rule {
	
	private Long id;
	private Room room;
	private EnvironmentState ruleEnvironmentState;
	private DeviceState ruleActuatorState;
	private ComplexDevice ruleActuator;
	

	
	public Rule(Long id, Room room, EnvironmentState ruleEnvironmentState, DeviceState ruleActuatorState, ComplexDevice ruleActuator) {
		
		this.id = id;
		this.room = room;
		this.ruleEnvironmentState = ruleEnvironmentState;
		this.ruleActuatorState = ruleActuatorState;	
		this.ruleActuator = ruleActuator;
		
		this.dispatch();
		
	}
	
	public void dispatch() {
		
		
		if (ruleEnvironmentState instanceof TimeState) {
			
			if (ruleEnvironmentState.equals( room.getHouse().getTimeState() ) ) {
				
				this.executeCommand();
			}
		}
		
		for (EnvironmentState environmentState : room.getAllEnvironmentStateRoom() ) {
			
			if (ruleEnvironmentState.equals(environmentState)) {
				
				this.executeCommand();
			}
		}
	}

	
	private void executeCommand() {
		
		if (!ruleActuatorState.equals(ruleActuator.getDeviceState())) {
		
			switch(ruleActuatorState) {
			
			case OFF:
				
				new TurnOffCommand().execute(ruleActuator);
				
				break;
			case ON:
				
				new TurnOnCommand().execute(ruleActuator);
				
				break;
			case STAND_BY:
				
				new StandByCommand().execute(ruleActuator);
				
				break;
			default:
				break;
			}
		}
	}
	

	public CommandDevice getRuleCommandDevice() {
		
		switch(ruleActuatorState) {
		
		case OFF: return new TurnOffCommand();
		case ON: return new TurnOnCommand();
		case STAND_BY: return new StandByCommand();
		default:
			break;
		}
		
		return null;
	}
	
	
	public Long getId() {
		return id;
	}


	public ComplexDevice getRuleActuator() {
		return ruleActuator;
	}

	
	public Object getRuleEnvironmentState() {
		
		return ruleEnvironmentState;
	}
	
	public Room getRoom() {
		
		return room;
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(room);
		sb.append(": when " + ruleEnvironmentState);
		
		if (ruleActuatorState.equals(DeviceState.STAND_BY)) {
			
			sb.append(" then " + ruleActuatorState);
			
		} else {
			
			sb.append(" then TURN " + ruleActuatorState);
		}
		
		
		sb.append(" the " + ruleActuator);
		
		return sb.toString();
	}
}
