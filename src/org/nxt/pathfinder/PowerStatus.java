package org.nxt.pathfinder;

import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class PowerStatus implements IPowerStatus {
	private TouchSensor touch;
	private boolean isOn = false;
	
	public PowerStatus(){
		touch = new TouchSensor(SensorPort.S1);
	}
	
	public boolean IsOn() {
		if(touch.isPressed()){
			isOn = !isOn;
		}		
		return isOn;
	}
}
