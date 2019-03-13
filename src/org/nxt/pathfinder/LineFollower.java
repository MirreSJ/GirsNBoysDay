package org.nxt.pathfinder;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class LineFollower {

	private static RegulatedMotor leftMotor = Motor.A;
	private static RegulatedMotor rightMotor = Motor.C;	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CancelationToken cancellationToken = new CancelationToken();
	    Behavior b1 = new DriveForward(cancellationToken, leftMotor, rightMotor);
	    Behavior b2 = new DetectLine(cancellationToken, leftMotor, rightMotor);
	    Behavior[] behaviorList =
	    {
	    		b1, b2
	    };
	    Arbitrator arbitrator = new Arbitrator(behaviorList, true);
	    LCD.drawString("Line Follower :)",0,1);
	    Button.waitForAnyPress();
	    arbitrator.start();

	}

}
