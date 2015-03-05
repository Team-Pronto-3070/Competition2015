package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class PIDMechDrive implements Pronstants {
	
	public CANTalon frontLeft, frontRight, rearLeft, rearRight;
	
	public PIDMechDrive(CANTalon fL, CANTalon fR, CANTalon rL, CANTalon rR) {
		//mecanum motors
		frontLeft = fL;
		frontRight = fR;
		rearLeft = rL;
		rearRight = rR;
		
		setControlModeSpeed();
		
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		//accommodates for reversed sensors(the ones on the right)
		frontRight.reverseSensor(true);
		rearRight.reverseSensor(true);
		frontLeft.reverseSensor(false);
		rearLeft.reverseSensor(false);
		
		//see above
		frontLeft.reverseOutput(true);
		rearLeft.reverseOutput(true);
		frontRight.reverseOutput(false);		
		rearRight.reverseOutput(false);		
		
		//sets PID for motors(KP=1.25,KI=0,KD=0
		frontLeft.setPID(KP, KI, KD);
		frontRight.setPID(KP, KI, KD);
		rearLeft.setPID(KP, KI, KD);
		rearRight.setPID(KP, KI, KD);
	}
	
	//makes the robot drive around
	public void drive(double x, double y, double rotation) {
		//sets deadzones for joysticks
		x = checkForDeadzone(x);
		y = checkForDeadzone(-y);
		// y inverted b/c y-axis on Joystick is negative for forward values
		rotation = checkForDeadzone(rotation);
		
		//multiplies # by 1600 for encoder distances
		x = convertToEncValue(x);
		y = convertToEncValue(y);
		rotation = convertToEncValue(rotation);
		
		//drives
		frontLeft.set(x + y + rotation);
		frontRight.set(-x + y - rotation);
		rearLeft.set(-x + y + rotation);
		rearRight.set(x + y - rotation);
	}
	
	//Sets robot to a specific location
	public void setPos(double x, double y, double rotation) {
		frontLeft.set(x + y + rotation);
		frontRight.set(-x + y - rotation);
		rearLeft.set(-x + y + rotation);
		rearRight.set(x + y - rotation);
	}

	//resets encoders
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
	
	//
	public void setControlModePosition() {
		frontLeft.changeControlMode(CANTalon.ControlMode.Position);
		frontRight.changeControlMode(CANTalon.ControlMode.Position);
		rearLeft.changeControlMode(CANTalon.ControlMode.Position);
		rearRight.changeControlMode(CANTalon.ControlMode.Position);
	}

	//Sets all wheels to same PID
	public void setAllPID(double kP, double kI, double kD){
		frontLeft.setPID(kP, kI, kD);
		frontRight.setPID(kP, kI, kD);
		rearLeft.setPID(kP, kI, kD);
		rearRight.setPID(kP, kI, kD);
	}
	
	//Check for deadzone
	private double checkForDeadzone(double a) {
		if (Math.abs(a) < DEADZONE) {
			a = 0.0;
		}
		a = Math.pow(a, 3);
		
		return a;
		
	}

	//Multiplies x by 1600
	private double convertToEncValue(double x) {
		return x * ENCODER_MAX_SPEED;
	}
	
	
}
