package pt.feup.ads.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.DeviceState;
import pt.feup.ads.device.Observable;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.device.sensor.TimeSensor;
import pt.feup.ads.environment.House;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.room.part.RoomPart;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.ContactState;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.InfraRedState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.SmokeState;
import pt.feup.ads.environment.state.TemperatureState;
import pt.feup.ads.environment.state.TimeState;
import pt.feup.ads.environment.state.WindowState;
import pt.feup.ads.protocol.Protocol;
import pt.feup.ads.util.Id;

/*
 * Implementation of Factory Method
 * */
public class JSONHouseLoader implements HouseLoader {
	

	@Override
	public House build(File selectedFile) {
		
		String jSONContent = this.loadFileToString(selectedFile);
		
		return this.convertJSONToHouse(jSONContent);
	}
	
	
	public House convertJSONToHouse(String jSONContent) {
		
		JSONObject obj = new JSONObject( jSONContent );

		Long idHouse = Id.getNextId();
		String nameHouse = obj.getJSONObject(JSONConst.HOUSE).getString(JSONConst.NAME);
		String timeStateHouse = obj.getJSONObject(JSONConst.HOUSE).getString(JSONConst.TIME_STATE);
		
		House house = new House(idHouse, nameHouse);
		
		house.setTimeState(TimeState.valueOf(timeStateHouse));

		JSONArray rooms = obj.getJSONObject(JSONConst.HOUSE).getJSONArray(JSONConst.ROOMS);
		
		for (int i = 0; i < rooms.length(); i++) {
			
		    Long idRoom = Id.getNextId(); 
		    String nameRoom = rooms.getJSONObject(i).getString(JSONConst.NAME);
		    String hubProtocol = rooms.getJSONObject(i).getString(JSONConst.HUB_PROTOCOL);
		    String temperature = rooms.getJSONObject(i).getString(JSONConst.TEMPERATURE_STATE);
		    String light = rooms.getJSONObject(i).getString(JSONConst.LIGHT_STATE);
		    String motion = rooms.getJSONObject(i).getString(JSONConst.MOTION_STATE);
		    String smoke = rooms.getJSONObject(i).getString(JSONConst.SMOKE_STATE);
		    String cO2Level = rooms.getJSONObject(i).getString(JSONConst.CO2_LEVEL_STATE);
		    String infraRed = rooms.getJSONObject(i).getString(JSONConst.INFRA_RED_STATE);
		    
		    Room room = new Room(idRoom, nameRoom, this.getProtocolObject(hubProtocol), house );
		    
		    room.getEnvironment().setTemperatureState(TemperatureState.valueOf(temperature));
		    room.getEnvironment().setLightState(LightState.valueOf(light));
		    room.getEnvironment().setMotionState(MotionState.valueOf(motion));
		    room.getEnvironment().setSmokeState(SmokeState.valueOf(smoke));
		    room.getEnvironment().setcO2LevelState(CO2LevelState.valueOf(cO2Level));
		    room.getEnvironment().setInfraRedState(InfraRedState.valueOf(infraRed));
		    
		    //Room Parts
		    if (!rooms.getJSONObject(i).isNull(JSONConst.ROOM_PARTS)) {
		    	
		    	JSONArray roomParts = rooms.getJSONObject(i).getJSONArray(JSONConst.ROOM_PARTS);
		    	
		    	this.loadRoomParts(roomParts, room);
		    }
		    
		    //Room Devices
		    if (!rooms.getJSONObject(i).isNull(JSONConst.DEVICES)) {
		    	
		    	JSONArray devices = rooms.getJSONObject(i).getJSONArray(JSONConst.DEVICES);
		    	
		    	this.loadDevices(devices, room, null);
		    }
		    
		    house.addRoom(room);
		}
		
		return house;
	}
	
	

	
	private void loadRoomParts(JSONArray roomParts, Room room) {

		for (int j = 0; j < roomParts.length(); j++) {
			
			Long idRoomPart = Id.getNextId();
		    String nameRoomPart = roomParts.getJSONObject(j).getString(JSONConst.NAME);
		    String typeRoomPart = roomParts.getJSONObject(j).getString(JSONConst.TYPE);
		    
		    EnvironmentState environmentState = null;
		    
		    if (!roomParts.getJSONObject(j).isNull(JSONConst.WINDOW_STATE) ) {
		    	
		    	environmentState = WindowState.valueOf(roomParts.getJSONObject(j).getString(JSONConst.WINDOW_STATE));
		    	
		    } else if (!roomParts.getJSONObject(j).isNull(JSONConst.DOOR_STATE)) {
		    	
		    	environmentState = DoorState.valueOf(roomParts.getJSONObject(j).getString(JSONConst.DOOR_STATE));
		    	
		    } else if (!roomParts.getJSONObject(j).isNull(JSONConst.CONTACT_STATE)) {
		    	
		    	environmentState = ContactState.valueOf(roomParts.getJSONObject(j).getString(JSONConst.CONTACT_STATE));
		    }
		    
		    try {
		        
		    	Class<?> roomPartClass = getClass().getClassLoader().loadClass(typeRoomPart);
		    	Constructor<?> roomPartConstructor = roomPartClass.getConstructor(Long.class, String.class, Room.class);
		    	
		    	RoomPart roomPart = (RoomPart)roomPartConstructor.newInstance(idRoomPart, nameRoomPart, room);
		    	
		    	roomPart.setEnvironmentState(environmentState);
		        
		    	//RoomParts Devices
			    if (!roomParts.getJSONObject(j).isNull(JSONConst.DEVICES)) {
			    	
			    	JSONArray devices = roomParts.getJSONObject(j).getJSONArray(JSONConst.DEVICES);
			    	
			    	this.loadDevices(devices, room, roomPart);
			    }
			    
			    room.addRoomPart(roomPart);
		    	
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
	
	
	private void loadDevices(JSONArray devices, Room room, RoomPart roomPart) {

		for (int j = 0; j < devices.length(); j++) {
			
			Long idActuator = Id.getNextId();
		    String nameActuator = devices.getJSONObject(j).getString(JSONConst.NAME);
		    String typeActuator = devices.getJSONObject(j).getString(JSONConst.TYPE);
		    String protocolActuator = devices.getJSONObject(j).getString(JSONConst.PROTOCOL);
		    String actuatorState = devices.getJSONObject(j).getString(JSONConst.DEVICE_STATE);
		    
		    try {
		        
		    	Class<?> actuatorClass = getClass().getClassLoader().loadClass(typeActuator);
		    	Constructor<?> actuatorConstructor = actuatorClass.getConstructor(Long.class, String.class);
		    	
		    	ComplexDevice actuator = (ComplexDevice)actuatorConstructor.newInstance(idActuator, nameActuator);
		    	actuator.setProtocol(this.getProtocolObject(protocolActuator));
		    	actuator.setDeviceState(DeviceState.valueOf(actuatorState));
		    	actuator.setRoom(room);
		    	
		    	
		    	if (!devices.getJSONObject(j).isNull(JSONConst.SENSORS)) {
			    	
			    	JSONArray sensors = devices.getJSONObject(j).getJSONArray(JSONConst.SENSORS);
			    	
			    	List<Observable> observables = new ArrayList<Observable>();
			    	
			    	//Sensors
			    	for (int k = 0; k < sensors.length(); k++) {
			    		
			    		Long idSensor = Id.getNextId(); 
					    String nameSensor = sensors.getJSONObject(k).getString(JSONConst.NAME);
					    String typeSensor = sensors.getJSONObject(k).getString(JSONConst.TYPE);
					    
					    Class<?> sensorClass = getClass().getClassLoader().loadClass(typeSensor);
					    Constructor<?> sensorConstructor = sensorClass.getConstructor(Long.class, String.class);
					    
					    SimpleDevice sensor = (SimpleDevice)sensorConstructor.newInstance(idSensor, nameSensor);
					    
					    sensor.setRoom(room);
					    sensor.register(actuator);
					    
					    actuator.add(sensor);
					    observables.add(sensor);
					}
			    	
			    	//adding TimeSensor: every Actuator will have a TimeSensor to response the House time changing.
			    	SimpleDevice timeSensor = new TimeSensor(Id.getNextId(), "Time Sensor");
			    	timeSensor.setRoom(room);
			    	timeSensor.register(actuator);
			    	actuator.add(timeSensor);
				    observables.add(timeSensor);
			    	
			    	actuator.registerObservables(observables);
		    	
		    	}    
		        
		    	
		    	if (roomPart != null) {
		    		
		    		actuator.setIdRoomPart(roomPart.getId());
		    		
		    		roomPart.addDevice(actuator);
		    		
		    	} else {
		    		
		    		room.addDevice(actuator);		    		
		    	}
		    	
		        
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
	
	
	private Protocol getProtocolObject(String protocolClassName) {
		
		try {
		
			Class<?> protocolClass = getClass().getClassLoader().loadClass(protocolClassName);
		    
		    Constructor<?> constructor = protocolClass.getConstructor();
		    
		    Object object = constructor.newInstance();
		    
		    return (Protocol)object;
		    
		    
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;	
	}
	
	
    private String loadFileToString(File selectedFile)  {

    	
        StringBuilder contentBuilder = new StringBuilder();
        
        try {
        	
        	FileReader f = new FileReader(selectedFile);
 
        	BufferedReader br = new BufferedReader( f );
        	
            String sCurrentLine;
            
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return contentBuilder.toString();
    }
}
