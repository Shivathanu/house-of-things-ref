package pt.feup.ads.protocol;

public class TradfriProtocol extends Protocol {
	
	public TradfriProtocol() {
		
		super.setName("Tradfri Protocol");
	}

	@Override
	public Boolean checkDataFormat(String data) {
		
		//Just for example. The objective here is analyze if data is well formatted, in according to this Protocol.
		
		return true;
	}
}
