package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.TemperatureState;

public class HeaterActuator extends ComplexDevice {

	
	public HeaterActuator(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		//In case to add a particular behavior for this actuator 
		if (es instanceof TemperatureState)  {
			
			this.update( (TemperatureState)es);
			
		} 
		
	}
	
	private void update(TemperatureState temperature) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (temperature) {
			case FREEZING_COLD:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case COLD:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
			case MILD:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
			case HEAT:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			case SIZZLING_HEAT:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, temperature );
		}
	}
}
