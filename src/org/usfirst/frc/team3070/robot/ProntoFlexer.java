package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;

public class ProntoFlexer implements Pronstants {

	interface Flexstate {
		public Flexstate check();
	}

	static CANTalon flexer;
	static Joystick jLeft;
	static int timeCounter;
	static boolean flexedIn;
	Flexstate state;
	
	public ProntoFlexer(CANTalon f, Joystick x) {
		flexer = f;
		jLeft = x;
		state = Flexstates.FlexerStoppedExpanded;
		
		timeCounter = 0;
		flexedIn = false;
	}

	enum Flexstates implements Flexstate {
		FlexerStoppedExpanded {
			@Override
			public Flexstate check() {
				flexedIn = false;
				
				if (jLeft.getRawButton(3))
					return StartFlexIn;
				
				if(jLeft.getRawButton(11) && jLeft.getRawButton(6)) {
					if (jLeft.getRawButton(2))
						return StartFlexOut;
				}
				
				// else
				return FlexerStoppedExpanded;
			}
		},
		FlexerStoppedContracted {
			@Override
			public Flexstate check() {
				flexedIn = true;
				
				if (jLeft.getRawButton(3))
					return StartFlexOut;
				
				if(jLeft.getRawButton(11) && jLeft.getRawButton(6)) {
					if (jLeft.getRawButton(2))
						return StartFlexIn;
				}
				
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
				if (timeCounter >= NUM_TICKS)
					return FlexerStoppingIn;

				// else
				timeCounter++;
				return FlexingIn;
			}
		},

		FlexingOut {
			@Override
			public Flexstate check() {
				if (timeCounter >= NUM_TICKS)
					return FlexerStoppingOut;

				// else
				timeCounter++;
				return FlexingOut;
			}
		},

		FlexerStoppingIn {
			@Override
			public Flexstate check() {
				flexedIn = true;
				
				flexStop();
				timeCounter = 0;
				return FlexerStoppedContracted;
			}
		},
		
		FlexerStoppingOut {
			@Override
			public Flexstate check() {
				flexedIn = false;
				
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
