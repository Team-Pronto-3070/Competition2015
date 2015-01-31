
package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	
	public static final int RIGHT_X = 4;
	
	//Encoder enFrontLeft, enFrontRight, enBackLeft, enBackRight;
	
	ProntoDrive drive;
	ProntoLift lift;
	ProntoLoader loader;
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight, mLift1, mLift2, mLoader;
	Joystick xbox;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	mFrontLeft = new CANTalon(3);
    	mFrontRight = new CANTalon(2);
    	mRearLeft = new CANTalon(5);
    	mRearRight = new CANTalon(4);
    	mLift1 = new CANTalon(8);
    	mLift2 = new CANTalon(9);
    	mLoader = new CANTalon(7);
    	xbox = new Joystick(1);
    	drive = new ProntoDrive(mFrontLeft, mFrontRight, mRearLeft, mRearRight, xbox);
    	lift = new ProntoLift(mLift1, mLift2, xbox);
    	loader = new ProntoLoader(mLoader, xbox);
    	
    	drive.start();
    	loader.start();
    	lift.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void teleopInit() {
    	drive.setRun(true);
    	loader.setRun(true);
    	lift.setRun(true);
    }
    
    public void disabledInit() {
    	if (drive != null) {
    		drive.setRun(false);
    	}
    	
    	if (lift != null) {
    		lift.setRun(false);
    	}
    	
    	if (loader != null) {
    		loader.setRun(false);
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	/*
    	 * A = lift up
    	 * B = lift down
    	 * 
    	 * LB = draw in
    	 * RB = out
    	 */
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
