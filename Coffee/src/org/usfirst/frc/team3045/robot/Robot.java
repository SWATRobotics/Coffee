package org.usfirst.frc.team3045.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.CANTalon;



public class Robot extends IterativeRobot {
	
	public CANTalon talon0 = new CANTalon(0);
	public CANTalon talon1 = new CANTalon(2);
	public CANTalon talon2 = new CANTalon(3);
	public CANTalon talon5 = new CANTalon(5);
	public CANTalon midWheelLeft = new CANTalon(4);
	public CANTalon midWheelRight = new CANTalon(1);
	public CANTalon shooterControl = new CANTalon(6);
	public CANTalon shooterFollow = new CANTalon(7);
	public CANTalon winchControl = new CANTalon(8);
	public CANTalon winchFollow = new CANTalon(9);
	
	public RobotDrive robotDriveFront = new RobotDrive(talon0,talon2);
	public RobotDrive robotDriveRear = new RobotDrive(talon1,talon5);
	
	public Compressor cp = new Compressor();
	
	public Solenoid gearSolenoid = new Solenoid(1, 0);
	public DoubleSolenoid gearIntake = new DoubleSolenoid(0, 0, 1);
	public DoubleSolenoid Shift = new DoubleSolenoid(0, 4, 5);
	public Joystick jsR = new Joystick(0);
	public Joystick jsL = new Joystick(1);


	public void robotInit() {
		talon0.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon5.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		talon0.setCurrentLimit(20);
		talon0.EnableCurrentLimit(true);
		talon1.setCurrentLimit(20);
		talon1.EnableCurrentLimit(true);
		talon2.setCurrentLimit(20);
		talon2.EnableCurrentLimit(true);
		talon5.setCurrentLimit(20);
		talon5.EnableCurrentLimit(true);
		
		midWheelLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		midWheelLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		midWheelRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		midWheelRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		winchControl.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		winchFollow.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		winchFollow.changeControlMode(CANTalon.TalonControlMode.Follower);
		winchFollow.set(6);
	}
	
	public void StopRobot() {
		robotDriveFront.setLeftRightMotorOutputs(0, 0); //stops robot
		robotDriveRear.setLeftRightMotorOutputs(0, 0);
		midWheelLeft.set(0);
		midWheelRight.set(0);
	}

	
	public void autonomousInit() {
		cp.start();
	}

	public void autonomousPeriodic() {
		
	}
	
	public void teleopInit() {
		cp.start();
		Shift.set(DoubleSolenoid.Value.kOff);
		talon0.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		talon5.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		winchControl.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		winchFollow.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
	}
	
	public void Drive() {
		double leftInput = 0.70 * jsL.getY();
		double rightInput = 0.70 * jsR.getY();
		double leftMidwheelInput = 0.70 * jsL.getY();
		double rightMidwheelInput = 0.70 * jsR.getY();
		robotDriveFront.setLeftRightMotorOutputs(leftInput, rightInput);
		robotDriveRear.setLeftRightMotorOutputs(leftInput, rightInput);
		midWheelLeft.set(leftMidwheelInput);
		midWheelRight.set(-1 * rightMidwheelInput);
	}
	
	public void Winch() {
		double winchInput = jsR.getZ();

		if (jsR.getRawButton(1)) {
			winchControl.set(1 * Math.abs(winchInput));
		} else {
			winchControl.set(0);
		}
	}

	public void teleopPeriodic() {
		Drive();
		Winch();
	}

	public void testPeriodic() {
		
	}
}

