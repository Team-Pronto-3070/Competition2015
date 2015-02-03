package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift extends Thread implements Pronstants {

	boolean running = false;
	SpeedController motor1, motor2;
	Joystick xbox;

	public ProntoLift(SpeedController m1, SpeedController m2, Joystick x) {
		motor1 = m1;
		motor2 = m2;
		xbox = x;
	}

	public void setRun(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (true) {
			while (running) {

				//
				//
				// Code here to raise the forklift in a non-manual way
				//
				//

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
