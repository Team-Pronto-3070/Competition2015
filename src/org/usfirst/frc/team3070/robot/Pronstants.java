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
<<<<<<< HEAD
	//used in ProntoLoader.java to set loader
	public static final int B_BUTTON = 2;
	//used in ProntoLoader.java
=======
	public static final int B_BUTTON = 2;
>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	
	// CANTalons
	// Ports used in: Robot.java
	public static final int M_FRONT_RIGHT_ID = 2;
	public static final int M_FRONT_LEFT_ID = 3;
	public static final int M_REAR_RIGHT_ID = 4;
	public static final int M_REAR_LEFT_ID = 5;
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
	public static final double LIFT_SPEED = 1.0;
<<<<<<< HEAD
	public static final double LOAD_SPEED_OUT = 1.0;
	public static final double LOAD_SPEED_IN = -1.0;
=======

>>>>>>> 8b1b58db90f235520bb8594c2f24f5fcdbc9c4c3

}
