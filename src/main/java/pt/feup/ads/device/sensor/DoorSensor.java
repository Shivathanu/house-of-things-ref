package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;

public class DoorSensor extends SimpleDevice implements Observable {

	
	private DoorState doorState;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	public DoorSensor(Long id, String name) {
		
		super(id, name);
	}

	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof DoorState) {
				
			this.doorState = (DoorState)es;
			
			this.notifyObservers(command);	
		}
	}
	
	
	@Override
	public DoorState getEnvironmentState() {
		
		return doorState;
	}
	
	
	@Override
	public void register(Observer observer) {
		
		this.observers.add( observer);
	}

	
	@Override
	public void unRegister(Observer observer) {
		
		this.observers.remove( observer);
	}

	
	@Override
	public void notifyObservers(CommandDevice command) {
		
		for (Observer observer : observers) {
			
			observer.update(this.doorState, command);
		}
	}
	
	
	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return DoorState.class;
	}
}
