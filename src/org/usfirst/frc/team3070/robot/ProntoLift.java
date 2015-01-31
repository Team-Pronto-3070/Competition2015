package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift extends Thread {
	
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
				if (xbox.getRawButton(1)) { // A
					motor1.set(1);
					motor2.set(-1);
				} else {
					motor1.set(0);
					motor2.set(0);
				}
				
				if (xbox.getRawButton(2)) { // B
					motor1.set(-1);
					motor2.set(1);
				} else {
					motor1.set(0);
					motor2.set(0);
				}
				
				try {
					Thread.sleep(10);
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
