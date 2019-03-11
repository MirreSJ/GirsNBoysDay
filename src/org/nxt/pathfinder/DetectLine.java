package org.nxt.pathfinder;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

import lejos.robotics.subsumption.Behavior;

public class DetectLine implements Behavior{
	private LightSensor sensor;
	
	public DetectLine(){
		sensor = new LightSensor(SensorPort.S1);
	}
	
	public void action() {
		//suche die Linie
		
	}

	public void suppress(){
		//Since  this is highest priority behavior, suppress will never be called.
	}

	public boolean takeControl() {
		return sensor.readValue() > 40;
	}

}
