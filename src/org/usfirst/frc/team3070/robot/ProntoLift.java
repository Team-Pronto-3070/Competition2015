package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift extends Thread implements Pronstants {

	boolean running = false;
	SpeedController motor1, motor2;
	Joystick xbox;

=======
import edu.wpi.first.wpilibj.Joystick.RumbleType;
>>>>>>> origin/master
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift extends Thread implements Pronstants {
	
	boolean running = false;
	SpeedController motor1, motor2;
	Joystick xbox;
	
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
	public ProntoLift(SpeedController m1, SpeedController m2, Joystick x) {
		motor1 = m1;
		motor2 = m2;
		xbox = x;
	}
<<<<<<< HEAD

	public void setRun(boolean run) {
		running = run;
	}

=======
	
	public void setRun(boolean run) {
		running = run;
	}
	
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
	@Override
	public void run() {
		while (true) {
			while (running) {
<<<<<<< HEAD

				//
				//
				// Code here to raise the forklift in a non-manual way
				//
				//

=======
				if (xbox.getRawButton(A_BUTTON)) {
					liftUp();
				} else {
					if (xbox.getRawButton(B_BUTTON)) { 
						liftDown();
					} else {
						liftStop();
					}
				}
				
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
				try {
					Thread.sleep(20);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
<<<<<<< HEAD

=======
			
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
<<<<<<< HEAD
=======
	
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

>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
}
