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
	// creating the CANTalon objects

	DigitalInput upperLimit, lowerLimit, toteLimit;
	// creating the limit switches

	Joystick jLeft, jRight;
	// creating joysticks

	PIDMechDrive mechDrive;
	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;
	// creating custom classes

	CameraServer camera;
	// creating camera
	
	int autoState = 0;

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
		// setting the CANTAlons
		
		mFrontLeft.setVoltageRampRate(RAMP_RATE);
		mFrontRight.setVoltageRampRate(RAMP_RATE);
		mRearLeft.setVoltageRampRate(RAMP_RATE);
		mRearRight.setVoltageRampRate(RAMP_RATE);
		// setting the motor ramp rates

		jLeft = new Joystick(LEFT_JOYSTICK_PORT);
		jRight = new Joystick(RIGHT_JOYSTICK_PORT);
		// setting joysticks
		
		upperLimit = new DigitalInput(UPPER_LIMIT_ID);
		lowerLimit = new DigitalInput(LOWER_LIMIT_ID);
		toteLimit = new DigitalInput(TOTE_LIMIT_ID);
		// setting limit switches

		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);
		lifter = new ProntoLift(mLift1, mLift2, upperLimit, lowerLimit,
				toteLimit, jRight);
		loader = new ProntoLoader(mLoader, jLeft, jRight);
		flexer = new ProntoFlexer(mFlexer, jLeft);
		// setting drive, load, lift, and flex classes

		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	public void autonomousInit() {
		if (camera == null)
			cameraSetup();
		// starts camera recording
		
		mechDrive.setControlModePosition();
		// set drive to move based on position input
		
		mechDrive.resetPosition();
		// zero the encoders
		
		autoState = 1;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// Example: mechDrive.setPos(0, 2000, 0);
		// goes 2000 encoder units straight
		
		//if in the first state
		if (autoState == 1){
			//drives back(hopefully) 200 encoder units, which may or may not be enough to make it into the scoring zone, need to test.
			driveBack();
		}
	}

	public void teleopInit() {
		mechDrive.setControlModeSpeed();
		// set for velocity input
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		getJoystickInput();
		// assigns values to x, y, and based on joystick input

		mechDrive.drive(x, y, z);
		// sets the motors to a velocity based on joystick input

		lifter.periodic();
		loader.periodic();
		flexer.periodic();
		// operate the other functions of the robot depending on button input

		printToSmartDashboard();
		// updates smartdashboard values
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
		// stops the other functions on the robot upon disable
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	// assigning joystick variables to joystick input
	private void getJoystickInput() {
		x = jLeft.getX();
		y = jLeft.getY();
		z = jRight.getX();
	}

	// prints the given values to smart dashboard
	private void printToSmartDashboard() {
		SmartDashboard.putBoolean(" At Top ", !ProntoLift.notAtTop);
		// displays if cars are at absolute upper limit
		
		SmartDashboard.putBoolean(" At Bottom ", !ProntoLift.notAtBottom);
		// displays if cars are at absolute lower limit
		
		SmartDashboard.putBoolean(" Ready For Tote ",
				ProntoLift.readyForNextTote);
		// displays if cars are in position for a tote
		
		SmartDashboard.putBoolean(" Flexer Contracted ", ProntoFlexer.flexedIn);
		// displays if the arms are contracted
	}

	// sets up the camera
	private void cameraSetup() {
		camera = CameraServer.getInstance();
		camera.setQuality(0);
		camera.startAutomaticCapture();
		// start recording
	}
}
