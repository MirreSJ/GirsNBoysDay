package org.nxt.pathfinder;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.PilotProps;

public class DetectLine implements Behavior{
	private ColorSensor sensor;
	final static int INTERVAL = 200; // milliseconds
	private boolean suppressed = false;
	private CancelationToken cancelationToken;
	private int direction = -1;

	private DifferentialPilot pilot;	
	
	public DetectLine(CancelationToken cancelationToken, DifferentialPilot pilot){
		sensor = new ColorSensor(SensorPort.S1);
		this.cancelationToken = cancelationToken;
		
		
		this.pilot = pilot;
		
	}
	
	public void action() {
		while(!suppressed && HasLostLine() && !cancelationToken.IsCancelationRequested()) {
			LCD.clear();
			LCD.drawString("Value: " + sensor.getLightValue(),0,1);
			
			int maxDegree = 160;
			int step = 30;
			int degree = step;
			while(!cancelationToken.IsCancelationRequested() && degree < maxDegree && HasLostLine()){				
				searchLineOnBothSides(degree);
				degree += step;
			}			
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void searchLineOnBothSides(int degree) {
		LCD.clear();
		LCD.drawString("Start: " +direction,0,1);
		int step = 5;
		int steps = degree / step;
		int stepCnt = 0;
		boolean found = false;
		found = Search(stepCnt,steps, step);
		if(!found){
			stepCnt = 0;
			direction = direction * -1;
			Search(stepCnt,steps, step);
		}
		LCD.drawString("End: " +direction,0,2);
	}
	
	private boolean Search(int stepCnt, int steps, int step){
		boolean hasLostLine = HasLostLine();
		while(!cancelationToken.IsCancelationRequested() && stepCnt < steps && hasLostLine){
			stepCnt++;
			pilot.rotate(step * direction);
			hasLostLine = HasLostLine();
		}
		if(hasLostLine){
			pilot.rotate(stepCnt * step * direction * -1);
		}else{
			pilot.rotate(step * direction);
		}
		return !hasLostLine;
	}
	
	

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl() {
		return HasLostLine() && !cancelationToken.IsCancelationRequested();
	}

	private boolean HasLostLine() {
		return sensor.getLightValue() > 100;
	}

}