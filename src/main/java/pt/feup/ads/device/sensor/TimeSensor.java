package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.TimeState;

public class TimeSensor extends SimpleDevice implements Observable {

	private TimeState time;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	
	public TimeSensor(Long id, String name) {
		
		super(id, name);	
	}

	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof TimeState) {
			
			this.time = (TimeState)es;
			
			this.notifyObservers(command);
		}
	}

	
	@Override
	public TimeState getEnvironmentState() {
		
		return time;
	}
	
	
	@Override
	public void register(Observer observer) {
		
		this.observers.add(observer);
	}

	@Override
	public void unRegister(Observer observer) {
		
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers(CommandDevice command) {
		
		for (Observer observer : observers) {
			
			observer.update(this.time, command);
		}
	}
	

	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return TimeState.class;
	}
}
