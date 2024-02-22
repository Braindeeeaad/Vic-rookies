package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Mechenism.DriveTrain;
import Mechenism.Intake;

@TeleOp
public class RookieTeleOp extends LinearOpMode {
    private DriveTrain dt;
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DriveTrain frontLeftMotor = new DriveTrain(hardwareMap);
        DriveTrain frontRightMotor = new DriveTrain(hardwareMap);
        DriveTrain backLeftMotor = new DriveTrain(hardwareMap);
        DriveTrain backRightMotor = new DriveTrain(hardwareMap);
        Intake intakeMotor = new Intake(hardwareMap);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            frontRightMotor.fieldDrive(gamepad1);
            frontLeftMotor.fieldDrive(gamepad1);
            backRightMotor.fieldDrive(gamepad1);
            backLeftMotor.fieldDrive(gamepad1);
            boolean intakeOn = false;
            boolean extakeOn = false;

            if(gamepad1.x) {
                intakeOn=true;
                extakeOn=false;
            }
            else if(gamepad1.b) {
                extakeOn=true;
                intakeOn=false;
            }

            if(intakeOn) {
                intakeMotor.runIntake();
                frontLeftMotor.slowFieldDrive(gamepad1);
                frontRightMotor.slowFieldDrive(gamepad1);
                backLeftMotor.slowFieldDrive(gamepad1);
                backRightMotor.slowFieldDrive(gamepad1);
            }
            else if(extakeOn) {
                intakeMotor.runExtake();
                frontLeftMotor.slowFieldDriveBack(gamepad1);
                frontRightMotor.slowFieldDriveBack(gamepad1);
                backLeftMotor.slowFieldDriveBack(gamepad1);
                backRightMotor.slowFieldDriveBack(gamepad1);
            }
            else {
                intakeMotor.stop();
            }
        }
    }
}