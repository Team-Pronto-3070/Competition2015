package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader extends Thread implements Pronstants {

	boolean running = false;

	SpeedController load, flex;
	Joystick xbox;

	public ProntoLoader(SpeedController l, SpeedController f, Joystick x) {
		load = l;
		flex = f;
		xbox = x;
	}

	public void setRun(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (true) {
			while (running) {				
				if (xbox.getRawButton(LEFT_BUMPER)) {
					load.set(LOAD_SPEED);
				} else if (xbox.getRawButton(RIGHT_BUMPER)) {
					load.set(-LOAD_SPEED);
				} else {
					load.set(0);
				}
				
				if (xbox.getRawButton(3)) {
					flex.set(FLEX_SPEED);
				} else if (xbox.getRawButton(4)) {
					flex.set(-FLEX_SPEED);
				} else {
					flex.set(0);
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
