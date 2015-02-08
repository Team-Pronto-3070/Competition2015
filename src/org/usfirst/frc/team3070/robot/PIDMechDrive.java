package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class PIDMechDrive implements Pronstants {
	
	public CANTalon frontLeft, frontRight, rearLeft, rearRight;
	
	public PIDMechDrive(CANTalon fL, CANTalon fR, CANTalon rL, CANTalon rR) {
		frontLeft = fL;
		frontRight = fR;
		rearLeft = rL;
		rearRight = rR;
		
		frontLeft.changeControlMode(CANTalon.ControlMode.Speed);
		frontRight.changeControlMode(CANTalon.ControlMode.Speed);
		rearLeft.changeControlMode(CANTalon.ControlMode.Speed);
		rearRight.changeControlMode(CANTalon.ControlMode.Speed);
		
		frontRight.reverseSensor(true);
		rearRight.reverseSensor(true);
		
		frontLeft.reverseOutput(true);
		rearLeft.reverseOutput(true);
		
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rearRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		frontLeft.setPID(KP, KI, KD);
		frontRight.setPID(KP, KI, KD);
		rearLeft.setPID(KP, KI, KD);
		rearRight.setPID(KP, KI, KD);
	}
	
	public void drive(double x, double y, double rotation) {
		x = checkForDeadzone(x);
		y = checkForDeadzone(-y);
		// y inverted b/c y-axis on Joystick is negative for forward values
		rotation = checkForDeadzone(rotation);
		
		x = convertToEncValue(x);
		y = convertToEncValue(y);
		rotation = convertToEncValue(rotation);
		
		frontLeft.set(x + y + rotation);		
		frontRight.set(-x + y - rotation);		
		rearLeft.set(-x + y + rotation);		
		rearRight.set(x + y - rotation);	
	}
	
	public void dPadDrive(int pov) {
		switch (pov) {
		case DPAD_UP: //if top is pressed
			drive(0, 0.5, 0);
			break;
		case DPAD_RIGHT: //if right is pressed
			drive(0.5, 0, 0);
			break;
		case DPAD_DOWN: //if down is pressed
			drive(0, -0.5, 0);
			break;
		case DPAD_LEFT: //if left is pressed
			drive(-0.5, 0, 0);
			break;
		default:
			break;

		}
	}

	private double checkForDeadzone(double a) {
		if (Math.abs(a) < DEADZONE) { //0.2
			a = 0.0;
		}
		
		return a;
		
	}

	private double convertToEncValue(double x) {
		return x * ENCODER_MAX_SPEED; //1600
	}

}
