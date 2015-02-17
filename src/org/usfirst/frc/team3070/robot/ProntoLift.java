package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class ProntoLift implements Pronstants{

	interface LiftState {
		public LiftState check();
	}
	
	static CANTalon motor1, motor2;
	static Joystick jRight;
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
		jRight = r;
		notAtTop = true;
		notAtBottom = true;
		toteCount = 0;
		
		state = LiftStates.Stopped;
	}

	enum LiftStates implements LiftState {
		Stopped {
			@Override
			public LiftState check() {
				if (notAtTop && jRight.getRawButton(3)) {
					return StartLiftUp;
				}

				if (notAtBottom && jRight.getRawButton(2)) {
					return StartLiftDown;
				}

				return Stopped;
			}
		},
		StartLiftUp {
			@Override
			public LiftState check() {
				setLift(LIFT_SPEED);
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
				
				if (!jRight.getRawButton(3)) {
					return Stopping;
				}
				
				return LiftingUp;
			}
		},
		WaitForRelease {
			@Override
			public LiftState check() {
				if (!tote.get()) {
					setLift(.4);
				} else {
					setLift(0);
				}
				if (!jRight.getRawButton(3)) {
					return Stopping;
				}
				
				return WaitForRelease;
			}
		},
		StartLiftDown {
			@Override
			public LiftState check() {
				setLift(-LIFT_SPEED);
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
				
				if (!jRight.getRawButton(2))
					return Stopping;
				
				return LiftingDown;
			}
		},
		Stopping {
			@Override
			public LiftState check() {
				setLift(0);
				return Stopped;
			}
		}
	}
	
	public void periodic() {
		state = state.check();
	}
	
	public void stopPeriodic() {
		setLift(0);
	}
	
	private static void setLift(double speed) {
		motor1.set(-speed);
		motor2.set(-speed);
	}

}
