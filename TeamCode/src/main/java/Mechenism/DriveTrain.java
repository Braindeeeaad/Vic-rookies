package Mechenism;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveTrain {
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private double x,y,rx,denominator,frontLeftPower,frontRightPower,backLeftPower,backRightPower,rotX,rotY,botHeading;
    private Mode mode;
    private IMU imu;
    public enum Mode {
        Field,
        Robot
    }
    public DriveTrain(HardwareMap hardwareMap) {
        frontLeftMotor = (DcMotor) hardwareMap.get("FL");
        frontRightMotor = (DcMotor) hardwareMap.get("FR");
        backRightMotor = (DcMotor) hardwareMap.get("BR");
        backLeftMotor = (DcMotor) hardwareMap.get("BL");

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        mode=Mode.Robot;
        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
    }
    public void drive(Gamepad gamepad) {
        y= -gamepad.left_stick_y;
        x= gamepad.left_stick_x*1.1;
        rx= gamepad.right_stick_x;

        denominator= Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx) / denominator;
        backLeftPower = (y - x + rx) / denominator;
        frontRightPower = (y - x - rx) / denominator;
        backRightPower = (y + x - rx) / denominator;

        powerMotors(frontLeftPower,frontRightPower,backLeftPower,backRightPower);
    }
    public void slowDrive(Gamepad gamepad) {
        y= -gamepad.left_stick_y;
        x= gamepad.left_stick_x*1.1;
        rx= gamepad.right_stick_x;

        denominator= Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx) / denominator;
        backLeftPower = (y - x + rx) / denominator;
        frontRightPower = (y - x - rx) / denominator;
        backRightPower = (y + x - rx) / denominator;

        powerMotors(frontLeftPower-0.3,frontRightPower-0.3,backLeftPower-0.3,backRightPower-0.3);
    }
    public void fieldDrive(Gamepad gamepad) {
        y = -gamepad.left_stick_y; // Remember, Y stick value is reversed
        x = gamepad.left_stick_x;
        rx = gamepad.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad.options) {
            imu.resetYaw();
        }

        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        frontLeftPower = (rotY + rotX + rx) / denominator;
        backLeftPower = (rotY - rotX + rx) / denominator;
        frontRightPower = (rotY - rotX - rx) / denominator;
        backRightPower = (rotY + rotX - rx) / denominator;

        powerMotors(frontLeftPower,frontRightPower,backLeftPower,backRightPower);
    }
    public void slowFieldDrive(Gamepad gamepad) {
        y = -gamepad.left_stick_y; // Remember, Y stick value is reversed
        x = gamepad.left_stick_x;
        rx = gamepad.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad.options) {
            imu.resetYaw();
        }

        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        frontLeftPower = (rotY + rotX + rx) / denominator;
        backLeftPower = (rotY - rotX + rx) / denominator;
        frontRightPower = (rotY - rotX - rx) / denominator;
        backRightPower = (rotY + rotX - rx) / denominator;

        powerMotors(frontLeftPower/2+0.5,frontRightPower/2+0.5,backLeftPower/2+0.5,backRightPower/2+0.5);
    }
    public void slowFieldDriveBack(Gamepad gamepad) {
        y = -gamepad.left_stick_y; // Remember, Y stick value is reversed
        x = gamepad.left_stick_x;
        rx = gamepad.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad.options) {
            imu.resetYaw();
        }

        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        frontLeftPower = (rotY + rotX + rx) / denominator;
        backLeftPower = (rotY - rotX + rx) / denominator;
        frontRightPower = (rotY - rotX - rx) / denominator;
        backRightPower = (rotY + rotX - rx) / denominator;

        powerMotors(frontLeftPower/2-0.5,frontRightPower/2-0.5,backLeftPower/2-0.5,backRightPower/2-0.5);
    }
    public void reverse() {
        if(mode==Mode.Robot) {
            frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else {
            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    public void changeMode() {
        if(mode==Mode.Robot) {
            mode=Mode.Field;
            reverse();
        }
        else {
            mode=Mode.Robot;
            reverse();
        }
    }
    public Mode getMode() {
        return mode;
    }
    private void powerMotors(double FLPower, double FRPower, double BLPower, double BRPower) {
        frontLeftMotor.setPower(FLPower);
        frontRightMotor.setPower(FRPower);
        backLeftMotor.setPower(BLPower);
        backRightMotor.setPower(BRPower);
    }
}
