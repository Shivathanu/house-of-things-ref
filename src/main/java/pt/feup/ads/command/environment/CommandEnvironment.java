package pt.feup.ads.command.environment;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.hub.Hub;
import pt.feup.ads.environment.state.EnvironmentState;

public interface CommandEnvironment {
	
	public void execute(Hub hub, ComplexDevice device, EnvironmentState data);
}
