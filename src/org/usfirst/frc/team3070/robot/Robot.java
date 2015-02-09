
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
public class Robot extends IterativeRobot implements Pronstants {
	
	//Encoder enFrontLeft, enFrontRight, enBackLeft, enBackRight;
	
	CANTalon mFrontLeft, mFrontRight, mRearLeft, mRearRight, mLift1, mLift2, mLoader;
	Joystick jLeft, jRight;
	
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
    	
    	jLeft = new Joystick(J_LEFT_PORT);
    	jRight = new Joystick(J_RIGHT_PORT);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void disabledInit() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
