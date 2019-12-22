package pt.feup.ads.command.device;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;

public class TurnOffCommand implements CommandDevice {

	@Override
	public void execute(ComplexDevice device) {
		
		if (!device.getDeviceState().equals(DeviceState.OFF)) {
			
			device.setDeviceState(DeviceState.OFF);
		}
	}
}
