package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;

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
				if (xbox.getRawButton(A_BUTTON))
					return StartLoadIn;

				if (xbox.getRawButton(B_BUTTON))
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
	
	public void periodic() {
		state = state.check();
	}
	
	public void stopPeriodic() {
		loadStop();
	}
	
	private static void loadIn() {
		loader.set(LOAD_SPEED);
	}
	
	private static void loadOut() {
		loader.set(-LOAD_SPEED);
	}
	
	private static void loadStop() {
		loader.set(0);
	}
}
