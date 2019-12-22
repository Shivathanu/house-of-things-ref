package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.ContactState;
import pt.feup.ads.environment.state.EnvironmentState;

public class CoffeeMachineActuator extends ComplexDevice {

	
	public CoffeeMachineActuator(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof ContactState)  {
			
			this.update( (ContactState)es );
			
		} 
	}
	
	
	private void update(ContactState contact) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (contact) {
			case TOUCH:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case UNTOUCH:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, contact );
		}
	}
}
