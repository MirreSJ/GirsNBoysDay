package org.nxt.pathfinder;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class LineFollower {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CancelationToken cancellationToken = new CancelationToken();
	    Behavior b1 = new DriveForward(cancellationToken);
	    Behavior b2 = new DetectLine();
	    Behavior[] behaviorList =
	    {
	      b2
	    };
	    Arbitrator arbitrator = new Arbitrator(behaviorList);
	    LCD.drawString("Line Follower :)",0,1);
	    Button.waitForAnyPress();
	    arbitrator.start();

	}

}
