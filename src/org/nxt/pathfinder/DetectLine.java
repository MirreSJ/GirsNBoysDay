package org.nxt.pathfinder;
import lejos.nxt.LCD;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class DetectLine implements Behavior{
	private ColorSensor sensor;
	final static int INTERVAL = 200; // milliseconds
	private boolean suppressed = false;
	private CancelationToken cancelationToken;
	
	public DetectLine(CancelationToken cancelationToken){
		sensor = new ColorSensor(SensorPort.S1);
		this.cancelationToken = cancelationToken;
	}
	
	public void action() {
		while(!suppressed && HasLeftLine() && !cancelationToken.IsCancelationRequested()) {
			LCD.clear();
			LCD.drawString("Value: " + sensor.getLightValue(),0,1);
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl() {
		return HasLeftLine() && !cancelationToken.IsCancelationRequested();
	}

	private boolean HasLeftLine() {
		return sensor.getLightValue() > 100;
	}

}