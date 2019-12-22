package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.MotionState;

public class MotionSensor extends SimpleDevice implements Observable {
	

	private MotionState motion;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	public MotionSensor(Long id, String name) {
		
		super(id, name);	
	}
	
	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof MotionState) {
				
			this.motion = (MotionState)es;
			
			this.notifyObservers(command);
		}
	}
	
	
	@Override
	public MotionState getEnvironmentState() {
		
		return motion;
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
			
			observer.update(this.motion, command);
		}
	}
	
	
	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return MotionState.class;
	}
}
