package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;

public class Prontonomous implements Pronstants {
	
	interface AutoState {
		public AutoState check();
	}
	
	PIDMechDrive autoDrive;
//	DigitalInput upperLimit, lowerLimit, toteLimit;
	ProntoLift Lifter;
	
	public Prontonomous(PIDMechDrive aD, /*DigitalInput u, DigitalInput l, DigitalInput t, */ProntoLift j) {
		autoDrive = aD;
		
//		upperLimit = u;
//		lowerLimit = l;
//		toteLimit = t;
		
		Lifter = j;
	}
	
	public void run(){
		if(timer.get()<0.25){
			
		}
	}
	
//	enum AutoStates implements AutoState {
//		StartSetLift {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		SettingLift {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopSettingLift {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartLowerLiftToPosition {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		LoweringLiftToPosition {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopLowerLiftToPosition {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartDriveForward {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		DrivingForward {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopDriveForward {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartLiftUp {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		LiftingUp {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopLiftUp {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartDriveBackwards {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		DriveBackwards {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopDriveBackwards {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartLiftDown {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		LiftingDown {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopLiftDown {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StartMoveAwayFromTote {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		MovingAwayFromTote {
//			@Override
//			public AutoState check() {
//				
//			}
//		},
//		StopMovingAwayFromTote {
//			@Override
//			public AutoState check() {
//				
//			}
//		}
//	}

}
