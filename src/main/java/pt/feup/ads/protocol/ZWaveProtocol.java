package pt.feup.ads.protocol;

public class ZWaveProtocol extends Protocol {
	
	
	public ZWaveProtocol() {
		
		super.setName("ZWave Protocol");
	}

	@Override
	public Boolean checkDataFormat(String data) {
		
		//Just for example. The objective here is analyze if data is well formatted, in according to this Protocol.
		
		return true;
	}
}
