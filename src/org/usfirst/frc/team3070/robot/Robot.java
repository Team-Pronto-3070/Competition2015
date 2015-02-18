package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements Pronstants {
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight;
	CANTalon mLift1, mLift2;
	CANTalon mLoader, mFlexer;

	DigitalInput upperLimit, lowerLimit, toteLimit;

	Joystick jLeft, jRight;

	PIDMechDrive mechDrive;
	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;

	CameraServer camera;

	double x, y, z, speedLevel;

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

		upperLimit = new DigitalInput(UPPER_LIMIT_ID);
		lowerLimit = new DigitalInput(LOWER_LIMIT_ID);
		toteLimit = new DigitalInput(TOTE_LIMIT_ID);

		mFrontLeft.setVoltageRampRate(RAMP_RATE);
		mFrontRight.setVoltageRampRate(RAMP_RATE);
		mRearLeft.setVoltageRampRate(RAMP_RATE);
		mRearRight.setVoltageRampRate(RAMP_RATE);

		jLeft = new Joystick(LEFT_JOYSTICK_PORT);
		jRight = new Joystick(RIGHT_JOYSTICK_PORT);

		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);

		lifter = new ProntoLift(mLift1, mLift2, upperLimit, lowerLimit,
				toteLimit, jRight);
		loader = new ProntoLoader(mLoader, jLeft, jRight);
		flexer = new ProntoFlexer(mFlexer, jLeft);

		x = 0.0;
		y = 0.0;
		z = 0.0;
		speedLevel = 0.0;
	}

	public void autonomousInit() {
		if (camera == null)
			cameraSetup();
		
		mechDrive.setControlModePosition();
		mechDrive.resetPosition();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		mechDrive.setControlModeSpeed();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		x = jLeft.getX();
		y = jLeft.getY();
		z = jRight.getX();

		getJoystickInput();

		mechDrive.drive(x, y, z, speedLevel);

		lifter.periodic();
		loader.periodic();
		flexer.periodic();

		printToSmartDashboard();
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	private void getJoystickInput() {
		speedLevel = jLeft.getZ();
	}

	private void printToSmartDashboard() {
		SmartDashboard.putBoolean(" At Top ", !ProntoLift.notAtTop);
		SmartDashboard.putBoolean(" At Bottom ", !ProntoLift.notAtBottom);
		SmartDashboard.putBoolean(" Ready For Tote ",
				ProntoLift.readyForNextTote);
		SmartDashboard.putBoolean(" Flexer Contracted ", ProntoFlexer.flexedIn);

		SmartDashboard.putNumber(" Power Percent ",
				PIDMechDrive.speedCoeff * 100);
	}

	private void cameraSetup() {
		camera = CameraServer.getInstance();
		camera.setQuality(0);
		camera.startAutomaticCapture();
	}
}
