package pt.feup.ads.device;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.environment.state.EnvironmentState;

public interface Observable {
	
	EnvironmentState getEnvironmentState();
	
	Class<?> getEnvironmentStateClass();
	
	void setEnvironmentState(EnvironmentState es, CommandDevice command);
	
	void register(Observer observer);
	
	void unRegister(Observer observer);
	
	void notifyObservers(CommandDevice command);

}
