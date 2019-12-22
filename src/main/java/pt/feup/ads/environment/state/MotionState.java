package pt.feup.ads.environment.state;


import pt.feup.ads.device.Device;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.rule.Rule;

public enum MotionState implements EnvironmentState {

	MOVING,
	MOVELESS;
	
	@Override
	public void doAction(Rule rule) {
		
		for (Device simpleDevice : rule.getRuleActuator().getObjectList()) {
			
			((SimpleDevice)simpleDevice).setEnvironmentState(this, rule.getRuleCommandDevice());
		}
	}
	
	@Override
	public String toString() {
		
		return "Motion = " + super.toString();
	}
	
	
	@Override
	public String getName() {
		
		return super.toString();
	}
	
}
