package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements Pronstants {

	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight, mLift1, mLift2,
			mLoader, mFlexer;
	Joystick jLeft, jRight;
	PIDMechDrive mechDrive, autoDrive;
	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;
	DigitalInput upperlimit, lowerlimit, totelimit;
	
	double x, y, z;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID);
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID);
		mRearLeft = new CANTalon(M_REAR_LEFT_ID);
		mRearRight = new CANTalon(M_REAR_RIGHT_ID);
		mLift1 = new CANTalon(M_LIFT1_ID);
		mLift2 = new CANTalon(M_LIFT2_ID);
		mLoader = new CANTalon(M_LOADER_ID);
		mFlexer = new CANTalon(M_FLEXER_ID);
		
		mFrontLeft.setVoltageRampRate(RAMP_RATE);
		mFrontRight.setVoltageRampRate(RAMP_RATE);
		mRearRight.setVoltageRampRate(RAMP_RATE);
		mRearLeft.setVoltageRampRate(RAMP_RATE);

		jLeft = new Joystick(LEFT_JOYSTICK_PORT);
		jRight = new Joystick(RIGHT_JOYSTICK_PORT);
		
		upperlimit = new DigitalInput(1);
		lowerlimit = new DigitalInput(2);
		totelimit = new DigitalInput(3);

		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);
		
		autoDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft, mRearRight);

		lifter = new ProntoLift(mLift1, mLift2, upperlimit, lowerlimit, totelimit, jRight);
		loader = new ProntoLoader(mLoader, jLeft, jRight);
		flexer = new ProntoFlexer(mFlexer, jLeft);

		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public void autonomousInit() {
		autoDrive.setControlModePosition();
		autoDrive.resetPosition();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		autoDrive.positionDrive(-3000, 0, 0);
		System.out.println(autoDrive.frontLeft.getPosition());
		System.out.println(autoDrive.frontRight.getPosition());
		System.out.println(autoDrive.rearLeft.getPosition());
		System.out.println(autoDrive.rearRight.getPosition());
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
	}
	
	public void teleopInit() {
		autoDrive.setControlModeSpeed();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// SmartDashboard.putNumber("Forward Left Voltage: ", mFrontLeft.getOutputVoltage());
		// SmartDashboard.putNumber("Forward Right Voltage: ", mFrontRight.getOutputVoltage());
		// SmartDashboard.putNumber("Rear Left Voltage: ", mRearLeft.getOutputVoltage());
		// SmartDashboard.putNumber("Rear Right Voltage: ", mRearRight.getOutputVoltage());
		// SmartDashboard.putData("Mechanum Drive PID:", mechDrive);
		// I don't know why this doesn't work D:
		
		x = jLeft.getX();
		y = jLeft.getY();
		z = jRight.getX();

		mechDrive.drive(x, y, z);

		lifter.periodic();
		loader.periodic();
		flexer.periodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}
}
