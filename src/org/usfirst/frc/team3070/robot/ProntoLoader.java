package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader implements Pronstants {

	interface Loadstate {
		public Loadstate check();
	}

	static SpeedController loader, flexer;
	static Joystick jLeft, jRight;
	Loadstate state;
	
	public ProntoLoader(SpeedController l, Joystick jL, Joystick jR) {
		loader = l;
		jLeft = jL;
		jRight = jR;
		state = Loadstates.LoaderStopped;
	}

	enum Loadstates implements Loadstate {
		LoaderStopped {
			@Override
			public Loadstate check() {
				if (jRight.getRawButton(1))
					return StartLoadIn;

				if (jLeft.getRawButton(1))
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
				if (!jRight.getRawButton(1))
					return LoaderStopping;
				
				// else
				return LoadingIn;
			}
		},

		LoadingOut {
			public Loadstate check() {
				if (!jLeft.getRawButton(1))
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
