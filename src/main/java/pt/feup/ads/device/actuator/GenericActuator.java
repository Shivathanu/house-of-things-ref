package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.environment.state.EnvironmentState;

public class GenericActuator extends ComplexDevice {

	
	public GenericActuator(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		//It's a GenericActuator. It does not affect the environment directly by a specific behavior, only for a system rule.	
	}
}
