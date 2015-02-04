package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

public class MultiMotorPID {

	PIDController frontLeft, frontRight, backLeft, backRight;

	public MultiMotorPID(double kP, double kI, double kD, PIDSource sensor, CANTalon motor1, CANTalon motor2) {
		motor1.reverseOutput(true);
		
		frontLeft = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor1));
		frontRight = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor2));

	}

	public MultiMotorPID(double kP, double kI, double kD, PIDSource sensor, CANTalon motor1, CANTalon motor2,
			CANTalon motor3, CANTalon motor4) {
		
		motor1.reverseOutput(true);
		motor3.reverseOutput(true);

		frontLeft = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor1));
		frontRight = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor2));
		backLeft = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor3));
		backRight = new PIDController(kP, kI, kD, sensor, new RateControllerMotor(motor4));

	}

	public void enableAll() {
		if (backLeft != null) {
			frontLeft.enable();
			frontRight.enable();
			backLeft.enable();
			backRight.enable();
		} else {
			frontLeft.enable();
			frontRight.enable();
		}
	}

	public void disableAll() {
		if (backLeft != null) {
			frontLeft.disable();
			frontRight.disable();
			backLeft.disable();
			backRight.disable();
		} else {
			frontLeft.disable();
			frontRight.disable();
		}
	}

	public void setTarget(double target) {
		if (backLeft != null) {
			frontLeft.setSetpoint(target);
			frontRight.setSetpoint(target);
			backLeft.setSetpoint(target);
			backRight.setSetpoint(target);
		} else {
			frontLeft.setSetpoint(target);
			frontRight.setSetpoint(target);
		}
	}

}
