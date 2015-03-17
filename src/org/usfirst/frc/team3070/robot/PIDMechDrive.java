package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class PIDMechDrive implements Pronstants {
	
	public CANTalon frontLeft, frontRight, rearLeft, rearRight;
	
	public PIDMechDrive(CANTalon fL, CANTalon fR, CANTalon rL, CANTalon rR) {
		frontLeft  = fL;
		frontRight = fR;
		rearLeft   = rL;
		rearRight  = rR;
		// assigning class motors to given motors
		
		setControlModeSpeed();
		// PIDMechDrive defaults to velocity drive
		
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		// sets the sensor for PID to be the encoders on the CANTalons
		
		frontRight.reverseSensor(true);
		rearRight.reverseSensor(false);
		frontLeft.reverseSensor(false);
		rearLeft.reverseSensor(true);
		// these encoders need to have their values reversed to operate properly
		
		frontLeft.reverseOutput(false);
		rearLeft.reverseOutput(true);
		frontRight.reverseOutput(false);
		rearRight.reverseOutput(true);
		// these motors need to have their values reversed to operate properly

		setAllPID(KP, KI, KD);
		// P is the only value used, I and D are 0
	}
	
	public void drive(double rotation, double y, double x) {
		x = checkForDeadzone(x);
		y = checkForDeadzone(y);
		rotation = checkForDeadzone(rotation);
		// sets motors to 0 if joystick input is within the set deadzone
				
		x = convertToEncValue(x);
		y = convertToEncValue(y);
		rotation = convertToEncValue(rotation);
		// convert the joystick input to an encoder setting
				
		frontLeft.set(x + y + rotation);		
		frontRight.set(-x + y - rotation);		
		rearLeft.set(-x + y + rotation);
		rearRight.set(x + y - rotation);
		// calculations for setting each wheel to the right velocity
	}
	
	// use setPos for moving the robot in autonomous
	public void setPos(double x, double y, double rotation) {
		frontLeft.set(x + y + rotation);		
		frontRight.set(-x + y - rotation);		
		rearLeft.set(-x + y + rotation);
		rearRight.set(x + y - rotation);
	}
	
	// zero all the encoder position readings
	public void resetPosition() {
		frontLeft.setPosition(0);
		frontRight.setPosition(0);
		rearLeft.setPosition(0);
		rearRight.setPosition(0);
	}
	
	// sets motors to output as velocity
	public void setControlModeSpeed() {
		frontLeft.changeControlMode(CANTalon.ControlMode.Speed);
		frontRight.changeControlMode(CANTalon.ControlMode.Speed);
		rearLeft.changeControlMode(CANTalon.ControlMode.Speed);
		rearRight.changeControlMode(CANTalon.ControlMode.Speed);
	}
	
	// sets motors to output as position
	public void setControlModePosition() {
		frontLeft.changeControlMode(CANTalon.ControlMode.Position);
		frontRight.changeControlMode(CANTalon.ControlMode.Position);
		rearLeft.changeControlMode(CANTalon.ControlMode.Position);
		rearRight.changeControlMode(CANTalon.ControlMode.Position);
	}
	
	// sets the PID values
	public void setAllPID(double kP, double kI, double kD) {
		frontLeft.setPID(kP, kI, kD);
		frontRight.setPID(kP, kI, kD);
		rearLeft.setPID(kP, kI, kD);
		rearRight.setPID(kP, kI, kD);
	}

	// outputs 0 if within deadzone constraint
	private double checkForDeadzone(double a) {
		if (Math.abs(a) < DEADZONE) {
			a = 0.0;
		}
		
		a = Math.pow(a, 3);
		/*
		 * cubes a to improve sensitivity driving
		 * low joystick input yields low motor output
		 * high joystick input yields high motor output
		 * 
		 * changes CANTalons from linear output to cubic output
		 */
		
		return a;
	}
	
	// multiplies joystick input by maximum Encoder speed
	private double convertToEncValue(double x) {
		return x * ENCODER_MAX_SPEED;
		/*
		 * the greatest that joystick input can be is 1 and all
		 * other values are decimals.
		 */
	}
}
