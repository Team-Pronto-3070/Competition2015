package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader extends Thread {

	boolean running = false;

	SpeedController load;
	Joystick xbox;

	public ProntoLoader(SpeedController l, Joystick x) {
		load = l;
		xbox = x;
	}

	public void setRun(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (true) {
			while (running) {
				if (xbox.getRawButton(5)) { // Left Bumper
					load.set(.5);
				} else {
					load.set(0);
				}
				
				if (xbox.getRawButton(6)) { // RightBumper
					load.set(-.5);
				} else {
					load.set(0);
				}

				try {
					Thread.sleep(20);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}
