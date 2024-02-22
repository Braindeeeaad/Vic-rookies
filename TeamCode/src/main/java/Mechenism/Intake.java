package Mechenism;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intakeMotor;
    private double speed = 1;
    public Intake(HardwareMap hardwareMap) {
        intakeMotor = (DcMotor) hardwareMap.dcMotor.get("intake");
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setPower(0.0);
    }

    public void runIntake(){
        intakeMotor.setPower(speed);
    }
    public void stop() {
        intakeMotor.setPower(0);
    }
    public void runExtake() {
        intakeMotor.setPower(-speed);
    }
}
