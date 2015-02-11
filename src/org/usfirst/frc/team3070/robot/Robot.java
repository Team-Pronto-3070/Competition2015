package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;

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
	Joystick xbox;
	PIDMechDrive mechDrive;
	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;

	double x, y, z;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID); //port 3
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID); //port 2
		mRearLeft = new CANTalon(M_REAR_LEFT_ID); //port 5
		mRearRight = new CANTalon(M_REAR_RIGHT_ID); //port 4
		mLift1 = new CANTalon(M_LIFT1_ID); //port 8
		mLift2 = new CANTalon(M_LIFT2_ID); //port 9
		mLoader = new CANTalon(M_LOADER_ID); //port 7
		mFlexer = new CANTalon(M_FLEXER_ID); //port 6

		xbox = new Joystick(JOYSTICK_PORT);

		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);

		lifter = new ProntoLift(mLift1, mLift2, xbox);
		loader = new ProntoLoader(mLoader, xbox);
		flexer = new ProntoFlexer(mFlexer, xbox);

		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		CameraServer camera = CameraServer.getInstance();
		camera.setQuality(10);
		camera.startAutomaticCapture("cam0");
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
	}

	/**
	 * This function is called periodically during operator control
	 */
	//
	public void teleopPeriodic() {
		x = xbox.getRawAxis(LEFT_X);
		y = xbox.getRawAxis(LEFT_Y);
		z = xbox.getRawAxis(RIGHT_X);

		//sets mechanum drive to dpad value and if !pressed sets to joystick values
		if (xbox.getPOV() == NO_DPAD_INPUT) {
			mechDrive.drive(x, y, z);
		} else {
			mechDrive.dPadDrive(xbox.getPOV());
		}

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
