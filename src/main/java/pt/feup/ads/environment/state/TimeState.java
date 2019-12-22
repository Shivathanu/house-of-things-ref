package pt.feup.ads.environment.state;


import pt.feup.ads.device.Device;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.rule.Rule;

public enum TimeState implements EnvironmentState {
	
	SUNRISE,
	SUNSET,
	MIDDAY,	
	MORNING,
	EVENING,
	NIGHT,
	MIDNIGHT,
	DAWN;
	
	
	@Override
	public void doAction(Rule rule) {
		
		for (Device simpleDevice : rule.getRuleActuator().getObjectList()) {
			
			((SimpleDevice)simpleDevice).setEnvironmentState(this, rule.getRuleCommandDevice());
		}
	}
	
	@Override
	public String toString() {
		
		return "Time = " + super.toString();
	}
	
	
	@Override
	public String getName() {
		
		return super.toString();
	}

}
