package org.firstinspires.ftc.teamcode.teleop.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MotorTest")
public class MotorTest extends LinearOpMode {

    private DcMotorEx lf_drive, lb_drive, rf_drive, rb_drive;
    @Override
    public void runOpMode() {
        this.lf_drive = hardwareMap.get(DcMotorEx.class, "lf_drive");
        this.rf_drive = hardwareMap.get(DcMotorEx.class, "rf_drive");
        this.lb_drive = hardwareMap.get(DcMotorEx.class, "lb_drive");
        this.rb_drive = hardwareMap.get(DcMotorEx.class, "rb_drive");
        lf_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        lb_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        lf_drive.setPower(1);
        sleep(3000);
        lf_drive.setPower(0);
        rf_drive.setPower(1);
        sleep(3000);
        rf_drive.setPower(0);
        rb_drive.setPower(1);
        sleep(3000);
        rb_drive.setPower(0);
        lb_drive.setPower(1);
        sleep(3000);
        lb_drive.setPower(0);
    }
}
