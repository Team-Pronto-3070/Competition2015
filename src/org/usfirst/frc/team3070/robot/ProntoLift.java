package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ProntoLift implements Pronstants {

	interface LiftState {
		public LiftState check();
	}

	static SpeedController motor1, motor2;
	static Joystick xbox;
	static DigitalInput upperLimit, lowerLimit, toteLimit;
	static int totesLoaded;
	static int temp;
	static boolean atTop, atBottom;
	LiftState state;

	public ProntoLift(SpeedController m1, SpeedController m2, DigitalInput u,
			DigitalInput l, DigitalInput t, Joystick x) {
		motor1 = m1;
		motor2 = m2;
		upperLimit = u;
		lowerLimit = l;
		toteLimit = t;
		xbox = x;

		totesLoaded = 0;
		temp = 0;
		atTop = false;
		atBottom = false;
		state = LiftStates.Stopped;
	}

	enum LiftStates implements LiftState {
		Stopped {
			@Override
			public LiftState check() {
				if (!atTop && xbox.getRawButton(RIGHT_BUMPER)) {
					temp = totesLoaded;
					return StartLiftUp;
				}
				if (!atBottom && xbox.getRawButton(LEFT_BUMPER))
					return StartLiftDown;
				return Stopped;
			}
		},
		StartLiftUp {
			@Override
			public LiftState check() {
				liftUp();
				return LiftingUp;
			}
		},
		StartLiftDown {
			@Override
			public LiftState check() {
				liftDown();
				return LiftingDown;
			}
		},
		LiftingUp {
			@Override
			public LiftState check() {
				if (toteLimit.get() && temp == totesLoaded) {
					totesLoaded++;
					return Stopping;
				}
				
				if (upperLimit.get()) {
					atTop = true;
					return Stopping;
				}

				if (!xbox.getRawButton(RIGHT_BUMPER))
					return Stopping;
				
				atBottom = false;
				return LiftingUp;
			}
		},
		LiftingDown {
			@Override
			public LiftState check() {
				if (toteLimit.get() && temp == totesLoaded) {
					totesLoaded--;
					return Stopping;
				}
				
				if (lowerLimit.get()) {
					atBottom = true;
					return Stopping;
				}

				if (!xbox.getRawButton(LEFT_BUMPER)) {
					return Stopping;
				}
				
				atTop = false;
				return LiftingDown;
			}
		},
		Stopping {
			@Override
			public LiftState check() {
				liftStop();
				return Stopped;
			}
		}
	}

	public void periodic() {
		state = state.check();
	}

	public void stopPeriodic() {
		liftStop();
	}

	private static void liftUp() {
		motor1.set(LIFT_SPEED);
		motor2.set(-LIFT_SPEED);
	}

	private static void liftDown() {
		motor1.set(-LIFT_SPEED);
		motor2.set(LIFT_SPEED);
	}

	private static void liftStop() {
		motor1.set(0);
		motor2.set(0);
	}
}