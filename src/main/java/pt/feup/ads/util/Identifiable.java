package pt.feup.ads.util;

import java.util.List;

public interface Identifiable {
	
	
	public Long getId();
	public String getName();
	public String getImage();
	
	public List<? extends Identifiable> getObjectList();
	
	public String getJSONProperties();
	
	public void updateIdentifiableAttributesJSON(String jSONRepresentation);
}
