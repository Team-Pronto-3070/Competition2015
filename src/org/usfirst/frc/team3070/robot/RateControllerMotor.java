package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class RateControllerMotor implements PIDOutput {
	
	private final SpeedController motor;
	
	public RateControllerMotor(SpeedController m) {
		motor = m;
	}
	
	public void pidWrite(double output) {
		double rateOutput = motor.get() + output;
		rateOutput = Math.min(1.0, rateOutput);
		rateOutput = Math.max(-1.0, rateOutput);
		motor.set(rateOutput);
	}

}
