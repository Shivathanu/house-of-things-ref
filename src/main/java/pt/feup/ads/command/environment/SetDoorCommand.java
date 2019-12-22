package pt.feup.ads.command.environment;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.hub.Hub;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;

public class SetDoorCommand implements CommandEnvironment {
	
	
	@Override
	public void execute(Hub hub, ComplexDevice device, EnvironmentState data) {
		
		if (!hub.getProtocol().getName().equals( device.getProtocol().getName() ) ) {
			
			hub.addMessageLog(device);
			
		} else {
			
			hub.getRoom().getRoomPart( device.getIdRoomPart() ).setEnvironmentState( (DoorState)data);
		}
	}
}