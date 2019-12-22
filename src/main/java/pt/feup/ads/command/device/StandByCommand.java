package pt.feup.ads.command.device;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;

public class StandByCommand implements CommandDevice {

	@Override
	public void execute(ComplexDevice device) {
		
		if (!device.getDeviceState().equals(DeviceState.STAND_BY)) {
			
			device.setDeviceState(DeviceState.STAND_BY);
		}
	}
}
