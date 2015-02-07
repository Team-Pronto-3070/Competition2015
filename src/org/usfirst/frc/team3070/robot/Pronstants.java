package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	
	// Joystick Axes
	// used in: ProntoDrive.java, ProntoLift.java
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	public static final int RIGHT_X = 4;
	public static final int RIGHT_Y = 5;
	
	// DPad input
	// used in ProntoDrive.java
	public static final int NO_DPAD_INPUT = -1; // DPad default position
	public static final int DPAD_UP = 0;
	public static final int DPAD_RIGHT = 90;
	public static final int DPAD_DOWN = 180;
	public static final int DPAD_LEFT = 270;
	
	// Joystick Buttons
	// used in: ProntoLoader.java, ProntoLift.java, ProntoFlexer.java
	public static final int A_BUTTON = 1; // Load in
	public static final int B_BUTTON = 2; // Load out
	public static final int X_BUTTON = 3; // Flex in
	public static final int Y_BUTTON = 4; // Flex out
	public static final int LEFT_BUMPER = 5; // Lift down
	public static final int RIGHT_BUMPER = 6; // Lift up
	
	/* Joystick Notes:
	 * Triggers only go in the positive.
	 * Forward on the Y-Axes is in the negative
	 * 
	 * DPad counts in degrees increasing going clockwise
	 * 0 degrees is DPad up
	 */
	
	// CANTalons
	// used in: Robot.java
	public static final int M_FRONT_RIGHT_ID = 2;
	public static final int M_FRONT_LEFT_ID = 3;
	public static final int M_REAR_RIGHT_ID = 4;
	public static final int M_REAR_LEFT_ID = 5;
	public static final int M_FLEXER_ID = 6;
	public static final int M_LOADER_ID = 7;
	public static final int M_LIFT1_ID = 8;
	public static final int M_LIFT2_ID = 9;
	
	// Limit Switches
	// used in ProntoLift.java
	// ports are arbitrary at this moment
	public static final int UPPER_LIMIT_ID = 1;
	public static final int LOWER_LIMIT_ID = 2;
	public static final int TOTE_LIMIT_ID = 3;
	
	// Joystick
	// used in: Robot.java, PIDMechDrive.java
	public static final int JOYSTICK_PORT = 1;
	public static final double DEADZONE = 0.2;
	
	// PID
	// used in: PIDMechDrive.java
	public static final double KP = 1.25;
	public static final double KI = 0.0;
	public static final double KD = 0.0;
	
	// Static velocities
	// used in: all classes
	public static final double LIFT_SPEED = .6;
	public static final double LOAD_SPEED = 1.0;
	public static final double FLEX_SPEED = 1.0;
	public static final int ENCODER_MAX_SPEED = 1600;

}
