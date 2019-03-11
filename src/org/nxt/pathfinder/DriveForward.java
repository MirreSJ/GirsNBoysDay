package org.nxt.pathfinder;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {

	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	
	private boolean suppressed = false;
	
	public DriveForward(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		
	}
	
	public boolean takeControl() {
		return true;
		
	}

	public void action() {
		suppressed = false;
	    leftMotor.forward();
	    rightMotor.forward();
	    while (!suppressed)
	    {
	      Thread.yield(); //don't exit till suppressed
	    }
	    leftMotor.stop(); 
	    rightMotor.stop();

	}

	public void suppress() {
		suppressed = true;
		
	}

}
