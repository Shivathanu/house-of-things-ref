package pt.feup.ads.device.sensor;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.command.device.CommandDevice;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.Observer;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.InfraRedState;

public class InfraRedSensor extends SimpleDevice implements Observable {

	
	private InfraRedState infraRed;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	public InfraRedSensor(Long id, String name) {
		
		super(id, name);
	}

	
	
	@Override
	public void setEnvironmentState(EnvironmentState es, CommandDevice command) {
		
		if (es instanceof InfraRedState) {
				
			this.infraRed = (InfraRedState)es;
			
			this.notifyObservers(command);
		}
	}
	
	
	@Override
	public InfraRedState getEnvironmentState() {
		
		return infraRed;
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
			
			observer.update(this.infraRed, command);
		}
	}
	
	
	@Override
	public Class<?> getEnvironmentStateClass() {
		
		return InfraRedState.class;
	}
}
