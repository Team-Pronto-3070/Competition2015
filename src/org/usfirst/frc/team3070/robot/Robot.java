package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
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
	//mecanum wheels
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight;
	//pulley motors
	CANTalon mLift1, mLift2;
	//loader and flexer motors
	CANTalon mLoader, mFlexer;
	
	//reed switches
	DigitalInput upperlimit, lowerlimit, totelimit;
	
	//the two joysticks
	Joystick jLeft, jRight;

	//instances of other classes
	PIDMechDrive mechDrive;
	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;
	
	//mecanum wheel variables
	double x, y, z;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID); //3
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID); //2
		mRearLeft = new CANTalon(M_REAR_LEFT_ID); //5
		mRearRight = new CANTalon(M_REAR_RIGHT_ID); //4
		mLift1 = new CANTalon(M_LIFT1_ID); //8
		mLift2 = new CANTalon(M_LIFT2_ID); //9
		mLoader = new CANTalon(M_LOADER_ID); //7
		mFlexer = new CANTalon(M_FLEXER_ID); //6
		
		mFrontLeft.setVoltageRampRate(RAMP_RATE); //all 30
		mFrontRight.setVoltageRampRate(RAMP_RATE);
		mRearRight.setVoltageRampRate(RAMP_RATE);
		mRearLeft.setVoltageRampRate(RAMP_RATE);

		jLeft = new Joystick(LEFT_JOYSTICK_PORT); //1
		jRight = new Joystick(RIGHT_JOYSTICK_PORT); //2
		
		upperlimit = new DigitalInput(UPPER_LIMIT_ID); //1
		lowerlimit = new DigitalInput(LOWER_LIMIT_ID); //2
		totelimit = new DigitalInput(TOTE_LIMIT_ID); //3

		//assigns the variables to the mecanum wheels
		mechDrive = new PIDMechDrive(mFrontLeft, mFrontRight, mRearLeft,
				mRearRight);
		
		//assigns the variables to the lifter, loader, and flexer
		lifter = new ProntoLift(mLift1, mLift2, upperlimit, lowerlimit, totelimit, jRight);
		loader = new ProntoLoader(mLoader, jLeft, jRight);
		flexer = new ProntoFlexer(mFlexer, jLeft);

		//variables for the mecanum drive
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public void autonomousInit() {
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
		getJoystickInput();

		mechDrive.drive(x, y, z);

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
	
	private void printToSmartDashboard(){
		SmartDashboard.putBoolean(" At Top ", !ProntoLift.notAtTop);
		SmartDashboard.putBoolean(" At Bottom ", !ProntoLift.notAtBottom);
		SmartDashboard.putBoolean(" Ready For Tote ", ProntoLift.readyForNextTote);
		SmartDashboard.putBoolean(" Flexer Contracted ", ProntoFlexer.flexedIn);
	}
	private void getJoystickInput(){
		x = jLeft.getX();
		y = jLeft.getY();
		z = jRight.getX();
	}
}
