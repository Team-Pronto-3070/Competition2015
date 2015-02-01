package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift extends Thread implements Pronstants {
	
	boolean running = false;
	SpeedController motor1, motor2;
	Joystick jLeft, jRight;
	
	public ProntoLift(SpeedController m1, SpeedController m2, Joystick jL, Joystick jR) {
		motor1 = m1;
		motor2 = m2;
		jLeft = jL;
		jRight = jR;
	}
	
	public void setRun(boolean run) {
		running = run;
	}
	
	@Override
	public void run() {
		while (true) {
			while (running) {
				if (jRight.getRawButton(3)) {
					liftUp();
				} else if (jRight.getRawButton(2)) { 
					liftDown();
				} else {
					liftStop();
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
	}
	
	private void liftDown() {
		motor1.set(-LIFT_SPEED);
		motor2.set(LIFT_SPEED);
	}
	
	private void liftStop() {
		motor1.set(0);
		motor2.set(0);
	}

}
