package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.EnvironmentState;


public class CO2LevelSensor extends SimpleDevice implements Observable {
	
	private CO2LevelState cO2Level;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	public CO2LevelSensor(Long id, String name) {
		
		super(id, name);
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
			
			observer.update(this.cO2Level, command);
		}
	}

	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof CO2LevelState) {	
			
			this.cO2Level = (CO2LevelState)es;
			
			this.notifyObservers(command);
		}
	}
	
	
	@Override
	public CO2LevelState getEnvironmentState() {
		
		return cO2Level;
	}
	
	
	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return CO2LevelState.class;
	}
}
