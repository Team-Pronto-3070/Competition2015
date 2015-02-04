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
				if (xbox.getRawButton(A_BUTTON)) {
					liftUp();
				} else {
					if (xbox.getRawButton(B_BUTTON)) { 
						liftDown();
					} else {
						liftStop();
					}
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
	
	private void liftUp() {
		motor1.set(LIFT_SPEED);
		motor2.set(-LIFT_SPEED);
		// xbox.setRumble(RumbleType.kLeftRumble, 1);
		// xbox.setRumble(RumbleType.kRightRumble, 1);
	}
	
	private void liftDown() {
		motor1.set(-LIFT_SPEED);
		motor2.set(LIFT_SPEED);
	}
	
	private void liftStop() {
		motor1.set(0);
		motor2.set(0);
		// xbox.setRumble(RumbleType.kLeftRumble, 0);
		// xbox.setRumble(RumbleType.kRightRumble, 0);
	}

}
