package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLoader implements Pronstants {

<<<<<<< HEAD
	boolean running = false;

	SpeedController load, flex;
	Joystick xbox;

	public ProntoLoader(SpeedController l, SpeedController f, Joystick x) {
		load = l;
		flex = f;
=======
	interface Loadstate {
		public Loadstate check();
	}

	static SpeedController loader, flexer;
	static Joystick xbox;
	Loadstate state;
	
	public ProntoLoader(SpeedController l, Joystick x) {
		loader = l;
>>>>>>> origin/Enum-Test
		xbox = x;
		state = Loadstates.LoaderStopped;
	}

	enum Loadstates implements Loadstate {
		LoaderStopped {
			@Override
			public Loadstate check() {
				if (xbox.getRawButton(A_BUTTON))
					return StartLoadIn;

<<<<<<< HEAD
	@Override
	public void run() {
		while (true) {
			while (running) {				
				if (xbox.getRawButton(LEFT_BUMPER)) {
					load.set(LOAD_SPEED);
				} else if (xbox.getRawButton(RIGHT_BUMPER)) {
					load.set(-LOAD_SPEED);
				} else {
					load.set(0);
				}
				
				if (xbox.getRawButton(3)) {
					flex.set(FLEX_SPEED);
				} else if (xbox.getRawButton(4)) {
					flex.set(-FLEX_SPEED);
				} else {
					flex.set(0);
				}
=======
				if (xbox.getRawButton(B_BUTTON))
					return StartLoadOut;
>>>>>>> origin/Enum-Test

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
