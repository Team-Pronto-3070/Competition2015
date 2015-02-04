
package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
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
	
	//Encoder enFrontLeft, enFrontRight, enBackLeft, enBackRight;
	
	ProntoDrive drive;
	ProntoLift lift;
	ProntoLoader loader;
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight, mLift1, mLift2, mLoader, mFlex;
	Joystick xbox;
	
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
    	
    	drive = new ProntoDrive(mFrontLeft, mFrontRight, mRearLeft, mRearRight, xbox);
    	lift = new ProntoLift(mLift1, mLift2, xbox);
    	loader = new ProntoLoader(mLoader, mFlex, xbox);
    	
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
