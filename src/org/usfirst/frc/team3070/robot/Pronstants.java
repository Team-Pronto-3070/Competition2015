package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	/*
	 * Occupied Buttons:
	 * Left Joystick:
	 * Trigger - Load out
	 * 3 - Expand/Contract loader
	 * 
	 * Right Joystick:
	 * Trigger - Load in
	 * 2 - Lift down
	 * 3 - Lift up
	 */
	
	/* Joystick Notes:
	 * Triggers only go in the positive.
	 * Forward on the Y-Axes is in the negative
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
	public static final int UPPER_LIMIT_ID = 1;
	public static final int LOWER_LIMIT_ID = 0;
	public static final int TOTE_LIMIT_ID = 2;

	// Joystick
	// used in: Robot.java, PIDMechDrive.java
	public static final int LEFT_JOYSTICK_PORT = 1;
	public static final int RIGHT_JOYSTICK_PORT = 2;
	public static final double DEADZONE = 0.2;
	
	// PID
	// used in: PIDMechDrive.java
	public static final double KP = 1.25;
	public static final double KI = 0.0;
	public static final double KD = 0.0;
	public static final double AUTO_KP = 0.75;
	
	// Static velocities
	// used in: all classes
	public static final double LIFT_SPEED = .6;
	public static final double LOAD_SPEED = 1.0;
	public static final double FLEX_SPEED = 1.0;
	public static final int ENCODER_MAX_SPEED = 1600;
	public static final int NUM_TICKS = 125;
	public static final double RAMP_RATE = 60.0;
}
