package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends IterativeRobot implements Pronstants {
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight; //mecanum wheel motors
	CANTalon mLift1, mLift2; //lift motors
	CANTalon mLoader, mFlexer; //loader and flexer motors
	
	DigitalInput upperlimit, lowerlimit, totelimit; //reed switches
	
	Joystick jLeft, jRight; //joysticks

	//instances of mecanum wheels, lifter, loader, and flexer
	PIDMechDrive mechDrive; 

	ProntoLift lifter;
	ProntoLoader loader;
	ProntoFlexer flexer;
	
	//mecanum wheel variables
	double x, y, z;
	
	//autonomous state
	int autoState = 0;
	
	public void robotInit() {
		mFrontLeft = new CANTalon(M_FRONT_LEFT_ID); //3
		mFrontRight = new CANTalon(M_FRONT_RIGHT_ID); //2
		mRearLeft = new CANTalon(M_REAR_LEFT_ID); //5
		mRearRight = new CANTalon(M_REAR_RIGHT_ID); //4
		mLift1 = new CANTalon(M_LIFT1_ID); //8
		mLift2 = new CANTalon(M_LIFT2_ID); //9
		mLoader = new CANTalon(M_LOADER_ID); //7
		mFlexer = new CANTalon(M_FLEXER_ID); //6
		
		mFrontLeft.setVoltageRampRate(RAMP_RATE); //30
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
				mRearRight); //declares the mecanum drive
		
		//assigns the variables to the lifter, loader, and flexer
		lifter = new ProntoLift(mLift1, mLift2, upperlimit, lowerlimit, totelimit, jRight);
		loader = new ProntoLoader(mLoader, jLeft, jRight);
		flexer = new ProntoFlexer(mFlexer, jLeft);

		//mecanum drive variables
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public void autonomousInit() {
		mechDrive.setControlModePosition();
		mechDrive.resetPosition();
		//sets it to the first state
		autoState = 1;
	}

	public void autonomousPeriodic() {
		//if in the first state
		if (autoState == 1){
			
		}
	}
	
	public void teleopInit() {
		mechDrive.setControlModeSpeed();
	}

	public void teleopPeriodic() {
		//checks if anything is triggered
		getJoystickInput();

		//move
		mechDrive.drive(x, y, z);

		//checks for updates for lifter, loader, and flexer
		lifter.periodic(); 
		loader.periodic();
		flexer.periodic();
		
		//Junk code
		printToSmartDashboard();
	}

	public void disabledInit() {
		//stops lifter, loader, and flexer
		lifter.stopPeriodic();
		loader.stopPeriodic();
		flexer.stopPeriodic();
	}

	public void testPeriodic() {

	}
	
	//ignore this
	private void printToSmartDashboard(){
		SmartDashboard.putBoolean(" At Top ", !ProntoLift.notAtTop);
		SmartDashboard.putBoolean(" At Bottom ", !ProntoLift.notAtBottom);
		SmartDashboard.putBoolean(" Ready For Tote ", ProntoLift.readyForNextTote);
		SmartDashboard.putBoolean(" Flexer Contracted ", ProntoFlexer.flexedIn);
	}
	
	//Checks if the joysticks have moved at all
	private void getJoystickInput(){
		x = jLeft.getX();
		y = jLeft.getY();
		z = jRight.getX();
	}
	
	private void pickUp(){
		
	}
	
	private void driveBack(){
		mechDrive.setPos(0.0, -200.0, 0.0);
	}
}
