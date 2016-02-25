package org.nashua.tt151;

import org.nashua.tt151.lib.F310;
import org.nashua.tt151.system.Arm;
import org.nashua.tt151.system.TankDrive;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * @author Harrison Pound
 * @author Madeline Esp
 * @author Ruthra Govindaraj
 * @author Randy Wang
 * @author Shannon McInnis
 * @author Julia Zhang
 * 
 * @version 02-22-2016 (1.7)
/**
 * The VM is configured to automatically run this class, and to call the 
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends SampleRobot {


	F310 drive = new F310(0, 0.1);
	F310 arm = new F310(1, 0.1);
	private boolean autoInit = false;
	
	private CameraServer cameraServer;

	int targetDistance = 17;

	private SendableChooser autoChooser = new SendableChooser();

	public enum AutoModes {
		AutoForward,
		AutoRight,
		AutoLeft
	}

	public enum AutoGoals{
		LowBar,
		SpyBot,
		Moat,
		RoughTerrain
	}
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Init Camera Server
		cameraServer = CameraServer.getInstance();
		cameraServer.setQuality(50);
		cameraServer.startAutomaticCapture("cam0");
		TankDrive.getInstance().init();
		autoChooser.addDefault("AutoForward", AutoModes.AutoForward);
		autoChooser.addObject("AutoLeft", AutoModes.AutoLeft);
		autoChooser.addObject("AutoRight", AutoModes.AutoRight);
		autoChooser.addDefault("LowBar", AutoGoals.LowBar);
		autoChooser.addDefault("SpyBot", AutoGoals.SpyBot);
		autoChooser.addDefault("Moat", AutoGoals.Moat);
		autoChooser.addDefault("RoughTerrain", AutoGoals.RoughTerrain);
	}	

	@Override
	public void autonomous() { 
		//		if(!autoInit) {
		//			TankDrive.getInstance().setDistancePerPulse();
		//			TankDrive.getInstance().resetEncoders();
		//		}

		System.out.println("0: "+TankDrive.getInstance().getAutoSwitch0());
		System.out.println("1: "+TankDrive.getInstance().getAutoSwitch1());


		if(TankDrive.getInstance().getAutoSwitch0()> 1 && TankDrive.getInstance().getAutoSwitch1() <=1) {
			System.out.println("SpyBot");
			TankDrive.getInstance().autoSpyBot();
		}
		else if(TankDrive.getInstance().getAutoSwitch0() >1 && TankDrive.getInstance().getAutoSwitch1() >1) {
			System.out.println("Moat");
			TankDrive.getInstance().autoMoat();
			TankDrive.getInstance().autoForward();
		}
		else if(TankDrive.getInstance().getAutoSwitch0() <=1 && TankDrive.getInstance().getAutoSwitch1() >1) {
			System.out.println("RoughTerrain");
			TankDrive.getInstance().autoRoughTerrain();
			TankDrive.getInstance().autoForward();
		}
		else {
			System.out.println("Default: LowBar");
			TankDrive.getInstance().autoLowBar();
			TankDrive.getInstance().autoForward();

		}


//			AutoModes autoSelected = (AutoModes)autoChooser.getSelected();
//			AutoGoals goalsSelected = (AutoGoals)autoChooser.getSelected();
//			switch (goalsSelected) {
//			case LowBar: { 
//				TankDrive.getInstance().autoLowBar();
//				autoChooser(autoSelected);
//				break;
//			}
//			case SpyBot: { 
//				TankDrive.getInstance().autoSpyBot();
//				autoChooser(autoSelected);
//				break;
//			}
//			case Moat: { 
//				TankDrive.getInstance().autoMoat();
//				autoChooser(autoSelected);
//			
//				break;
//			}
//			case RoughTerrain: { 
//				TankDrive.getInstance().autoRoughTerrain();
//				autoChooser(autoSelected);
//				break;
//			}
//			default :{
//				System.err.println("Shit done broke fam");
//				break;
//			}
//		}
	}

	;;;  ;;; ;;;   ;;;;;   ;;;;;           ;;    ;; ;;; ;;;;;;
	;;;  ;;;       ;;; ;; ;; ;;;           ;;    ;;     ;;  ;;
	;;;;;;;; ;;;   ;;;   ;;  ;;; ;;;;   ;;;;; ;;;;; ;;; ;;;;;;
	;;;  ;;; ;;;   ;;;       ;;; ;  ;   ;  ;; ;  ;; ;;; ;; 
	;;;  ;;; ;;;   ;;;       ;;; ;;;;;; ;;;;; ;;;;; ;;; ;;;;;;    

	;;;  ;;; ;;;   ;;;  ;;;                        ;;;  ;;;;  
	;;;  ;;;       ;;;  ;;;          ;;;;    ;;;;      ;;     ;;;; 
	;;;;;;;; ;;;   ;;;;;;;; ;;;;   ;;;  ;; ;;;  ;; ;;;  ;;;;  ;  ; ;;;;;
	;;;  ;;; ;;;   ;;;  ;;; ;  ;   ;;;     ;;;     ;;;     ;; ;  ; ;;  ;; 
	;;;  ;;; ;;;   ;;;  ;;; ;;;;;; ;;;     ;;;     ;;;  ;;;;  ;;;; ;;  ;;


	private void autoChooser(AutoModes autoSelected) {
		switch(autoSelected) {
			case AutoForward: {
			TankDrive.getInstance().autoForward();
			break;
			}
			case AutoLeft: {
			TankDrive.getInstance().autoForward();
			break;
			}
			case AutoRight: {
			TankDrive.getInstance().autoRight();
			break;
			}
			default: {
			System.err.println("No autonomous mode selected");
			break;
			}
		}		
	}

	public void operatorControl() {
		TankDrive.getInstance().init();
		Arm.getInstance().init();
		
		while(isOperatorControl() && isEnabled()) {
			drive.query();
			arm.query();
			TankDrive.getInstance().operatorControl(drive, arm);
			Arm.getInstance().operatorControl(drive, arm);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void test() {
		LiveWindow.run();
	}

	protected void disabled() {
		TankDrive.getInstance().set(0.0, 0.0);
		Arm.getInstance().set(0.0, 0.0);
	}
}
