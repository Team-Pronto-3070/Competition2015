package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;


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
                    if (xbox.getRawButton(RIGHT_BUMPER))
                        return StartLiftUp; // make the totes go up
                    if (xbox.getRawButton(LEFT_BUMPER))
                        return StartLiftDown; // make the totes go down
                    return Stopped;
                }
            },
            StartLiftUp {
                @Override
                public LiftState check() {
                    liftUp(); // make the totes go up
                    return LiftingUp;
                }
            },
            StartLiftDown {
                @Override
                public LiftState check() {
                    liftDown(); // make the totes go down
                    return LiftingDown;
                }
            },
            LiftingUp {
                @Override
                public LiftState check() {
                    if (!xbox.getRawButton(RIGHT_BUMPER)) 
                        return Stopping; // stop the totes if you press the right bumper
                    return LiftingUp;
                }
            },
            LiftingDown {
                @Override
                public LiftState check() {
                    if (!xbox.getRawButton(LEFT_BUMPER)) 
                        return Stopping; // stop the totes if you press the left bumper
                    return LiftingDown;
                }
            },
            Stopping {
                @Override
                public LiftState check() {
                    liftStop(); // stop the totes
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

    private static void liftUp() { // makes the totes go up
        motor1.set(LIFT_SPEED);
        motor2.set(-LIFT_SPEED);
    }

    private static void liftDown() { // makes the totes go down
        motor1.set(-LIFT_SPEED);
        motor2.set(LIFT_SPEED);
    }

    private static void liftStop() { // stops the totes from going up or down
        motor1.set(0);
        motor2.set(0);
    }
}