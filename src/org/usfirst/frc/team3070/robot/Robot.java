package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements Pronstants {

	// Encoder enFrontLeft, enFrontRight, enBackLeft, enBackRight;

	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight, mLift1, mLift2,
			mLoader, mFlex;
	Joystick xbox;
	RobotDrive mechDrive;
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
		mFlex = new CANTalon(M_FLEX_ID);

		xbox = new Joystick(JOYSTICK_PORT);

		mechDrive = new RobotDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		if (xbox.getRawButton(LEFT_BUMPER)) {
			mLoader.set(LOAD_SPEED);
		} else if (xbox.getRawButton(RIGHT_BUMPER)) {
			mLoader.set(-LOAD_SPEED);
		} else {
			mLoader.set(0);
		}

		if (xbox.getRawButton(3)) {
			mFlex.set(FLEX_SPEED);
		} else if (xbox.getRawButton(4)) {
			mFlex.set(-FLEX_SPEED);
		} else {
			mFlex.set(0);
		}

		if (xbox.getRawButton(A_BUTTON)) {
			liftUp();
		} else if (xbox.getRawButton(B_BUTTON)) {
			liftDown();
		} else {
			liftStop();
		}
		
		x = xbox.getRawAxis(LEFT_X);
		y = xbox.getRawAxis(LEFT_Y);
		z = xbox.getRawAxis(RIGHT_X);
		
		checkDeadzones();
		
		mechDrive.mecanumDrive_Cartesian(x, y, z, 0);

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	private void liftUp() {
		mLift1.set(LIFT_SPEED);
		mLift2.set(-LIFT_SPEED);
	}

	private void liftDown() {
		mLift1.set(-LIFT_SPEED);
		mLift2.set(LIFT_SPEED);
	}

	private void liftStop() {
		mLift1.set(0);
		mLift2.set(0);
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
