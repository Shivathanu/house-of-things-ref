package pt.feup.ads.environment.room.part;

import org.json.JSONObject;

import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.factory.JSONConst;

public class DoorRoomPart extends RoomPart {

	private DoorState doorState = DoorState.CLOSE;
	
	public DoorRoomPart(Long id, String name, Room room) {

		super(id, name, room);
	}
	
	@Override 
	public DoorState getEnvironmentState() {
		
		return doorState;
	}
	
	@Override
	public void setEnvironmentState(EnvironmentState newEnvironmentState) {
		
		if (this.getEnvironmentState() != null && !this.getEnvironmentState().equals(newEnvironmentState)) {
			
			System.out.println("Changing " + JSONConst.DOOR_STATE + 
					" from " + this.getEnvironmentState().getName() + 
					" to " + newEnvironmentState.getName() +
					" in " + this.getName());
		}
		
		this.doorState = (DoorState)newEnvironmentState;
		
		this.processRules(newEnvironmentState);
	}

	@Override
	public String getEnvironmentStateName() {
		
		return JSONConst.DOOR_STATE;
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		this.setEnvironmentState( DoorState.valueOf( obj.getString(JSONConst.DOOR_STATE) ) );
	}
}
