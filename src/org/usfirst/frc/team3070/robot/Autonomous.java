//package org.usfirst.frc.team3070.robot;
//
//public class Autonomous implements Pronstants {
//	
//	interface AutoState {
//		public AutoState check();
//	}
//	
//	public Autonomous() {
//		
//	}
//	
//	enum AutoStates implements AutoState {
//		StartLiftUp {
//			@Override
//			public AutoState check() {
//				liftUp();
//				return LiftingUp;
//			}
//		},
//		LiftingUp {
//			@Override
//			public AutoState check() {
//				if (!upperLimit) {
//					return StopLiftUp;
//				}
//				
//				return LiftingUp;
//			}
//		},
//		StopLiftUp {
//			@Override
//			public AutoState check() {
//				liftStop();
//				return StartStrafeLeft;
//			}
//		},
//		StartStrafeLeft {
//			@Override
//			public AutoState check() {
//				autoDrive.positionDrive(1,1,1);
//				return StrafingLeft;
//			}
//		},
//		StrafingLeft {
//			@Override
//			public AutoState check() {
//				if(set_distance) {
//					return Stop StraferLeft;
//				}
//					return StrafingLeft; 	
//			}
//		},
//		StopStrafeLeft {
//			@Override
//			public AutoState check() {
//				autoDrive.positionDrive(0,0,0);
//				return StartLiftDown;
//			}
//		},
//		StartLiftDown {
//			@Override
//			public AutoState check() {
//				liftDown();
//				return LiftingDown;
//			}
//		},
//		LiftingDown {
//			@Override
//			public AutoState check() {
//				if(!lowerLimit) {
//					return StopLiftDown;
//				}
//				
//				return LiftingDown;
//
//			}
//		},
//		StopLiftDown {
//			@Override
//			public AutoState check() {
//				liftStop;
//				return StartDriveBackwards;
//				
//			}
//		},
//		StartDriveBackwards {
//			@Override
//			public AutoState check() {
//				autoDrive.positionDrive(1,1,1);
//				return DrivingBackwards;
//				
//			}
//		},
//		DrivingBackwards {
//			@Override
//			public AutoState check() {
//				if(set_distance) {
//					return StopDrivingBackwards;
//				}
//				return DrivingBackwards;
//			}
//		},
//		StopDrivingBackwards {
//			@Override
//			public AutoState check() {
//				autoDrive.positionDrive(0,0,0);
//				return StopDrivingBackwards;
//			}
//		}
//		
//	}
//
//}
