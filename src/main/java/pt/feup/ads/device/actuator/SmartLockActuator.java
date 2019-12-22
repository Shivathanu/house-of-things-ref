package pt.feup.ads.device.actuator;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;


public class SmartLockActuator extends ComplexDevice {

	
	public SmartLockActuator(Long id, String name) {
		
		super(id, name);
		
	}


	@Override
	public void update(EnvironmentState es, CommandDevice command) {
		
		command.execute(this);
		
		if (es instanceof DoorState)  {
			
			this.update( (DoorState)es );
			
		} 
	}
	

	private void update(DoorState door) {
		
		if (!this.getDeviceState().equals( DeviceState.OFF )) {
			
			switch (door) {
			case OPEN:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;
			case HALF_OPEN:
				
				this.setDeviceState(DeviceState.STAND_BY);
				
				break;	
			case CLOSE:
				
				this.setDeviceState(DeviceState.ON);
				
				break;
			default:
				break;
			}
			
			this.getRoom().getHub().send(this, door );
		}
	}
}
