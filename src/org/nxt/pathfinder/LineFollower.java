package org.nxt.pathfinder;

import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.PilotProps;


public class LineFollower {

	private static RegulatedMotor leftMotor = Motor.A;
	private static RegulatedMotor rightMotor = Motor.C;	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CancelationToken cancellationToken = new CancelationToken();
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
		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter,trackWidth,leftMotor,rightMotor,reverse);
		
	    Behavior b1 = new DriveForward(cancellationToken, pilot);
	    Behavior b2 = new DetectLine(cancellationToken, pilot);
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
