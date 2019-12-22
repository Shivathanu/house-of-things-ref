package pt.feup.ads.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import pt.feup.ads.factory.JSONConst;

public abstract class SimpleDevice extends Device implements Observable {

	protected List<Observer> observers = new ArrayList<Observer>();

	
	protected SimpleDevice(Long id, String name) {
		
		super(id, name);
	}
	
	@Override
	public List<Device> getObjectList() {
		
		return Collections.emptyList();
	}

	@Override
	public String getImage() {
		
		return "sensor.png";
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
	public String toString() {

		return this.getName();
	}
	
	@Override
	public String getJSONProperties() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n");
		sb.append("\t\"" + JSONConst.ID + "\": \"" + this.getId() + "\",\n");
		sb.append("\t\"" + JSONConst.NAME + "\": \"" + this.getName() + "\",\n");
		sb.append("\t\"" + JSONConst.TYPE + "\": \"" + this.getClass().getCanonicalName() + "\"\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		JSONObject obj = new JSONObject( jSONRepresentation );
		
		//Only name can be changed
		this.setName( obj.getString(JSONConst.NAME) );
	}
}
