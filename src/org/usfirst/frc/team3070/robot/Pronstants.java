package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	
	// Joystick Axes
	// Ports used in: ProntoDrive.java
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int RIGHT_X = 4;
	
	// Joystick Buttons
	// Ports used in: ProntoLift.java, ProntoLoader.java
	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static final int X_BUTTON = 3;
	public static final int Y_BUTTON = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	
	// CANTalons
	// Ports used in: Robot.java
	public static final int M_FRONT_RIGHT_ID = 2;
	public static final int M_FRONT_LEFT_ID = 3;
	public static final int M_REAR_RIGHT_ID = 4;
	public static final int M_REAR_LEFT_ID = 5;
	public static final int M_FLEXER_ID = 6;
	public static final int M_LOADER_ID = 7;
	public static final int M_LIFT1_ID = 8;
	public static final int M_LIFT2_ID = 9;
	
	// Joystick
	// Ports used in: Robot.java
	public static final int JOYSTICK_PORT = 1;
	
	// PID
	// Ports used in: N/A
	public static final double KP = 0.0125;
	public static final double KI = 0.0;
	public static final double KD = 0.0;
	
	
	public static final double DEADZONE = 0.2;
	public static final double LIFT_SPEED = .6;
	public static final double LOAD_SPEED = 1.0;
	public static final double FLEX_SPEED = 1.0;

}
