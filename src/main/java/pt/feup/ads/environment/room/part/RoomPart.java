package pt.feup.ads.environment.room.part;

import java.util.ArrayList;
import java.util.List;

import pt.feup.ads.device.Device;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.factory.JSONConst;
import pt.feup.ads.util.Identifiable;

public abstract class RoomPart  implements Identifiable {
	
	private Long id;
	private String name;
	
	private List<Device> devices = new ArrayList<Device>();
	
	private Room room;
	
	public RoomPart(Long id, String name, Room room) {
		
		this.id = id;
		this.setName(name);
		this.setRoom(room);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if (this.getName() != null && !this.getName().equals(name)) {
			
			System.out.println("Changing the name from " + this.getName() + 
					" to " + name +
					" in " + this.getName());
		}
		
		this.name = name;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room r) {
		this.room = r;
	}
	
	public void addDevice(Device d) {
		
		d.setRoom(room);
		
		devices.add(d);
	}
	
	public void removeDevice(Device d) {
		
		d.setRoom(null);
		
		devices.remove(d);
	}
	
	
	protected void processRules(EnvironmentState newEnvironmentState) {
		
		if (this.getRoom().getHouse().getRules() != null) {
			
			for (Rule rule : this.getRoom().getHouse().getRules() ) {
				
				if (rule.getRuleEnvironmentState().equals(newEnvironmentState)) {

					if (this.getId().equals(rule.getRuleActuator().getIdRoomPart())) {
						
						newEnvironmentState.doAction(rule);
						
						rule.dispatch();
					}
				}
			}
		}
	}
	
	
	@Override
	public List<Device> getObjectList() {
		
		return devices;
	}
	
	@Override
	public String getImage() {
		
		return "room_part.png";
	}
	
	@Override
	public String getJSONProperties() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n");
		sb.append("\t\"" + JSONConst.ID + "\": \"" + this.getId() + "\",\n");
		sb.append("\t\"" + JSONConst.NAME + "\": \"" + this.getName() + "\",\n");
		sb.append("\t\"" + JSONConst.TYPE + "\": \"" + this.getClass().getCanonicalName() + "\",\n");
		sb.append("\t\"" + this.getEnvironmentStateName() + "\": \"" + this.getEnvironmentState().getName() + "\"\n");
		
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public String toString() {	
		
		return this.getName();
	}

	public abstract EnvironmentState getEnvironmentState();

	public abstract void setEnvironmentState(EnvironmentState environmentState);
	
	public abstract String getEnvironmentStateName();

}
