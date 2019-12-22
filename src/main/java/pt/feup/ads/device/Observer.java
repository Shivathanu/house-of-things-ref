package pt.feup.ads.device;

import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.environment.state.EnvironmentState;

public interface Observer {
	
	void update(EnvironmentState es, CommandDevice commandDevice);
	
	void registerObservables(List<Observable> sensors);
}
