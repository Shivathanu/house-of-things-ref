package pt.feup.ads;

import pt.feup.ads.environment.House;
import pt.feup.ads.factory.HouseFactory;


public class MainCLI {
	
	public static void main(String[] args) {
		
		
		House house = HouseFactory.getLoadedHouse();
		
		System.out.println( house.toFullString() );
		
		//simulator.start( createHouse() ); 
		
	}
	
	/*
	private static House createHouse() {
		
		House house = new House(Id.getNextId(), "House of Dreams");
		
		Room living = new Room(Id.getNextId(), "Living Room");
		FanActuator fanActuator = createFanActuator(Id.getNextId(), "Fan Actuator 1");
		living.addDevice(fanActuator);
		
		
		Room kitchen = new Room(Id.getNextId(), "Kitchen");
		LampActuator lampActuator = createLampActuator(Id.getNextId(), "Lamp Actuator 1");
		kitchen.addDevice(lampActuator);
		
		
		house.addRoom(living);
		house.addRoom(kitchen);
		
		return house;
	}
	
	public static LampActuator createLampActuator(Long id, String name) {
		
		LightSensor lightSensor = new LightSensor(Id.getNextId(), "Light Sensor 1");
		MotionSensor motionSensor = new MotionSensor(Id.getNextId(), "Motion Sensor 1");
		
		LampActuator lampActuator = new LampActuator(id, name, lightSensor, motionSensor);
		
		
		return lampActuator;
	}

	
	public static FanActuator createFanActuator(Long id, String name) {
		
		TemperatureSensor temperatureSensor = new TemperatureSensor(Id.getNextId(), "Temperature Sensor 1");
		MotionSensor motionSensor = new MotionSensor(Id.getNextId(), "Motion Sensor 2");
		
		FanActuator fanActuator = new FanActuator(id, name, temperatureSensor, motionSensor);
		
		return fanActuator;
	}
	*/
}
