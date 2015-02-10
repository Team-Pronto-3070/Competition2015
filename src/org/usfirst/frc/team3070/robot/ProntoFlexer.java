package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoFlexer implements Pronstants {

	interface Flexstate {
		public Flexstate check();
	}

	static SpeedController flexer;
	static Joystick jLeft;
	Flexstate state;
	
	public ProntoFlexer(SpeedController f, Joystick x) {
		flexer = f;
		jLeft = x;
		state = Flexstates.FlexerStopped;
	}

	enum Flexstates implements Flexstate {
		FlexerStopped {
			public Flexstate check() {
				if (jLeft.getRawButton(3))
					return StartFlexIn;
				if (jLeft.getRawButton(2))
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
				if (!jLeft.getRawButton(3))
					return FlexerStopping;

				// else
				return FlexingIn;
			}
		},

		FlexingOut {
			public Flexstate check() {
				if (!jLeft.getRawButton(2))
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
	
	public void periodic() {
		state = state.check();
	}
	
	public void stopPeriodic() {
		flexStop();
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
