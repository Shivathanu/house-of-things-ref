package pt.feup.ads.util;

public class Id {
	
	private static Long number = 0L;
	
	
	private Id() {
		
	}
	
	public synchronized static Long getNextId() {
		
		number = number + 1L;
		
		return number;
	}

}
