package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.TemperatureState;

public class FanActuator extends ComplexDevice {
	
	
	public FanActuator(Long id, String name) {
		
		super(id, name);
	}
	
	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof MotionState)  {
			
			this.update( (MotionState)es );
			
		} else if (es instanceof TemperatureState) {
			
			this.update( (TemperatureState)es );
			
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

	private void update(TemperatureState temperature) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (temperature) {
			case FREEZING_COLD:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
				
			case COLD:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
				
			case MILD:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
				
			case HEAT:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case SIZZLING_HEAT:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, temperature);
		}
	}
}
