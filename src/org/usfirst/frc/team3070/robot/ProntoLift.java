package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class ProntoLift implements Pronstants{

	interface LiftState {
		public LiftState check();
	}
	
	static CANTalon motor1, motor2;
	static Joystick xbox;
	static DigitalInput lower, upper, tote;
	static boolean notAtTop, notAtBottom, readyForNextTote;
	static int toteCount;
	LiftState state;

	public ProntoLift(CANTalon m1, CANTalon m2, DigitalInput u, DigitalInput l,
			DigitalInput t, Joystick r) {
		motor1 = m1;
		motor2 = m2;
		lower = l;
		upper = u;
		tote = t;
		xbox = r;
		notAtTop = true;
		notAtBottom = true;
		toteCount = 0;
		
		state = LiftStates.Stopped;
	}

	enum LiftStates implements LiftState {
		Stopped {
			@Override
			public LiftState check() {
				if (notAtTop && xbox.getRawButton(1)) {
					return StartLiftUp;
				}

				if (notAtBottom && xbox.getRawButton(2)) {
					return StartLiftDown;
				}

				return Stopped;
			}
		},
		StartLiftUp {
			@Override
			public LiftState check() {
				lift(LIFT_SPEED);
				notAtBottom = true;
				return LiftingUp;
			}
		},
		LiftingUp {
			@Override
			public LiftState check() {
				if (!upper.get()) {
					notAtTop = false;
					return Stopping;
				}
				
				if (!tote.get()) {
					return WaitForRelease;
				}
				
				if (!xbox.getRawButton(1)) {
					return Stopping;
				}
				
				return LiftingUp;
			}
		},
		WaitForRelease {
			@Override
			public LiftState check() {
				if (!tote.get()) {
					lift(.4);
				} else {
					lift(0);
				}
				if (!xbox.getRawButton(1)) {
					return Stopping;
				}
				
				return WaitForRelease;
			}
		},
		StartLiftDown {
			@Override
			public LiftState check() {
				lift(-LIFT_SPEED);
				notAtTop = true;
				return LiftingDown;
			}
		},
		LiftingDown {
			@Override
			public LiftState check() {
				if (!lower.get()) {
					notAtBottom = false;
					return Stopping;
				}
				
				if (!xbox.getRawButton(2))
					return Stopping;
				
				return LiftingDown;
			}
		},
		Stopping {
			@Override
			public LiftState check() {
				lift(0);
				return Stopped;
			}
		}
	}
	
	public void periodic() {
		state = state.check();
	}
	
	public void stopPeriodic() {
		lift(0);
	}
	
	private static void lift(double speed) {
		motor1.set(-speed);
		motor2.set(speed);
	}

}
