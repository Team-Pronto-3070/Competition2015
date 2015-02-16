package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;


public class ProntoLift implements Pronstants {

    interface LiftState {
        public LiftState check();
    }
    
    static CANTalon motor1, motor2;
    static Joystick jRight;
    LiftState state;
    
    enum LiftStates implements LiftState {
            Stopped {
                @Override
                public LiftState check() {
                    if (jRight.getRawButton(3))
                        return StartLiftUp; // make the totes go up
                    if (jRight.getRawButton(2))
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
                    if (!jRight.getRawButton(3)) 
                        return Stopping; // stop the totes if you press the right bumper
                    return LiftingUp;
                }
            },
            LiftingDown {
                @Override
                public LiftState check() {
                    if (!jRight.getRawButton(2)) 
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


    

    public ProntoLift(CANTalon m1, CANTalon m2, Joystick x) {
        motor1 = m1;
        motor2 = m2;
        jRight = x;
        state = LiftStates.Stopped;
        
//        motor1.setVoltageRampRate(RAMP_RATE);
//        motor2.setVoltageRampRate(RAMP_RATE);
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