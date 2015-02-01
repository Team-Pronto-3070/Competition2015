package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader extends Thread implements Pronstants {

	boolean running = false;

	SpeedController load, flex;
	Joystick jRight;

	public ProntoLoader(SpeedController l, Joystick jR) {
		load = l;
		jRight = jR;
		// flex = f;
	}

	public void setRun(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (true) {
			while (running) {
				if (jRight.getRawButton(1)) {
					load.set(.5);
					// flex.set(.25);
				} else {
					load.set(0);
					// flex.set(0);
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
