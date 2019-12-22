package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.WindowState;

public class WindowBlindsActuator extends ComplexDevice {

	
	public WindowBlindsActuator(Long id, String name) {
		
		super(id, name);
		
	}


	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof WindowState)  {
			
			this.update( (WindowState)es );
			
		} 
	}
	

	private void update(WindowState window) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (window) {
			
			case OPEN:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
				
			case HALF_OPEN:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
			case CLOSE:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
				
			default:
				
				break;
			}
			
			this.getRoom().getHub().send(this, window );
		}
	}
}
