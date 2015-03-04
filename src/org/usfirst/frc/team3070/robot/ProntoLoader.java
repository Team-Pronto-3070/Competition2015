package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;

public class ProntoLoader implements Pronstants {

	interface Loadstate {
		public Loadstate check();
	}

	//import stuff
	static CANTalon loader, flexer;
	static Joystick jLeft, jRight;
	Loadstate state;
	
	//prepares joysticks and CANTalons
	public ProntoLoader(CANTalon l, Joystick jL, Joystick jR) {
		loader = l;
		jLeft = jL;
		jRight = jR;
		state = Loadstates.LoaderStopped;
	}

	enum Loadstates implements Loadstate {
		LoaderStopped {
			@Override
			//checks if buttons are pressed
			public Loadstate check() {
				//if right joystick button 1 is pressed
				//moves inwards
				if (jRight.getRawButton(1))
					return StartLoadIn;
				
				//if left joystick button 1 is pressed
				//moves outwards
				if (jLeft.getRawButton(1))
					return StartLoadOut;

				// else
				return LoaderStopped;
			}
		},
		
		//loader moves inwards
		StartLoadIn {
			public Loadstate check() {
				loadIn();
				return LoadingIn;
			}

		},

		//loader moves outwaards
		StartLoadOut {
			public Loadstate check() {
				loadOut();
				return LoadingOut;
			}
		},

		//while loader is moving in
		LoadingIn {
			public Loadstate check() {
				//if right joystick button 1 isn't pressed
				//stops loader
				if (!jRight.getRawButton(1))
					return LoaderStopping;
				
				// else
				return LoadingIn;
			}
		},

		//while loader is moving out
		LoadingOut {
			public Loadstate check() {
				//if left joystick button 1 isn't pressed
				//stops loader
				if (!jLeft.getRawButton(1))
					return LoaderStopping;
				
				// else
				return LoadingOut;
			}
		},

		//stops loader
		LoaderStopping {
			public Loadstate check() {
				loadStop();
				return LoaderStopped;
			}
		}
	}
	
	//runs the state function
	public void periodic() {
		state = state.check();
	}
	
	//stops the loader
	public void stopPeriodic() {
		loadStop();
	}
	
	//makes loader move in
	private static void loadIn() {
		loader.set(LOAD_SPEED);
	}

	//makes loader move out
	private static void loadOut() {
		loader.set(-LOAD_SPEED);
	}
	
	//stops the loader
	private static void loadStop() {
		loader.set(0);
	}
}
