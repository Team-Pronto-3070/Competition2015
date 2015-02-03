package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoDrive extends Thread implements Pronstants {

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
<<<<<<< HEAD
		PIDFrontLeft = new PIDController(Robot.KP, Robot.KI, Robot.KD, enFrontLeft,
				new RateControllerMotor(motorFrontLeft));
		PIDFrontRight = new PIDController(Robot.KP, Robot.KI, Robot.KD, enFrontRight,
				new RateControllerMotor(motorFrontRight));
		PIDBackLeft = new PIDController(Robot.KP, Robot.KI, Robot.KD, enBackLeft,
				new RateControllerMotor(motorBackLeft));
		PIDBackRight = new PIDController(Robot.KP, Robot.KI, Robot.KD, enBackRight,
=======
		PIDFrontLeft = new PIDController(KP, KI, KD, enFrontLeft,
				new RateControllerMotor(motorFrontLeft));
		PIDFrontRight = new PIDController(KP, KI, KD, enFrontRight,
				new RateControllerMotor(motorFrontRight));
		PIDBackLeft = new PIDController(KP, KI, KD, enBackLeft,
				new RateControllerMotor(motorBackLeft));
		PIDBackRight = new PIDController(KP, KI, KD, enBackRight,
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
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
				x = xbox.getRawAxis(LEFT_X);
				y = xbox.getRawAxis(LEFT_Y);
				z = xbox.getRawAxis(RIGHT_X);
				
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
