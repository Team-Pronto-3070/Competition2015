package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;

public class ProntoFlexer implements Pronstants {

	//States
	interface Flexstate {
		public Flexstate check();
	}

	//import stuff
	static CANTalon flexer;
	static Joystick jLeft;
	static int timeCounter;
	static boolean flexedIn;
	Flexstate state;
	
	//sets the CANTalon and joystick, prepares the state
	public ProntoFlexer(CANTalon f, Joystick x) {
		flexer = f;
		jLeft = x;
		state = Flexstates.FlexerStoppedExpanded;
		
		timeCounter = 0;
		flexedIn = false;
	}

	enum Flexstates implements Flexstate {
		//When flexer is stopped and out
		FlexerStoppedExpanded {
			@Override
			public Flexstate check() {
				flexedIn = false;
				
				if (jLeft.getRawButton(3)) //Left joystick button 3
					return StartFlexIn;
				
				if(jLeft.getRawButton(11) && jLeft.getRawButton(6)) {
					//If left joystick buttons 2, 6, and 11 are pressed
					if (jLeft.getRawButton(2)) 
						return StartFlexOut;
				}
				
				// else
				return FlexerStoppedExpanded;
			}
		},
		//when flexer is stopped and in
		FlexerStoppedContracted {
			@Override
			public Flexstate check() {
				flexedIn = true;
				
				//if left joystick button 3 is pressed
				if (jLeft.getRawButton(3))
					return StartFlexOut;
				
				//if left joystick buttons 2, 6, and 11 are pressed
				if(jLeft.getRawButton(11) && jLeft.getRawButton(6)) {
					if (jLeft.getRawButton(2))
						return StartFlexIn;
				}
				
				// else
				return FlexerStoppedContracted;
			}
		},
		
		//Prepares the flexer for going inwards
		StartFlexIn {
			@Override
			public Flexstate check() {
				flexIn();
				return FlexingIn;
			}
		},

		//Prepares the flexer for going outwards
		StartFlexOut {
			@Override
			public Flexstate check() {
				flexOut();
				return FlexingOut;
			}
		},
		
		//flexer goes inwards
		FlexingIn {
			@Override
			public Flexstate check() {
				// if it's been going for 80 ticks(1.6 seconds)
				if (timeCounter >= NUM_TICKS)
					return FlexerStoppingIn;

				// else
				timeCounter++;
				return FlexingIn;
			}
		},

		//flexer goes outwards
		FlexingOut {
			@Override
			public Flexstate check() {
				// if it's been going for 80 ticks(1.6 seconds)
				if (timeCounter >= NUM_TICKS)
					return FlexerStoppingOut;

				// else
				timeCounter++;
				return FlexingOut;
			}
		},

		//after the flexer has moved inwards
		FlexerStoppingIn {
			@Override
			public Flexstate check() {
				flexedIn = true;
				
				//stops the flexer
				flexStop();
				
				//resets timer
				timeCounter = 0;

				return FlexerStoppedContracted;
			}
		},
		//after the flexer has moved outwards
		FlexerStoppingOut {
			@Override
			public Flexstate check() {
				flexedIn = false;
				
				//stops the flexer
				flexStop();
				
				//resets timer
				timeCounter = 0;

				return FlexerStoppedExpanded;
			}
		}
	}
	
	//Runs the state function
	public void periodic() {
		state = state.check();
	}
	
	//stops the state function
	public void stopPeriodic() {
		flexStop();
	}
	
	//Moves flexer inwards
	private static void flexIn() {
		flexer.set(FLEX_SPEED);
	}

	//Moves flexer outwards
	private static void flexOut() {
		flexer.set(-FLEX_SPEED);
	}

	//stops flexer
	private static void flexStop() {
		flexer.set(0);
	}

}
