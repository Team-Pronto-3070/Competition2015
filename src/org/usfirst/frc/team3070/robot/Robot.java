package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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

	Timer timer;

	int autoState = 0;

	double x, y, z; // it appears that x indicates forward/backward; y indicates
					// left/right; z indicates turn left/right (JDS)

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID); // front left wheel motor
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID); // front right wheel motor
		mRearLeft = new CANTalon(M_REAR_LEFT_ID); // rear left wheel motor
		mRearRight = new CANTalon(M_REAR_RIGHT_ID); // rear right wheel motor
		mLift1 = new CANTalon(M_LIFT1_ID); // two motors drive the lifter,
		mLift2 = new CANTalon(M_LIFT2_ID); // ... and here is the second motor
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

		timer = new Timer();

		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	public void autonomousInit() {
		// if (camera == null)
		// cameraSetup();
		// // starts camera recording

		// mechDrive.setControlModePosition();
		// // set drive to move based on position input
		// mechDrive.resetPosition();
		// // zero the encoders

		timer.reset();
		timer.start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// Example: mechDrive.drive(0, 1, 0);
		// Makes the robot drive at a velocity of one

		if (timer.get() < .5) {
			mechDrive.drive(0, -.75, 0);
		} else if (timer.get() >= .5 && timer.get() <= 1.25) {
			mechDrive.drive(0,  0,  0);
			mLift1.set(-LIFT_SPEED);
			mLift2.set(-LIFT_SPEED);
		} else if (timer.get() > 1.25 && timer.get() < 4.75) {
			mechDrive.drive(0, .75, 0);
			mLift1.set(0.0);
			mLift2.set(0.0);
		} else {
			mechDrive.drive(0, 0, 0);
			timer.stop();
			mLift1.set(0.0);
			mLift2.set(0.0);
		}

	}

	public void teleopInit() {
		mechDrive.setControlModeSpeed();
		// set for velocity input

		mechDrive.setAllPID(KP, KI, KD);
		// set P to driving value
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
		// loader.periodic();
		// flexer.periodic();
		// operate the other functions of the robot depending on button input

		printToSmartDashboard();
		// updates smartdashboard values
	}

	public void disabledInit() {
		lifter.stopPeriodic();
		// loader.stopPeriodic();
		// flexer.stopPeriodic();
		// stops the other functions on the robot upon disable
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		if (camera == null)
			cameraSetup();
		// starts camera recording
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
		camera.setQuality(100);
//		camera.startAutomaticCapture();
//		// start recording
	}

	private void readyLifter() {
		// set cars fully up
		while (upperLimit.get()) {
			mLift1.set(-LIFT_SPEED);
			mLift2.set(-LIFT_SPEED);
		}

		// stop cars
		mLift1.set(0);
		mLift2.set(0);

		// position a car to tote level
		while (toteLimit.get()) {
			mLift1.set(LIFT_SPEED);
			mLift2.set(LIFT_SPEED);
		}

		// stop the lift
		mLift1.set(0);
		mLift2.set(0);

		// move down past the tote limit
		while (!toteLimit.get()) {
			mLift1.set(-0.4);
			mLift2.set(-0.4);
		}

		mLift1.set(0);
		mLift2.set(0);

		autoState++;
	}

	private void moveForward(int distance) {
		// moves forward in increments of 16 inches
		for (int i = 0; i < distance; i++) {
			while (mechDrive.rearLeft.getPosition() != -1000) {
				mechDrive.setPos(0, -1000, 0);
				// forward is negative distance
			}
			mechDrive.resetPosition();
		}

		autoState++;
	}

	private void moveBackward(int distance) {
		// moves backward in increments of 16 inches
		for (int i = 0; i < distance; i++) {
			while (mechDrive.rearLeft.getPosition() != 1000) {
				mechDrive.setPos(0, 1000, 0);
				// backward is positive distance
			}
			mechDrive.resetPosition();
		}

		autoState++;
	}

	private void liftUp() {
		while (upperLimit.get()) {
			// while not fully up, lifting up
			mLift1.set(-LIFT_SPEED);
			mLift2.set(-LIFT_SPEED);
		}

		mLift1.set(0);
		mLift2.set(0);

		autoState++;
	}

	private void liftDown() {
		while (toteLimit.get()) {
			// while not at the tote level, lifting down
			mLift1.set(LIFT_SPEED);
			mLift2.set(LIFT_SPEED);
		}

		while (!lowerLimit.get()) {
			// move past the tote limit
			mLift1.set(LIFT_SPEED);
			mLift2.set(LIFT_SPEED);
		}

		mLift1.set(0);
		mLift2.set(0);

		autoState++;

		// tote should be unhooked
	}
}
