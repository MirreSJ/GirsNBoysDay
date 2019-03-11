package org.nxt.pathfinder;


import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class LineFollower {

	  static RegulatedMotor leftMotor = Motor.A;
	  static RegulatedMotor rightMotor = Motor.C;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		leftMotor.setSpeed(400);
	    rightMotor.setSpeed(400);
	    Behavior b1 = new DriveForward(leftMotor, rightMotor);
	    Behavior b2 = new DetectLine();
	    Behavior[] behaviorList =
	    {
	      b1
	    };
	    Arbitrator arbitrator = new Arbitrator(behaviorList);
	    LCD.drawString("Line Follower :)",0,1);
	    Button.waitForAnyPress();
	    arbitrator.start();

	}

}
