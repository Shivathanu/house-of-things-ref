package pt.feup.ads.device.sensor;


import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.WindowState;

public class WindowSensor extends SimpleDevice  {

	
	private WindowState windowsState;
	
	public WindowSensor(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof WindowState) {
				
			this.windowsState = (WindowState)es;
			
			this.notifyObservers(command);
		}
	}
	
	
	@Override
	public WindowState getEnvironmentState() {
		
		return windowsState;
	}
	
	
	@Override
	public void notifyObservers(CommandDevice command) {
		
		for (Observer observer : observers) {
			
			observer.update(this.windowsState, command);
		}
	}
	

	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return WindowState.class;
	}
}
