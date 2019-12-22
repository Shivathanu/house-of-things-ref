package pt.feup.ads.environment.state;


import pt.feup.ads.device.Device;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.rule.Rule;

public enum TemperatureState implements EnvironmentState {
	
	FREEZING_COLD,  //	--5
	COLD,			//	 -4 to +10
	MILD,			//	+11 to +25 
	HEAT,			//	+26 to +35
	SIZZLING_HEAT;	// ++36
	
	
	@Override
	public void doAction(Rule rule) {
		
		for (Device simpleDevice : rule.getRuleActuator().getObjectList()) {
			
			((SimpleDevice)simpleDevice).setEnvironmentState(this, rule.getRuleCommandDevice());
		}
	}
	
	@Override
	public String toString() {
		
		return "Temperature = " + super.toString();
	}
	
	
	@Override
	public String getName() {
		
		return super.toString();
	}
}
