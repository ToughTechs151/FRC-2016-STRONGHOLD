/**
 * Code for the drive system used by the robot 
 * 
 * @author Harrison Pound
 * @author Madeline Esp
 * @author Ruthra Govindaraj
 * @author Randy Wang
 * @author Shannon McInnis
 * @author Julia Zhang
 * @version 2/20/16 (3.2)
 */

package org.nashua.tt151.system;

import java.lang.invoke.SwitchPoint;
import java.nio.channels.Channel;

import org.nashua.tt151.lib.F310;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class TankDrive extends Subsystem {
	static TankDrive INSTANCE = null;

	Talon l1 = new Talon(5);
	Talon l2 = new Talon(4);
	Talon r1 = new Talon(3);
	Talon r2 = new Talon(2);

	static int leftChannel1 = 0;
	static int leftChannel2 = 1;
	static int rightChannel1 = 2;
	static int rightChannel2 = 3;
	
	
//	DigitalInput autoSwitch0 = new DigitalInput(0);
//	DigitalInput autoSwitch1 = new DigitalInput(1);
	
	AnalogInput autoSwitch0 = new AnalogInput(0);
	AnalogInput autoSwitch1 = new AnalogInput(1);

	int AUTO_TARGET_DISTANCE;
	double AUTO_SPEED;

	public Encoder leftEncoder = new Encoder (leftChannel1, leftChannel2, true, CounterBase.EncodingType.k4X); //CHECK CHANNELS FOR ENCODER
	public Encoder rightEncoder = new Encoder (rightChannel1, rightChannel2, true, CounterBase.EncodingType.k4X); //CHECK CHANNELS FOR ENCODER

	private double DISTANCE_PER_PULSE = 0.01963194; //in inches

	private TankDrive() {}

	public static TankDrive getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TankDrive();
		}
		return INSTANCE;
	}


	@Override
	public void init() {
		resetEncoders();
		setDistancePerPulse();
	}


	@Override
	public void operatorControl(F310 drive, F310 arm) {

		double left = drive.getLeftY();
		double right = drive.getRightY();

		set(right * getMultiplierRight(drive), getMultiplierLeft(drive) * left);
	}

	private double getMultiplierLeft(F310 drive) {
		if(drive.getButton(F310.Button.LEFT_BUMPER)) {
			return 0.125;
		}
		else if(drive.getButton(F310.Button.LEFT_TRIGGER)) {
			return 0.5;
		}
		else {
			return 0.25;
		} 

	}	

	private double getMultiplierRight(F310 drive) {
		if(drive.getButton(F310.Button.RIGHT_BUMPER)) {
			return 0.125;
		}
		else if(drive.getButton(F310.Button.RIGHT_TRIGGER)) {
			return 0.5;
		}
		else {
			return 0.25;
		} 

	}	

	public void set(double left, double right) {
		setLeft(left);
		setRight(-right);
	}

	private void setLeft(double speed) {
		l1.set(speed);
		l2.set(speed);
	}

	private void setRight(double speed) {
		r1.set(speed);
		r2.set(speed);
	}

	/**
	 * Encoder Shit
	 */
	public void setDistancePerPulse() {
		//Sets distance per pulse in inches
		leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
	}

	public double getDistance() {
		//Returns distance per pulse in inches 
		return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public double getAutoSwitch0(){
		return autoSwitch0.getVoltage();
	}
	public double getAutoSwitch1(){
		return autoSwitch1.getVoltage();
	}
	/**
	 * Autonomous code
	 */


	//AUTO_TARGET_DISTANCE is in inches, so multiply it by twelve
	public void autoForward() {
		// TODO Auto-generated method stub
		//	AUTO_TARGET_DISTANCE = 15 * 12; //RANDOM VALUE, FIX THIS PLS AND THANKS
		if(getDistance() < AUTO_TARGET_DISTANCE) {
			TankDrive.getInstance().set(-0.125, -0.125);
			System.out.println("Distance so far: " + getDistance());
		}
		else if (getDistance() >= AUTO_TARGET_DISTANCE) {
			TankDrive.getInstance().set(0.0, 0.0);
			System.out.println("Distance so far: " + getDistance());
		}
	}

	public void autoLeft() {
		autoForward();
		
		
//		else if (getDistance() == AUTO_TARGET_DISTANCE - 15 * 12) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 15 * 12 && getDistance() <= AUTO_TARGET_DISTANCE - 12 * 12) {
//			TankDrive.getInstance().set(-0.05, 0.05);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 12 * 12 && getDistance() <= AUTO_TARGET_DISTANCE - 8 * 12) {
//			TankDrive.getInstance().set(AUTO_SPEED, AUTO_SPEED);
//		}
//		else if (getDistance() == AUTO_TARGET_DISTANCE - 8 * 12) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 8 * 12 && getDistance() < AUTO_TARGET_DISTANCE - 5 * 12) {
//			TankDrive.getInstance().set(0.05, -0.05);
//		}
//		else if (getDistance() >= AUTO_TARGET_DISTANCE - 5 * 12 && getDistance() < AUTO_TARGET_DISTANCE) {
//			TankDrive.getInstance().set(AUTO_SPEED, AUTO_SPEED);
//		}
//		e0lse if (getDistance() == AUTO_TARGET_DISTANCE) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
	}

	public void autoRight() {
		//	AUTO_TARGET_DISTANCE = 5 * 12; //RANDOM VALUE, FIX THIS PLS AND THANKS
		if (getDistance() < AUTO_TARGET_DISTANCE - 15 * 12) {
			TankDrive.getInstance().set(AUTO_SPEED, AUTO_SPEED);		}
//		else if (getDistance() == AUTO_TARGET_DISTANCE - 15 * 12) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 15 * 12 && getDistance() <= AUTO_TARGET_DISTANCE - 12 * 12) {
//			TankDrive.getInstance().set(0.05, -0.05);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 12 * 12 && getDistance() <= AUTO_TARGET_DISTANCE - 8 * 12) {
//			TankDrive.getInstance().set(AUTO_SPEED, AUTO_SPEED);		}
//		else if (getDistance() == AUTO_TARGET_DISTANCE - 8 * 12) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
//		else if (getDistance() > AUTO_TARGET_DISTANCE - 8 * 12 && getDistance() < AUTO_TARGET_DISTANCE - 5 * 12) {
//			TankDrive.getInstance().set(-0.05, 0.05);
//		}
//		else if (getDistance() >= AUTO_TARGET_DISTANCE - 5 * 12 && getDistance() < AUTO_TARGET_DISTANCE) {
//			TankDrive.getInstance().set(AUTO_SPEED, AUTO_SPEED);		}
//		else if (getDistance() == AUTO_TARGET_DISTANCE) {
//			TankDrive.getInstance().set(0.0, 0.0);
//		}
	}

	public void autoLowBar() {
		// TODO Auto-generated method stub
		AUTO_TARGET_DISTANCE = 11*12;
		AUTO_SPEED = .125;
	}

	public void autoSpyBot() {
		// TODO Auto-generated  method stub
		AUTO_TARGET_DISTANCE = 0;
		AUTO_SPEED = 0;
	}

	public void autoMoat() {
		// TODO Auto-generated method stub
		AUTO_TARGET_DISTANCE = 15*12;
		AUTO_SPEED = .25;

	}

	public void autoRoughTerrain() {
		// TODO Auto-generated method stub
		AUTO_TARGET_DISTANCE = 15*12;
		AUTO_SPEED = .25;		
	}
}

