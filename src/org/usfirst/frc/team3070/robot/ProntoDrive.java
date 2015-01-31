package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoDrive extends Thread {

	private static final double DEADZONE = 0.2;

	double kP = 0.0125;
	double kI = 0.0;
	double kD = 0.0;

	SpeedController motorFrontLeft, motorFrontRight, motorBackLeft,
			motorBackRight;
	Joystick xbox;
	Encoder enFrontLeft, enFrontRight, enBackLeft, enBackRight;

	RobotDrive mechDrive;
	// PIDController PIDFrontLeft, PIDFrontRight, PIDBackLeft, PIDBackRight;

	boolean running = false;
	double x, y, z;

	public ProntoDrive(SpeedController m1, SpeedController m2,
			SpeedController m3, SpeedController m4, /* Encoder fL, Encoder fR,
			Encoder bL, Encoder bR, */ Joystick xb) {

		// motors (CANTalons)
		motorFrontLeft = m1;
		motorFrontRight = m2;
		motorBackLeft = m3;
		motorBackRight = m4;

		/*
		// Encoders
		enFrontLeft = fL;
		enFrontRight = fR;
		enBackLeft = bL;
		enBackRight = bR;
		*/

		// Joystick
		xbox = xb;

		// RobotDrive
		mechDrive = new RobotDrive(motorFrontLeft, motorBackLeft,
				motorFrontRight, motorBackRight);
		mechDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		mechDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);

		/*
		//PIDs
		PIDFrontLeft = new PIDController(kP, kI, kD, enFrontLeft,
				new RateControllerMotor(motorFrontLeft));
		PIDFrontRight = new PIDController(kP, kI, kD, enFrontRight,
				new RateControllerMotor(motorFrontRight));
		PIDBackLeft = new PIDController(kP, kI, kD, enBackLeft,
				new RateControllerMotor(motorBackLeft));
		PIDBackRight = new PIDController(kP, kI, kD, enBackRight,
				new RateControllerMotor(motorBackRight));
				
		*/

		// initial motor outputs
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	public void setRun(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (true) {
			while (running) {
				x = xbox.getRawAxis(Robot.LEFT_X);
				y = xbox.getRawAxis(Robot.LEFT_Y);
				z = xbox.getRawAxis(Robot.RIGHT_X);
				
				checkDeadzones();
				
				mechDrive.mecanumDrive_Cartesian(x, y, z, 0);

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
	
	private void checkDeadzones() {
		if (Math.abs(x) < DEADZONE) {
			x = 0;
		}

		if (Math.abs(y) < DEADZONE) {
			y = 0;
		}

		if (Math.abs(z) < DEADZONE) {
			z = 0;
		}
	}

}
