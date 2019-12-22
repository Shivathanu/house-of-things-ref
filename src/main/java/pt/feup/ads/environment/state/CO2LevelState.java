package pt.feup.ads.environment.state;

import pt.feup.ads.device.Device;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.rule.Rule;

public enum CO2LevelState implements EnvironmentState {
	
	//PPM = parts per million
	//https://www.kane.co.uk/knowledge-centre/what-are-safe-levels-of-co-and-co2-in-rooms
	
	VERY_FRESH_AIR,			//BELOW_250_PPM
	FRESH_AIR, 				//BETWEEN_250_AND_400_PPM
	LIVEABLE_AIR, 			//BETWEEN_400_AND_1000_PPM
	POOR_AIR, 				//BETWEEN_1000_AND_2000_PPM
	VERY_POOR_AIR, 			//BETWEEN_2000_AND_5000_PPM
	EXTREME_POOR_AIR;		//ABOVE_5000_PPM

	
	@Override
	public void doAction(Rule rule) {
		
		for (Device simpleDevice : rule.getRuleActuator().getObjectList()) {
			
			((SimpleDevice)simpleDevice).setEnvironmentState(this, rule.getRuleCommandDevice());
		}
	}
	
	@Override
	public String toString() {
		
		return "CO2 Level = " + super.toString();
	}
	
	@Override
	public String getName() {
		
		return super.toString();
	}
}
