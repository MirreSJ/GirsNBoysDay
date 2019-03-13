package org.nxt.pathfinder;

import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {

	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.C;	
	private boolean suppressed = false;
	private CancelationToken cancelationToken;
	
	public DriveForward(CancelationToken cancelationToken) {
		leftMotor.setSpeed(400);
	    rightMotor.setSpeed(400);
	    this.cancelationToken = cancelationToken;
	}
	
	public boolean takeControl() {
		return !cancelationToken.IsCancelationRequested();		
	}

	public void action() {
		suppressed = false;
	    leftMotor.forward();
	    rightMotor.forward();
	    while (!suppressed && !cancelationToken.IsCancelationRequested())
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
