package org.nxt.pathfinder;
import java.io.IOException;

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
	
	DifferentialPilot robot;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	
	public DetectLine(CancelationToken cancelationToken, RegulatedMotor leftMotor, RegulatedMotor rightMotor){
		sensor = new ColorSensor(SensorPort.S1);
		this.cancelationToken = cancelationToken;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		
		PilotProps pp = new PilotProps();
		try {
			pp.loadPersistentValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "3"));
		float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "12"));
		boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE,"false"));

		this.robot = new DifferentialPilot(wheelDiameter,trackWidth,leftMotor,rightMotor,reverse);
		
	}
	
	public void action() {
		while(!suppressed && HasLostLine() && !cancelationToken.IsCancelationRequested()) {
			LCD.clear();
			LCD.drawString("Value: " + sensor.getLightValue(),0,1);
			
			int maxDegree = 160;
			int step = 10;
			int degree = 10;
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
		int step = 2;
		int steps = degree / step;
		int stepCnt = 0;
		boolean hasLostLine = HasLostLine();
		while(!cancelationToken.IsCancelationRequested() && stepCnt < steps && hasLostLine){
			stepCnt++;
			robot.rotate(step * -1);
			hasLostLine = HasLostLine();
		}
		if(hasLostLine){
			stepCnt = 0;	
			robot.rotate(stepCnt * step);
		}
		while(!cancelationToken.IsCancelationRequested() && stepCnt < steps && hasLostLine){
			stepCnt++;
			robot.rotate(step);
			hasLostLine = HasLostLine();
		}
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