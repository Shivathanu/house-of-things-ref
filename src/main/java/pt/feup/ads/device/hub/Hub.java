package pt.feup.ads.device.hub;

import java.util.Collections;
import java.util.List;

import pt.feup.ads.command.environment.CommandEnvironment;
import pt.feup.ads.command.environment.SetCO2LevelCommand;
import pt.feup.ads.command.environment.SetDoorCommand;
import pt.feup.ads.command.environment.SetInfraRedCommand;
import pt.feup.ads.command.environment.SetLightCommand;
import pt.feup.ads.command.environment.SetMotionCommand;
import pt.feup.ads.command.environment.SetSmokeCommand;
import pt.feup.ads.command.environment.SetTemperatureCommand;
import pt.feup.ads.command.environment.SetWindowCommand;
import pt.feup.ads.device.ComplexDevice;
import pt.feup.ads.device.Device;
import pt.feup.ads.environment.state.CO2LevelState;
import pt.feup.ads.environment.state.ContactState;
import pt.feup.ads.environment.state.DoorState;
import pt.feup.ads.environment.state.InfraRedState;
import pt.feup.ads.environment.state.LightState;
import pt.feup.ads.environment.state.MotionState;
import pt.feup.ads.environment.state.SmokeState;
import pt.feup.ads.environment.state.TemperatureState;
import pt.feup.ads.environment.state.WindowState;
import pt.feup.ads.protocol.Protocol;
import pt.feup.ads.util.Identifiable;


public class Hub extends Device {
	
	private Protocol protocol;
	
	public Hub(Long id, String name, Protocol protocol) {
		
		super(id, name + " Hub");
		
		this.setProtocol(protocol);
	}
	
	
	public synchronized void addMessageLog(ComplexDevice device) {

		System.out.println( "Actuator " + device.getName() + " uses a unsupported protocol by HUB Room.");		
	}
	
	
	public void send( ComplexDevice device, MotionState data ) {
		
		CommandEnvironment command = new SetMotionCommand();
		
		command.execute(this, device, data);

	}
	
	public void send( ComplexDevice device, ContactState data ) {
		
		CommandEnvironment command = new SetMotionCommand();
		
		command.execute(this, device, data);

	}
	
	public void send( ComplexDevice device, TemperatureState data ) {
		
		CommandEnvironment command = new SetTemperatureCommand();
		
		command.execute(this, device, data);

	}
	
	
	public void send( ComplexDevice device, LightState data ) {
		
		CommandEnvironment command = new SetLightCommand();
		
		command.execute(this, device, data);

	}
	
	public void send(ComplexDevice device, WindowState data) {
		
		CommandEnvironment command = new SetWindowCommand();
		
		command.execute(this, device, data);
	}
	
	
	public void send(ComplexDevice device, SmokeState data) {
		
		CommandEnvironment command = new SetSmokeCommand();
		
		command.execute(this, device, data);
	}
	
	
	public void send(ComplexDevice device, InfraRedState data) {
		
		CommandEnvironment command = new SetInfraRedCommand();
		
		command.execute(this, device, data);
	}
	
	public void send(ComplexDevice device, DoorState data) {
		
		CommandEnvironment command = new SetDoorCommand();
		
		command.execute(this, device, data);
	}
	
	
	public void send(ComplexDevice device, CO2LevelState data) {
		
		CommandEnvironment command = new SetCO2LevelCommand();
		
		command.execute(this, device, data);
	}
	
	
	

	public Protocol getProtocol() {
		return protocol;
	}


	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}


	@Override
	public String getImage() {
		
		return "";
	}


	@Override
	public List<? extends Identifiable> getObjectList() {
		
		return Collections.emptyList();
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(super.toString());
		
		return sb.toString();
	}
	
	@Override
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(super.toFullString());
		
		return sb.toString();
	}

	
	@Override
	public String getJSONProperties() {
		
		return null;
	}
	
	@Override
	public void updateIdentifiableAttributesJSON(String jSONRepresentation) {
		
		//No HUB Attribute can be changed
	}
}

