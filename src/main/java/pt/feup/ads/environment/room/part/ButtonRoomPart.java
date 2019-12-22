package pt.feup.ads.environment.room.part;

import org.json.JSONObject;

import pt.feup.ads.environment.room.Room;
import pt.feup.ads.environment.state.ContactState;
import pt.feup.ads.environment.state.EnvironmentState;
import pt.feup.ads.factory.JSONConst;

public class ButtonRoomPart extends RoomPart {

	private ContactState contactState = ContactState.UNTOUCH;
	
	
	public void setContactState(ContactState contactState) {
		this.contactState = contactState;
	}

	public ButtonRoomPart(Long id, String name, Room room) {
		
		super(id, name, room);
	}

	@Override 
	public ContactState getEnvironmentState() {
		
		return contactState;
	}
	
	
	@Override
	public void setEnvironmentState(EnvironmentState newEnvironmentState) {
		
		if (this.getEnvironmentState() != null && !this.getEnvironmentState().equals(newEnvironmentState)) {
			
			System.out.println("Changing " + JSONConst.CONTACT_STATE + 
					" from " + this.getEnvironmentState().getName() + 
					" to " + newEnvironmentState.getName() +
					" in " + this.getName());
		}
		
		this.contactState = (ContactState)newEnvironmentState;
		
		this.processRules(newEnvironmentState);
	}
	
	
	@Override
	public String getEnvironmentStateName() {
		
		return JSONConst.CONTACT_STATE;
	}
	
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		this.setName( obj.getString(JSONConst.NAME) );
		this.setEnvironmentState( ContactState.valueOf( obj.getString(JSONConst.CONTACT_STATE) ) );
	}
}
