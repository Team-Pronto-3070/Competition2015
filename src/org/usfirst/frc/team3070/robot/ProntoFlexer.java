package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoFlexer implements Pronstants {

	interface Flexstate {
		public Flexstate check();
	}

	static SpeedController flexer;
	static Joystick xbox;
	static int timeCounter;
	Flexstate state;
	
	public ProntoFlexer(SpeedController f, Joystick x) {
		flexer = f;
		xbox = x;
		state = Flexstates.FlexerStoppedExpanded;
		timeCounter = 0;
	}

	enum Flexstates implements Flexstate {
		FlexerStoppedExpanded {
			@Override
			public Flexstate check() {
				if (xbox.getRawButton(5))
					return StartFlexIn;
				
				if (xbox.getRawButton(6))
					return StartFlexOut;

				// else
				return FlexerStoppedExpanded;
			}
		},
		FlexerStoppedContracted {
			@Override
			public Flexstate check() {
				if (xbox.getRawButton(5))
					return StartFlexOut;
				
				if (xbox.getRawButton(6))
					return StartFlexIn;
				
				// else
				return FlexerStoppedContracted;
			}
		},

		StartFlexIn {
			@Override
			public Flexstate check() {
				flexIn();
				return FlexingIn;
			}
		},

		StartFlexOut {
			@Override
			public Flexstate check() {
				flexOut();
				return FlexingOut;
			}
		},

		FlexingIn {
			@Override
			public Flexstate check() {
				if (timeCounter >= FLEX_LIMIT)
					return FlexerStoppingIn;

				// else
				timeCounter++;
				return FlexingIn;
			}
		},

		FlexingOut {
			@Override
			public Flexstate check() {
				if (timeCounter >= FLEX_LIMIT)
					return FlexerStoppingOut;

				// else
				timeCounter++;
				return FlexingOut;
			}
		},

		FlexerStoppingIn {
			@Override
			public Flexstate check() {
				flexStop();
				timeCounter = 0;
				return FlexerStoppedContracted;
			}
		},
		
		FlexerStoppingOut {
			@Override
			public Flexstate check() {
				flexStop();
				timeCounter = 0;
				return FlexerStoppedExpanded;
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
