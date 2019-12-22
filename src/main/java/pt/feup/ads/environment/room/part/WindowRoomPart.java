package pt.feup.ads.environment.room.part;

import org.json.JSONObject;

import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.environment.state.WindowState;
import pt.feup.ads.factory.JSONConst;

public class WindowRoomPart extends RoomPart {

	private WindowState windowState = WindowState.OPEN;
	
	public WindowRoomPart(Long id, String name, Room room) {
		
		super(id, name, room);
	}

	@Override 
	public WindowState getEnvironmentState() {
		
		return windowState;
	}
	
	@Override
	public void setEnvironmentState(EnvironmentState newEnvironmentState) {
		
		if (this.getEnvironmentState() != null && !this.getEnvironmentState().equals(newEnvironmentState)) {
			
			System.out.println("Changing " + JSONConst.WINDOW_STATE + 
					" from " + this.getEnvironmentState().getName() + 
					" to " + newEnvironmentState.getName() +
					" in " + this.getName());
		}
		
		this.windowState = (WindowState)newEnvironmentState;
		
		this.processRules(newEnvironmentState);
	}
	
	@Override
	public String getEnvironmentStateName() {
		
		return JSONConst.WINDOW_STATE;
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		this.setEnvironmentState( WindowState.valueOf( obj.getString(JSONConst.WINDOW_STATE) ) );
	}
}
