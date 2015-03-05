package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class ProntoLift implements Pronstants{

	//states
	interface LiftState {
		public LiftState check();
	}
	
	//import stuff
	static CANTalon motor1, motor2;
	static Joystick jRight;
	static DigitalInput lower, upper, tote;
	static boolean notAtTop, notAtBottom, readyForNextTote;
	LiftState state;

	public ProntoLift(CANTalon m1, CANTalon m2, DigitalInput u, DigitalInput l,
			DigitalInput t, Joystick r) {
		//Prepares everything
		motor1 = m1;
		motor2 = m2;
		lower = l;
		upper = u;
		tote = t;
		jRight = r;
		notAtTop = true;
		notAtBottom = true;
		readyForNextTote = false;
		
		state = LiftStates.Stopped;
	}

	enum LiftStates implements LiftState {
		//When the lifter isn't moving
		Stopped {
			@Override
			public LiftState check() {
				//If lifter isn't at the top and button 3 is pressed
				//Moves lifter up
				if (notAtTop && jRight.getRawButton(3)) {
					readyForNextTote = false;
					return StartLiftUp;
				}
				//If lifter isn't at the bottom and button 2 is pressed
				//Moves lifter down
				if (notAtBottom && jRight.getRawButton(2)) {
					readyForNextTote = false;
					return StartLiftDown;
				}

				return Stopped;
			}
		},
		//Begins moving the lifter up
		StartLiftUp {
			@Override
			public LiftState check() {
				setLift(LIFT_SPEED);
				notAtBottom = true;
				return LiftingUp;
			}
		},
		
		//While lifter is moving up
		LiftingUp {
			@Override
			public LiftState check() {
				
				//if lifter is at top
				//stops the lifter
				if (!upper.get()) {
					notAtTop = false;
					return Stopping;
				}
				
				//if lifter is at tote switch
				//stops the lifter but in a special way
				if (!tote.get()) {
					return WaitForRelease;
				}
				
				//if button 3 isn't pressed
				//stops the lifter
				if (!jRight.getRawButton(3)) {
					return Stopping;
				}
				
				return LiftingUp;
			}
		},
		
		//looks commented fully but actually isn't
		//when the lifter reaches the tote switch going up
		WaitForRelease {
			@Override
			public LiftState check() {
				//if on tote switch, stop lifter
				//else, lift upwards
				if (!tote.get()) {
					setLift(.4);
				} else {
					readyForNextTote = true;
					setLift(0);
				}
				
				//if button isn't pressed
				//stops lifter
				if (!jRight.getRawButton(3)) {
					return Stopping;
				}
				
				return WaitForRelease;
			}
		},
		
		//makes lifter go down
		StartLiftDown {
			@Override
			public LiftState check() {
				setLift(-LIFT_SPEED);
				notAtTop = true;
				return LiftingDown;
			}
		},
		//while lifter is going down
		LiftingDown {
			@Override
			public LiftState check() {
				//if lifter is at bottom
				//stops lifter
				if (!lower.get()) {
					notAtBottom = false;
					return Stopping;
				}
				
				//if button 2 isn't pressed
				//stops lifter
				if (!jRight.getRawButton(2))
					return Stopping;
				
				return LiftingDown;
			}
		},
		
		//Stops the lifter
		Stopping {
			@Override
			public LiftState check() {
				setLift(0);
				return Stopped;
			}
		}
	}
	
	//runs the state loop
	public void periodic() {
		state = state.check();
	}
	
	//stops the lifter
	public void stopPeriodic() {
		setLift(0);
	}
	
	//makes lift move correctly
	private static void setLift(double speed) {
		motor1.set(-speed);
		motor2.set(speed);
	}

}
