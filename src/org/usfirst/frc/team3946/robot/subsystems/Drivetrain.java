package org.usfirst.frc.team3946.robot.subsystems;

import org.usfirst.frc.team3946.robot.RobotMap;
import org.usfirst.frc.team3946.robot.commands.drive.SlideDrivingCommand;

import libraries.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {
	
	public static final double DRIVE_SPEED = 0.4;
    public static final double STRAFE_SPEED = 0.4;
    public boolean slow = false;
    
    public Talon left = new Talon(RobotMap.driveLeftTalon);
    public Talon right = new Talon(RobotMap.driveRightTalon);
    public Talon strafe = new Talon(RobotMap.driveStrafeTalon);
    public BetterGyro gyro = new BetterGyro(RobotMap.driveGyro);
    public Encoder leftEncoder = RobotMap.leftEncoder;
    public Encoder rightEncoder = RobotMap.rightEncoder;

    private final MotorController leftWheel = new MotorController(left);
    private final MotorController rightWheel = new MotorController(right);
    private final MotorController strafeWheel = new MotorController(strafe);
    private final ThreeMotorController driveController = new ThreeMotorController(
            leftWheel, 
            rightWheel, 
            strafeWheel
    );

    private final SlideDrive slideDrive = new SlideDrive(driveController, gyro);
    private final ArcadeDrive arcadeDrive = new ArcadeDrive(driveController);

	public Drivetrain() {
        rightWheel.setInverted(true);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new SlideDrivingCommand());
        gyro.setPIDSourceParameter(PIDSource.PIDSourceParameter.kAngle);
        leftEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        rightEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
    }
    
    public void SlowGear() {
    	slow = !slow;
    	SmartDashboard.putBoolean("Low Gear?", slow);
    }
    
    public SlideDrive getSlideDrive() {
        return slideDrive;
    }

    public ArcadeDrive getArcadeDrive() {
        return arcadeDrive;
    }
    
    public void rotate(double speed) {
    	left.set(speed);
    	right.set(speed);
    }
    
    public void driveStraight(double speed) {
    	double leftSpeed = speed;
    	double rightSpeed = speed;
//    	double gyroValue;
//    	gyroValue = (Math.abs(gyro.getAngle()) / 45);
//    	if(gyro.getAngle() > 0){
//    		leftSpeed = leftSpeed * (1 - gyroValue);
//    		rightSpeed = rightSpeed * (1 + gyroValue);
//    	} else {
//    		leftSpeed = leftSpeed * (1 + gyroValue);
//    		rightSpeed = rightSpeed * (1 - gyroValue);
//    	}
    	left.set(leftSpeed);
    	right.set(-rightSpeed);
    }
    
    public void strafe(double speed) {
    	double strafeSpeed = speed;
    	strafe.set(strafeSpeed);
    }
    
    public void setMotor(double leftSpeed, double rightSpeed, double strafeSpeed){
    	left.set(leftSpeed);
    	right.set(rightSpeed);
    	strafe.set(strafeSpeed);
    }
    
    public BetterGyro getGyro() {
    	return gyro;
    }
    
    public Encoder getLeftEncoder() {
    	return leftEncoder;
    }
    
    public Encoder getRightEncoder() {
    	return rightEncoder;
    }
    
    public double getAverageSpeed() {
    	double speed = ((leftEncoder.getRate() + rightEncoder.getRate()) / 2);
    	return speed;
    }
}
