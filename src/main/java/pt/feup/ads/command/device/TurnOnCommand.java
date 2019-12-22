package pt.feup.ads.command.device;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;

public class TurnOnCommand implements CommandDevice {

	@Override
	public void execute(ComplexDevice device) {
		
		if (!device.getDeviceState().equals(DeviceState.ON)) {
			
			device.setDeviceState(DeviceState.ON);
		}
	}
}
