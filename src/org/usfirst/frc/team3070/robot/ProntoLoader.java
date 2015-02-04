package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader implements Pronstants {

	interface Loadstate {
		public Loadstate check();
	}

	static SpeedController loader, flexer;
	static Joystick xbox;
	Loadstate state;
	
	public ProntoLoader(SpeedController l, Joystick x) {
		loader = l;
		xbox = x;
		state = Loadstates.LoaderStopped;
	}

	enum Loadstates implements Loadstate {
		LoaderStopped {
			@Override
			public Loadstate check() {
				if (xbox.getRawButton(RIGHT_BUMPER))
					return StartLoadIn;

				if (xbox.getRawButton(LEFT_BUMPER))
					return StartLoadOut;

				// else
				return LoaderStopped;
			}
		},

		StartLoadIn {
			public Loadstate check() {
				loadIn();
				return LoadingIn;
			}

		},

		StartLoadOut {
			public Loadstate check() {
				loadOut();
				return LoadingOut;
			}
		},

		LoadingIn {
			public Loadstate check() {
				if (!xbox.getRawButton(RIGHT_BUMPER))
					return LoaderStopping;
				
				// else
				return LoadingIn;
			}
		},

		LoadingOut {
			public Loadstate check() {
				if (!xbox.getRawButton(LEFT_BUMPER))
					return LoaderStopping;
				
				// else
				return LoadingOut;
			}
		},

		LoaderStopping {
			public Loadstate check() {
				loadStop();
				return LoaderStopped;
			}
		}
	}
	
	private static void loadIn() {
		
	}
	
	private static void loadOut() {
		
	}
	
	private static void loadStop() {
		
	}
}
