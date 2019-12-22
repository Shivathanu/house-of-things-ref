package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.SmokeState;

public class SprinklerActuator extends ComplexDevice {

	
	public SprinklerActuator(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof SmokeState)  {
			
			this.update( (SmokeState)es );
			
		} 
		
	}
	
	private void update(SmokeState smoke) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (smoke) {
			case HAS_SMOKE:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case HAS_NOT_SMOKE:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, smoke );
		}
	}
}
