package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.SmokeState;


public class SmokeSensor extends SimpleDevice implements Observable {
	
	private SmokeState smoke;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	public SmokeSensor(Long id, String name) {
		
		super(id, name);
	}
	
	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof SmokeState) {
				
			this.smoke = (SmokeState)es;
			
			this.notifyObservers(command);
		}
	}

	
	@Override
	public SmokeState getEnvironmentState() {
		
		return smoke;
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
			
			observer.update(this.smoke, command);
		}
	}
	
	
	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return SmokeState.class;
	}
}
