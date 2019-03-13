package org.nxt.pathfinder;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class CancelationToken {
	private boolean cancelationRequested = false;
	private TouchSensor touch;
	
	public CancelationToken(){
		touch = new TouchSensor(SensorPort.S2);
	}
	
	public boolean IsCancelationRequested(){
		if(Button.ESCAPE.isDown() || touch.isPressed()){
			cancelationRequested = true;
		}
		return cancelationRequested;
	}

}
