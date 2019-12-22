package pt.feup.ads.gui.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.feup.ads.device.sensor.TimeSensor;
import pt.feup.ads.environment.House;
import pt.feup.ads.environment.room.Room;
import pt.feup.ads.util.Identifiable;


public class HoTTreeView {
    
	
	public HoTTreeView() {
		
	}
    
    public void fillHouseTree(TreeView<Identifiable> houseTree, House house) {
    	
    	houseTree.setRoot( this.fill(house, null) );
	}

    
    private ImageView getImage(String imageName) {
		
		return new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/" + imageName)));
	}
    
	
    private TreeItem<Identifiable> fill(Identifiable identifiable, TreeItem<Identifiable> root) {
    	
    	TreeItem<Identifiable> house = root;
    	TreeItem<Identifiable> local;
    	
    	List<TreeItem<Identifiable>> children = new ArrayList<TreeItem<Identifiable>>();
    	
    	if (house == null) {
    		house = new TreeItem<Identifiable>(identifiable, this.getImage(identifiable.getImage()));
    		
    		house.setExpanded(true);
    	}
    	
    	
    	this.addRoomParts(identifiable, children);
    	
    	for (Identifiable i: identifiable.getObjectList()) {
    		
    		//All actuators have a TimeSensor. TimeSensor does not need to be showed.
    		if (!(i instanceof TimeSensor)) {
    			
    			local = new TreeItem<Identifiable>(i, this.getImage(i.getImage() ) );
        		
        		local.setExpanded(true);
        		
        		children.add(local);
    		}
    	}
    	
    	
    	if (children.size() > 0) {
    		house.getChildren().addAll(children);
    		
    	} 
    		
    	for (TreeItem<Identifiable> tc: children) {
    		
    		tc = this.fill(tc.getValue(), tc);
    	}
    	   	
    	return house;
    }

    
	private void addRoomParts(Identifiable identifiable, List<TreeItem<Identifiable>> children) {
		
		TreeItem<Identifiable> local;
		
		if (identifiable instanceof Room) {
    		
    		Room room = (Room)identifiable;
    		
    		for (Identifiable i: room.getRoomParts()) {
        		
        		local = new TreeItem<Identifiable>(i, this.getImage(i.getImage() ) );
        		
        		local.setExpanded(true);
        		
        		children.add(local);
        	}
    	}
	}
}
