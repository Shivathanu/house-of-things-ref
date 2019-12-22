package pt.feup.ads.device;

import pt.feup.ads.environment.room.Room;
import pt.feup.ads.util.Identifiable;

public abstract class Device implements Identifiable {
	
	
	private Long id;
	private String name;
	
	private Room room;
	
	public Device(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}

	
	@Override
	public Long getId() {
		return id;
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {

		if (this.getName() != null && !this.getName().equals(name)) {
			
			System.out.println("Changing the name from " + this.getName() + " to " + name);
		}
		
		this.name = name;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		

        if (obj == this) { 
        	
            return true; 
        } 
  
      
        if (!(obj instanceof Device)) { 
        	
            return false; 
        } 
          
        Device c = (Device) obj;
        
        
        return this.id.equals( c.getId() ); 
	}
	

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {

		return this.getName();
	}
	
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{type: " + this.getClass() + 
				", id: " + id + 
				", name: " + name +  
				"}");
		
		return sb.toString();

	}
}

