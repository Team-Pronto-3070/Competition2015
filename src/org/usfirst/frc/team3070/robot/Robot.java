package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

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

	int autoState;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		// these are the ports
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID); // 3
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID); // 2
		mRearLeft = new CANTalon(M_REAR_LEFT_ID); // 5
		mRearRight = new CANTalon(M_REAR_RIGHT_ID);// 4
		mLift1 = new CANTalon(M_LIFT1_ID); // 8
		mLift2 = new CANTalon(M_LIFT2_ID); // 9
		mLoader = new CANTalon(M_LOADER_ID); // 7
		mFlexer = new CANTalon(M_FLEXER_ID); // 6

		xbox = new Joystick(JOYSTICK_PORT);// 1

		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);

		lifter = new ProntoLift(mLift1, mLift2, xbox);
		loader = new ProntoLoader(mLoader, xbox);
		flexer = new ProntoFlexer(mFlexer, xbox);

		x = 0.0;
		y = 0.0;
		z = 0.0;

		autoState = 0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		/*
		 * CameraServer camera = CameraServer.getInstance();
		 * camera.setQuality(10); camera.startAutomaticCapture("cam0");
		 */
		switch (autoState) {
		case 0:
			// drive forward
			driveStraight(30);
			break;
		case 1:
			// lift crate up
			Lift(30);
			break;
		case 2:
			// drive backward
			driveStraight(-100);
			break;
		case 3:
			// set crate down
			Lift(-35);
			break;
		case 4:
			// drive backward
			driveStraight(-50);
			break;
		default:
			break;
		}
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		x = xbox.getRawAxis(LEFT_X);// 0
		y = xbox.getRawAxis(LEFT_Y);// 1
		z = xbox.getRawAxis(RIGHT_X);// 4

		// .getPOV() gets info from the dpad, looks like a +

		// -1
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

	public void driveStraight(double x) {
		for (int i = 0; i < Math.abs(x); i++) {
			mFrontLeft.set(.5);
			mFrontRight.set(-.5);
			mRearLeft.set(.5);
			mRearRight.set(-.5);
		}
		mFrontLeft.set(0);
		mFrontRight.set(0);
		mRearLeft.set(0);
		mRearRight.set(0);
		autoState++;
	}

	public void Lift(double x) {
		for (int i = 0; i < Math.abs(x); i++) {
			mLift1.set(.5);
			mLift2.set(-.5);
		}
		mLift1.set(0);
		mLift2.set(0);
		autoState++;
	}
}
