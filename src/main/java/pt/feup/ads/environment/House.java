package pt.feup.ads.environment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.rule.Rule;
import pt.feup.ads.environment.state.TimeState;
import pt.feup.ads.factory.JSONConst;
import pt.feup.ads.util.Identifiable;


public class House implements Identifiable {
	
	private Long id;
	private String name;
	
	private TimeState timeState = TimeState.MORNING;
	
	
	private List<Room> rooms = new ArrayList<Room>();
	
	private List<Rule> rules;
	
	public House(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public void setRules(List<Rule> rules) {
	
		this.rules = rules;
	}
	
	public List<Rule> getRules() {
		
		return rules;
	}
	
	
	public void addRoom( Room room ) {
		
		rooms.add(room);
	}
	
	public void removeRoom( Room room ) {
		
		rooms.remove(room);
	}

	public Long getId() {
		
		return id;
	}
	
	public String getName() {
		
		return name;
	}
	
	private void setName(String name) {
		
		if (this.getName() != null && !this.getName().equals(name)) {
			
			System.out.println("Changing the name from " + this.getName() + " to " + name);
		}
		
		this.name = name;
	}
	
	public TimeState getTimeState() {
		return timeState;
	}


	public void setTimeState(TimeState newTimeState) {
		
		if (this.getTimeState() != null && !this.getTimeState().equals(newTimeState)) {
				
			System.out.println("Changing timeEstate from " + this.getTimeState().getName() + " to " + newTimeState.getName() );

		}
		
		this.timeState = newTimeState;
		
		
		if (this.getRules() != null) {
			
			for (Rule rule : this.getRules() ) {
				
				if (rule.getRuleEnvironmentState().equals(newTimeState)) {
					
					newTimeState.doAction(rule);
					
					rule.dispatch();
				}
			}
		}
		
	}
	
	
	@Override
	public String getImage() {
	
		return "iot_house.png";
	}

	@Override
	public List<Room> getObjectList() {
		
		return rooms;
	}

	@Override
	public String toString() {
		
		return this.getName();
	}
	
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{type: " + House.class + ", id: " + id + ", name: " + name + "\n");
		
		for (Room room : rooms) {
			
			sb.append("\n\t" + room.toFullString());
		} 
		
		return sb.toString();
	}

	@Override
	public String getJSONProperties() {
		
		
		StringBuilder sb = new StringBuilder();
	
		sb.append("{\n");
		sb.append("\t\"" + JSONConst.ID + "\": \"" + this.getId() + "\",\n");
		sb.append("\t\"" + JSONConst.NAME + "\": \"" + this.getName() + "\",\n");
		sb.append("\t\"" + JSONConst.TIME_STATE + "\": \"" + this.getTimeState().getName() + "\"\n");
		sb.append("}");
		
		return sb.toString();
		
		//return JSONHouseWriter.convertHouseToJSON(this);
	}
	
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		
		String newTimeStateHouse = obj.getString(JSONConst.TIME_STATE);
		
		this.setTimeState(TimeState.valueOf(newTimeStateHouse));	
	}
}
