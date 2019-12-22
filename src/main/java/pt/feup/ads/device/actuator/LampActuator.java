package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;


public class LampActuator extends ComplexDevice {
	
	
	public LampActuator(Long id, String name) {
		
		super(id, name);
	}
	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof MotionState)  {
			
			this.update( (MotionState)es );
			
		} else if (es instanceof LightState) {
			
			this.update( (LightState)es );
			
		}
	}
	
	
	private void update(MotionState motion) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (motion) {
			case MOVING:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case MOVELESS:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, motion);
		}
	}


	private void update(LightState light) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (light) {
			case DARK:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case LIGHTED:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			default:
				break;
			}
			
			
			this.getRoom().getHub().send(this, light);
		}
	}
}
