package org.nxt.pathfinder;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {
	private DifferentialPilot pilot;	
	private boolean suppressed = false;
	private CancelationToken cancelationToken;
	
	public DriveForward(CancelationToken cancelationToken, DifferentialPilot pilot) {
	    this.cancelationToken = cancelationToken;
	}
	
	public boolean takeControl() {
		return !cancelationToken.IsCancelationRequested();		
	}

	public void action() {
		suppressed = false;
		pilot.forward();
	    while (!suppressed && !cancelationToken.IsCancelationRequested())
	    {
	      Thread.yield(); //don't exit till suppressed
	    }
	    pilot.stop();
	}

	public void suppress() {
		suppressed = true;		
	}

}
