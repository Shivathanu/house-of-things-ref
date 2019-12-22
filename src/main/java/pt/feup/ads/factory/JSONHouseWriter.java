package pt.feup.ads.factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.Device;
import pt.feup.ads.device.SimpleDevice;
import pt.feup.ads.environment.House;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.room.part.RoomPart;

public class JSONHouseWriter {

	
	public static String convertHouseToJSON(House house) {
		
		//House
		JSONObject houseJSON = new JSONObject();
		houseJSON.put(JSONConst.ID, house.getId());
		houseJSON.put(JSONConst.NAME, house.getName());
		houseJSON.put(JSONConst.TIME_STATE, house.getTimeState());
		
		//Rooms
		JSONArray roomsJSON = new JSONArray();
		
		for (Room room : house.getObjectList()) {
			
			JSONObject roomJSON = new JSONObject();
			
			roomJSON.put(JSONConst.ID, room.getId());
			roomJSON.put(JSONConst.NAME, room.getName());
			roomJSON.put(JSONConst.TYPE, room.getClass().getCanonicalName() );
			roomJSON.put(JSONConst.HUB_PROTOCOL, room.getHub().getClass().getCanonicalName());
			roomJSON.put(JSONConst.TEMPERATURE_STATE, room.getEnvironment().getTemperature());
			roomJSON.put(JSONConst.LIGHT_STATE, room.getEnvironment().getLight());
			roomJSON.put(JSONConst.MOTION_STATE, room.getEnvironment().getMotion());
			roomJSON.put(JSONConst.SMOKE_STATE, room.getEnvironment().getSmoke());
			roomJSON.put(JSONConst.CO2_LEVEL_STATE, room.getEnvironment().getcO2Level());
			roomJSON.put(JSONConst.INFRA_RED_STATE, room.getEnvironment().getInfraRed());
			
			//Room Parts
			JSONArray roomPartsJSON = new JSONArray();
			
			for (RoomPart roomPart : room.getRoomParts()) {
				
				JSONObject roomPartJSON = new JSONObject();
				
				roomPartJSON.put(JSONConst.ID, roomPart.getId());
				roomPartJSON.put(JSONConst.NAME, roomPart.getName());
				roomPartJSON.put(JSONConst.TYPE, roomPart.getClass().getCanonicalName() );
				roomPartJSON.put(roomPart.getEnvironmentStateName(), roomPart.getEnvironmentState().getName());
				
				//Actuators Room Parts
				JSONArray actuatorsRoomPartsJSON = new JSONArray();
				
				for (Device actuatorRoomParts : roomPart.getObjectList()) {
					
					writeActuator(actuatorsRoomPartsJSON, actuatorRoomParts);
				}
				
				roomPartJSON.put(JSONConst.DEVICES, actuatorsRoomPartsJSON);
				roomPartsJSON.put(roomPartJSON);
			}
			
			roomJSON.put(JSONConst.ROOM_PARTS, roomPartsJSON);
			
			
			//Actuators
			JSONArray actuatorsRoomJSON = new JSONArray();
			
			for (Device actuatorRoom : room.getObjectList()) {
				
				writeActuator(actuatorsRoomJSON, actuatorRoom);
			}
			
			roomJSON.put(JSONConst.DEVICES, actuatorsRoomJSON);
			
			roomsJSON.put(roomJSON);

		}
		
		
		houseJSON.put(JSONConst.ROOMS, roomsJSON);
		
		String prettyJson = formatJsonString( houseJSON.toString() );
		
		return prettyJson;
	}

	
	private static String formatJsonString(String json) {
		
        ObjectMapper mapper = new ObjectMapper();
        
        try {
        	
            Object jsonObject = mapper.readValue(json, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            
            return prettyJson;
            
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
        
        return null;
    }
	

	private static void writeActuator(JSONArray actuatorsJSON, Device device) {
		
		ComplexDevice actuator = (ComplexDevice)device;
		
		JSONObject actuatorJSON = new JSONObject();
		
		actuatorJSON.put(JSONConst.ID, actuator.getId());
		actuatorJSON.put(JSONConst.NAME, actuator.getName());
		actuatorJSON.put(JSONConst.TYPE, actuator.getClass().getCanonicalName() );
		actuatorJSON.put(JSONConst.PROTOCOL, actuator.getProtocol().getClass().getCanonicalName() );
		actuatorJSON.put(JSONConst.DEVICE_STATE, actuator.getDeviceState() );
		
		//Sensors
		JSONArray sensorsJSON = new JSONArray();
		
		for (Device deviceSensor : actuator.getObjectList()) {
			
			SimpleDevice sensor = (SimpleDevice)deviceSensor;
			
			JSONObject sensorJSON = new JSONObject();
			
			sensorJSON.put(JSONConst.ID, sensor.getId());
			sensorJSON.put(JSONConst.NAME, sensor.getName());
			sensorJSON.put(JSONConst.TYPE, sensor.getClass().getCanonicalName() );
			
			sensorsJSON.put(sensorJSON);
		}
		
		actuatorJSON.put(JSONConst.SENSORS, sensorsJSON);
		actuatorsJSON.put(actuatorJSON);
	}
	
	
	public static void writeJsonSimpleDemo(String filename) throws Exception {
		
		JSONObject object = new JSONObject();
        object.put("id", 1L);
        object.put("name", "Alice");
        object.put("age", 20);

        JSONArray courses = new JSONArray();
        courses.put("Engineering");
        courses.put("Finance");
        courses.put("Chemistry");

        object.put("courses", courses);

        String jsonString = object.toString(2);
        System.out.println(jsonString);
        
	}
	
	
	@SuppressWarnings("unused")
	private String saveJSONToFile(String jSON, File file)  {

        StringBuilder contentBuilder = new StringBuilder();
        
        try {
        	
        	FileWriter f = new FileWriter(file);
 
        	BufferedWriter br = new BufferedWriter( f );
        	
        	br.write(jSON);
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return contentBuilder.toString();
    }
}
