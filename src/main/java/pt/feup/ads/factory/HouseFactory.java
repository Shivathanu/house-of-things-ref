package pt.feup.ads.factory;

import java.io.File;

import pt.feup.ads.environment.House;


/*
 * Implementation of Factory Method
 * */
public class HouseFactory {
	
	private static House house = null;
	
	
	private HouseFactory() {
		
	}
	
	
	public static House getHouseFromFile(File selectedFile) {
		
		return getHouse(selectedFile, FormatType.JSON);
	}
	
	public static House getLoadedHouse() {
			
		return house;
		
	}
	
	
	private static House getHouse(File selectedFile, FormatType formatType) {
		
		if (formatType == null) {
	    	  
			return null;
		}
	      
		if (formatType == FormatType.JSON) {
	    	 
			house = new JSONHouseLoader().build(selectedFile);
			
			return house;
	         
		} else if (formatType == FormatType.XML) {
			
			throw new RuntimeException("Not implemented yet.");
	         
		}
		      
		return house;
	}
	
	
	
}
