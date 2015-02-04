package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoFlexer implements Pronstants {

	interface Flexstate {
		public Flexstate check();
	}

	static SpeedController flexer;
	static Joystick xbox;
	
	public ProntoFlexer(SpeedController f, Joystick x) {
		flexer = f;
		xbox = x;
	}

	enum Flexstates implements Flexstate {
		FlexerStopped {
			public Flexstate check() {
				if (xbox.getRawButton(X_BUTTON))
					return StartFlexIn;
				if (xbox.getRawButton(Y_BUTTON))
					return StartFlexOut;

				// else
				return FlexerStopped;
			}
		},

		StartFlexIn {
			public Flexstate check() {
				flexIn();
				return FlexingIn;
			}
		},

		StartFlexOut {
			public Flexstate check() {
				flexOut();
				return FlexingOut;
			}
		},

		FlexingIn {
			public Flexstate check() {
				if (!xbox.getRawButton(X_BUTTON))
					return FlexerStopping;

				// else
				return FlexingIn;
			}
		},

		FlexingOut {
			public Flexstate check() {
				if (!xbox.getRawButton(Y_BUTTON))
					return FlexerStopping;

				// else
				return FlexingOut;
			}
		},

		FlexerStopping {
			public Flexstate check() {
				flexStop();
				return FlexerStopped;
			}
		}
	}

	private static void flexIn() {
		flexer.set(FLEX_SPEED);
	}

	private static void flexOut() {
		flexer.set(-FLEX_SPEED);
	}

	private static void flexStop() {
		flexer.set(0);
	}

}
