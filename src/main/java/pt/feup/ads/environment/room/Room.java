package pt.feup.ads.environment.room;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pt.feup.ads.device.Device;
import pt.feup.ads.device.hub.Hub;
import pt.feup.ads.environment.House;
import pt.feup.ads.environment.room.part.RoomPart;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.InfraRedState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.SmokeState;
import pt.feup.ads.environment.state.TemperatureState;
import pt.feup.ads.factory.JSONConst;
import pt.feup.ads.protocol.Protocol;
import pt.feup.ads.util.Id;
import pt.feup.ads.util.Identifiable;

public class Room implements Identifiable {
	
	private Long id;
	private String name;
	private List<Device> devices = new ArrayList<Device>();
	
	private List<RoomPart> roomParts = new ArrayList<RoomPart>();
	
	private Hub hub;
	
	private final EnvironmentRoom er;
	
	private House house;
	
	
	public Room(Long id, String name, Protocol protocol, House house) {
		
		this.id = id;
		this.name = name;
		this.house = house;
		
		hub = new Hub(Id.getNextId(), name, protocol);
		
		er = EnvironmentRoom.getInstance(this);
		
	}	
	
	public House getHouse() {
		
		return house;
	}

	
	public EnvironmentRoom getEnvironment() {
		
		return er;
	}
	
	
	public List<EnvironmentState> getAllEnvironmentStateRoom() {
		
		List<EnvironmentState> environmentStates = new ArrayList<EnvironmentState>(); 
		
		environmentStates.add( er.getcO2Level() );
		environmentStates.add( er.getInfraRed() );
		environmentStates.add( er.getLight() );
		environmentStates.add( er.getMotion() );
		environmentStates.add( er.getSmoke() );
		environmentStates.add( er.getTemperature() );
		
		
		for (RoomPart roomPart : roomParts) {
			
			environmentStates.add( roomPart.getEnvironmentState() );
		}
		
		return environmentStates;
	}
	
	
	public RoomPart getRoomPart(Long idRoomPart) {
		
		for (RoomPart roomPart : roomParts) {
			
			if (roomPart.getId().equals(idRoomPart)) {
				
				return roomPart;
			}
		}
		
		return null;
	}
	
	public void addRoomPart(RoomPart d) {
		
		d.setRoom(this);
		
		roomParts.add(d);
	}
	
	public void removeDevice(RoomPart d) {
		
		d.setRoom(null);
		
		roomParts.remove(d);
	}
	
	
	public void addDevice(Device d) {
		
		d.setRoom(this);
		
		devices.add(d);
	}
	
	public void removeDevice(Device d) {
		
		d.setRoom(null);
		
		devices.remove(d);
	}
	
	public void setName(String name) {
		
		if (this.getName() != null && !this.getName().equals(name)) {
			
			System.out.println("Changing the name from " + this.getName() + " to " + name);
		}
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public Long getId() {
		
		return id;
	}
	
	@Override
	public String getImage() {
		
		return "smart_room.png";
	}

	
	@Override
	public List<Device> getObjectList() {
		
		return devices;
	}

	
	public List<RoomPart> getRoomParts() {
		
		return roomParts;
	}

	
	public Hub getHub() {
		return hub;
	}

	
	public void setHub(Hub hub) {
		this.hub = hub;
	}
	
	@Override
	public int hashCode() {
		
	    int hash = 0;
	    
	    hash += (id != null ? id.hashCode() : 0);
	    
	    return hash;
	}

	@Override
	public boolean equals(Object object) {
		
		Long otherRoomId = -1L;
	     
	    if (object instanceof Room) {
	    	
	    	otherRoomId = ((Room)object).getId();
	        
	    } else if(object instanceof Long){
	    	
	    	otherRoomId = (Long)object;
	        
	    } else {
	    	
	        return false;
	    }   

	    if ((this.getId() == null && otherRoomId != null) || (this.getId() != null && !this.getId().equals(otherRoomId))) {
	    	
	        return false;
	    }
	    
	    return true;    
	}
	
	
	@Override
	public String toString() {	
		
		return this.getName();
	}

	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{type: " + Room.class + ", id: " + id + ", name: " + name + "}");
		
		sb.append("\n");
		sb.append( "\t" + hub.toFullString() );
		sb.append("\n");
		
		for (Device device : devices) {
			
			sb.append("\n\t\t" + device.toFullString());
		} 
		
		return sb.toString();
	}


	@Override
	public String getJSONProperties() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n");
		sb.append("\t\"" + JSONConst.ID + "\": \"" + this.getId() + "\",\n");
		sb.append("\t\"" + JSONConst.NAME + "\": \"" + this.getName() + "\",\n");
		sb.append("\t\"" + JSONConst.TYPE + "\": \"" + this.getClass().getCanonicalName() + "\",\n");
		sb.append("\t\"" + JSONConst.HUB_PROTOCOL + "\": \"" + this.getHub().getProtocol().getClass().getCanonicalName() + "\",\n");
		sb.append("\t\"" + JSONConst.TEMPERATURE_STATE + "\": \"" + this.getEnvironment().getTemperature().getName() + "\",\n");
		sb.append("\t\"" + JSONConst.LIGHT_STATE + "\": \"" + this.getEnvironment().getLight().getName() + "\",\n");
		sb.append("\t\"" + JSONConst.MOTION_STATE + "\": \"" + this.getEnvironment().getMotion().getName() + "\",\n");
		sb.append("\t\"" + JSONConst.SMOKE_STATE + "\": \"" + this.getEnvironment().getSmoke().getName() + "\",\n");
		sb.append("\t\"" + JSONConst.CO2_LEVEL_STATE + "\": \"" + this.getEnvironment().getcO2Level().getName() + "\",\n");
		sb.append("\t\"" + JSONConst.INFRA_RED_STATE + "\": \"" + this.getEnvironment().getInfraRed().getName() + "\"\n");
		
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		this.getEnvironment().setTemperatureState( TemperatureState.valueOf( obj.getString(JSONConst.TEMPERATURE_STATE) ) );
		this.getEnvironment().setLightState( LightState.valueOf( obj.getString(JSONConst.LIGHT_STATE) ) );
		this.getEnvironment().setMotionState( MotionState.valueOf( obj.getString(JSONConst.MOTION_STATE) ) );
		this.getEnvironment().setSmokeState( SmokeState.valueOf( obj.getString(JSONConst.SMOKE_STATE) ) );
		this.getEnvironment().setcO2LevelState( CO2LevelState.valueOf( obj.getString(JSONConst.CO2_LEVEL_STATE) ) );
		this.getEnvironment().setInfraRedState( InfraRedState.valueOf( obj.getString(JSONConst.INFRA_RED_STATE) ) );
	}
}
