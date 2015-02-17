package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class PIDMechDrive implements Pronstants {
	
	public CANTalon frontLeft, frontRight, rearLeft, rearRight;
	static double speedCoeff;
	
	public PIDMechDrive(CANTalon fL, CANTalon fR, CANTalon rL, CANTalon rR) {
		frontLeft = fL;
		frontRight = fR;
		rearLeft = rL;
		rearRight = rR;
		
		frontLeft.changeControlMode(CANTalon.ControlMode.Speed);
		frontRight.changeControlMode(CANTalon.ControlMode.Speed);
		rearLeft.changeControlMode(CANTalon.ControlMode.Speed);
		rearRight.changeControlMode(CANTalon.ControlMode.Speed);
		
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		frontRight.reverseSensor(true);
		rearRight.reverseSensor(true);
		frontLeft.reverseSensor(false);
		rearLeft.reverseSensor(false);
		
		frontLeft.reverseOutput(true);
		rearLeft.reverseOutput(true);

		frontLeft.setPID(KP, KI, KD);
		frontRight.setPID(KP, KI, KD);
		rearLeft.setPID(KP, KI, KD);
		rearRight.setPID(KP, KI, KD);
		
		speedCoeff = 0.0;
	}
	
	public void drive(double x, double y, double rotation, double spdLvl) {
		x = checkForDeadzone(x);
		y = checkForDeadzone(y);
		rotation = checkForDeadzone(-rotation);
		speedCoeff = convertToSpeedLevel(spdLvl);
				
		x = convertToEncValue(x);
		y = convertToEncValue(y);
		rotation = convertToEncValue(rotation);
		
		frontLeft.set(y);
		frontRight.set(y);
		rearLeft.set(y);
		rearRight.set(y);
		
//		frontLeft.set(x + y + rotation);		
//		frontRight.set(-x + y - rotation);		
//		rearLeft.set(-x + y + rotation);
//		rearRight.set(x + y - rotation);
		
		System.out.println("FL: " + frontLeft.getEncVelocity());
		System.out.println("FR: " + frontRight.getEncVelocity());
		System.out.println("RL: " + rearLeft.getEncVelocity());
		System.out.println("RR: " + rearRight.getEncVelocity());
	}
	
	public void setPos(double x, double y, double rotation) {
		frontLeft.set(x + y + rotation);		
		frontRight.set(-x + y - rotation);		
		rearLeft.set(-x + y + rotation);
		rearRight.set(x + y - rotation);
	}
	
	public void resetPosition() {
		frontLeft.setPosition(0);
		frontRight.setPosition(0);
		rearLeft.setPosition(0);
		rearRight.setPosition(0);
	}
	
	public void setControlModeSpeed() {
		frontLeft.changeControlMode(CANTalon.ControlMode.Speed);
		frontRight.changeControlMode(CANTalon.ControlMode.Speed);
		rearLeft.changeControlMode(CANTalon.ControlMode.Speed);
		rearRight.changeControlMode(CANTalon.ControlMode.Speed);
	}
	
	public void setControlModePosition() {
		frontLeft.changeControlMode(CANTalon.ControlMode.Position);
		frontRight.changeControlMode(CANTalon.ControlMode.Position);
		rearLeft.changeControlMode(CANTalon.ControlMode.Position);
		rearRight.changeControlMode(CANTalon.ControlMode.Position);
	}
	
	public void setAllPID(double kP, double kI, double kD) {
		frontLeft.setPID(kP, kI, kD);
		frontRight.setPID(kP, kI, kD);
		rearLeft.setPID(kP, kI, kD);
		rearRight.setPID(kP, kI, kD);
	}

	private double checkForDeadzone(double a) {
		if (Math.abs(a) < DEADZONE) {
			a = 0.0;
		}
		
		return a;
		
	}

	private double convertToEncValue(double x) {
		return x * ENCODER_MAX_SPEED * speedCoeff;
	}
	
	private double convertToSpeedLevel(double x) {
		if (x < 0) {
			x = (x * -0.5) + 0.5;
		} else if (x > 0) {
			x = 0.5 - (x * 0.5);
		} else {
			x = 0.5;
		}
		
		return x;
	}

}
