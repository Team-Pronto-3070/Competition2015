package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;


public class ProntoLift implements Pronstants {

    interface LiftState {
        public LiftState check();
    }
    static SpeedController motor1, motor2;
    static Joystick xbox;
    LiftState state;
    
    enum LiftStates implements LiftState {
            Stopped {
                @Override
                public LiftState check() {
                    if (xbox.getRawButton(A_BUTTON))
                        return StartLiftUp;
                    if (xbox.getRawButton(B_BUTTON))
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
                    if (!xbox.getRawButton(A_BUTTON)) 
                        return Stopping;
                    return LiftingUp;
                }
            },
            LiftingDown {
                @Override
                public LiftState check() {
                    if (!xbox.getRawButton(B_BUTTON)) 
                        return Stopping;
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


    

    public ProntoLift(SpeedController m1, SpeedController m2, Joystick x) {
        motor1 = m1;
        motor2 = m2;
        xbox = x;
        state = LiftStates.Stopped;
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