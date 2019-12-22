package pt.feup.ads.protocol;

public class HueProtocol extends Protocol {
	
	public HueProtocol() {
		
		super.setName("Hue Protocol");
	}

	@Override
	public Boolean checkDataFormat(String data) {
		
		//Just for example. The objective here is analyze if data is well formatted, in according to this Protocol.
		
		return true;
	}
}
