package Mechenism;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotorEx intakeMotor;
    private double speed = 1;
    public Intake(HardwareMap hardwareMap) {
        intakeMotor = (DcMotorEx) hardwareMap.dcMotor.get("intake");
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorEx.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setPower(0.0);
    }

    public void runIntake(){
        intakeMotor.setPower(1.0);
    }
    public void stop() {
        intakeMotor.setPower(0);
    }
    public void runExtake() {
        intakeMotor.setPower(-1.0);
    }
}
