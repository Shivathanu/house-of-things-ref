package pt.feup.ads.protocol;


public abstract class Protocol {
	
	private String name;
	
	public abstract Boolean checkDataFormat(String data);
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{type: " + this.getClass() + 
				", name: " + name + 
				"}");
		
		return sb.toString();
	}
}
