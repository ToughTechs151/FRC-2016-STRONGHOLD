/**
 * Code for articulating arm in elbow and shoulder joints 
 * 
 * @author Madeline Esp
 * @author Harrison Pound
 * @author Ruthra Govindaraj
 * @version 2/22/2016 (1.2)
 */ 

package org.nashua.tt151.system;
import org.nashua.tt151.lib.F310; 
import edu.wpi.first.wpilibj.Talon;


public class Arm extends Subsystem {
	private static Arm INSTANCE = null;
		
	Talon elbow = new Talon(0);
	Talon shoulder = new Talon(1);
	
	private Arm() {}
	
	public static Arm getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Arm();
		}
		return INSTANCE;
	}
	public void init() {}
	
	@Override
	public void operatorControl(F310 drive, F310 arm) {
	
		double mult = getMultiplier(arm);
		double shoulder2 = arm.getLeftY() * mult;
		double elbow2 = arm.getRightY() * mult;
		
		
//		set(elbow2, shoulder2);
//		boolean lock = false; //Boolean for locking the arm
//		lock = lockToggle(lock, arm);
//		
//			if(lock) {
//			elbow.set(0);
//			shoulder.set(0);
//		}
		
//		if(arm.getButton(F310.Button.B)) {
//			lock = true;
//			//Locks both elbow and shoulder joints
//		}
//		
//		if(arm.getButton(F310.Button.A)) {
//			lock = false;
//		}
		
		if(arm.getButton(F310.Button.LEFT_BUMPER)) {
			setShoulder(-.125); //Sets shoulder joint to 1/8 speed if left bumper is pressed
		} else {
			setShoulder(shoulder2);
		}
		
		if (arm.getButton(F310.Button.RIGHT_BUMPER)) {
			setElbow(.125); //Sets elbow joint to 1/8 speed if right bumper is pressed
		} else {
			setElbow(elbow2);
		}
	}
	/**
	 * Toggles boolean for lock status every time button B is pressed on Logitech Controller
	 * UNTESTED
	 * @param bool
	 * @param arm
	 * @author Ruthra Govindaraj
	 * @return bool
	 */
	
	private boolean lockToggle(boolean bool, F310 arm) {
		if(arm.getButton(F310.Button.B)) {
			return !bool; // Returns true, locking the arms (B must be held down to work)
		}
			return bool; // Returns false, unlocking the arms
	}

	private double getMultiplier(F310 arm) {
		if(arm.getButton(F310.Button.LEFT_TRIGGER)) {
			return 0.25;
		} 
		else if (arm.getButton(F310.Button.RIGHT_TRIGGER)) {
			return 1;
		}
		else {
			return 0.5;
		}
	}
	
	public void set(double elbow, double shoulder) {
		setElbow(elbow);
		setShoulder(shoulder);
	}
	
	private void setShoulder(double speed) {
		shoulder.set(speed);
	}
		
	private void setElbow(double speed) {
		elbow.set(speed);
	}
}
