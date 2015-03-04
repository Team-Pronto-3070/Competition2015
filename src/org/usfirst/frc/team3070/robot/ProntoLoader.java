package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;

public class ProntoLoader implements Pronstants {

	interface Loadstate {
		public Loadstate check();
	}

	static CANTalon loader, flexer;
	static Joystick jLeft, jRight;
	// creating class objects
	// these have to be static because of syntax reasons
	
	Loadstate state;
	// creates the state tracker for this class
	
	public ProntoLoader(CANTalon l, Joystick jL, Joystick jR) {
		loader = l;
		jLeft = jL;
		jRight = jR;
		// assign class joysticks and motor to the given objects
		
		state = Loadstates.LoaderStopped;
		// default state is stopped
	}

	enum Loadstates implements Loadstate {
		/*
		 * LoaderStopped is the state in which the wheels aren't moving
		 * and is checking for button input to move the wheels
		 */
		LoaderStopped {
			@Override
			public Loadstate check() {
				if (jRight.getRawButton(1))
					// if the right trigger is pressed, go to StartLoadIn state
					return StartLoadIn;

				if (jLeft.getRawButton(1))
					// if the left trigger is pressed, go to StartLoadOut state
					return StartLoadOut;

				// else, stay in the stopped state
				return LoaderStopped;
			}
		},

		/*
		 * set the motors to run inward then move to the LoadingIn state
		 */
		StartLoadIn {
			public Loadstate check() {
				loadIn();
				return LoadingIn;
			}

		},

		/*
		 * set the motors to run outward then move to the LoadingOut state
		 */
		StartLoadOut {
			public Loadstate check() {
				loadOut();
				return LoadingOut;
			}
		},

		/*
		 * the wheels will default to rotating inward in this state
		 * as long as the right trigger is held
		 */
		LoadingIn {
			public Loadstate check() {
				if (!jRight.getRawButton(1))
					// when the right trigger is released, move to LoaderStopping state
					return LoaderStopping;
				
				// else stay in this state
				return LoadingIn;
			}
		},

		/*
		 * the wheels will default to rotating outward in this state
		 * as long as the left trigger is held
		 */
		LoadingOut {
			public Loadstate check() {
				if (!jLeft.getRawButton(1))
					// when the left trigger is released, move to LoaderStopping state
					return LoaderStopping;
				
				// else stay in this state
				return LoadingOut;
			}
		},

		/*
		 * stops the motors and moves to the LoaderStopped state
		 */
		LoaderStopping {
			public Loadstate check() {
				loadStop();
				return LoaderStopped;
			}
		}
	}
	
	// determines the state that the robot will be running in
	// this method is called in teleopPeriodic
	public void periodic() {
		state = state.check();
	}
	
	// stops the wheels without switching states
	// this method is called in disabledInit
	public void stopPeriodic() {
		loadStop();
	}
	
	// sets motors to rotate inward
	private static void loadIn() {
		loader.set(LOAD_SPEED);
		// positive is inward
	}
	
	// sets motors to rotate outward
	private static void loadOut() {
		loader.set(-LOAD_SPEED);
		// negative is outward
	}
	
	// stops the motors
	private static void loadStop() {
		loader.set(0);
	}
}
