package org.firstinspires.ftc.teamcode.teleop.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.handlers.DcMotorExHandler;
import org.firstinspires.ftc.teamcode.handlers.ServoHandler;

@TeleOp(name = "FairDemo")
public class FairDemo extends OpMode {

    private DcMotorEx lf_drive, rf_drive, lb_drive, rb_drive;
    private DcMotorExHandler lift;
    private ServoHandler arm;
    private Servo claw;

    private double currentArmPosition;
    private static final double manualArmSpeed = 0.01, minArm = 0.75, maxArm = 1,
            openClaw = 0.3, closedClaw = 0.46, powerFactor = 0.7095;

    @Override
    public void init() {
        DcMotorExHandler.setHardwareMap(hardwareMap);
        ServoHandler.setHardwareMap(hardwareMap);
        this.lf_drive = hardwareMap.get(DcMotorEx.class, "lf_drive");
        this.rf_drive = hardwareMap.get(DcMotorEx.class, "rf_drive");
        this.lb_drive = hardwareMap.get(DcMotorEx.class, "lb_drive");
        this.rb_drive = hardwareMap.get(DcMotorEx.class, "rb_drive");
        lf_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        lb_drive.setDirection(DcMotorSimple.Direction.REVERSE);
//        this.lift = hardwareMap.get(DcMotorEx.class, "lift");
        this.lift = new DcMotorExHandler("lift");
        this.lift.setDirection(DcMotorSimple.Direction.REVERSE);
//        this.lift.setPositionBounds(-4200, 0);
//        this.lift = hardwareMap.get(DcMotorEx.class, "lift");
        this.arm = new ServoHandler("arm");
        this.arm.setPosition(0.75);
//        this.arm.scaleRange(0, 0.6);
        this.claw = hardwareMap.get(Servo.class, "claw");

        this.currentArmPosition = arm.getPosition();
    }

    private void moveBot(float vertical, float pivot, float horizontal) {
//        pivot *= 0.6;
        pivot *= 0.855f;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));
    }

    public void loop() {
        moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
        lift.setPower(gamepad2.right_stick_y);
        arm.setPower(-gamepad2.left_stick_y);
        /*
        if (gamepad2.left_stick_y < 0 && !gamepad2.a && !gamepad2.b && !gamepad2.y) {
            currentArmPosition += manualArmSpeed; // increase by a small step
            if (currentArmPosition >= maxArm) currentArmPosition = maxArm;
        } else if (gamepad2.left_stick_y > 0 && !gamepad2.a) {
            currentArmPosition -= manualArmSpeed; // decrease by a small step
            if (currentArmPosition <= minArm) currentArmPosition = minArm;
        }*/

        // grab claw
        if (gamepad2.left_trigger > 0.1) {
            claw.setPosition(closedClaw);
        }
        // open claw
        if (gamepad2.right_trigger > 0.1) {
            claw.setPosition(openClaw);
        }

        telemetry.addData("lift power sent: ", gamepad2.right_stick_y);
        telemetry.addData("lift power actual: ", lift.getPower());
        telemetry.addData("lift pos actual: ", lift.getPosition());
//        telemetry.addData("arm sent pos: ", currentArmPosition);
        telemetry.addData("arm pos actual: ", arm.getPosition());
        telemetry.addData("claw pos: ", claw.getPosition());
        telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
        telemetry.addData("lf_drive power: ", lf_drive.getPower());
        telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
        telemetry.addData("rf_drive power: ", rf_drive.getPower());
        telemetry.addData("gamepad2 right trigger: ", gamepad2.right_trigger);
        telemetry.addData("gamepad2 left trigger: ", gamepad2.left_trigger);
        telemetry.addData("gamepad2 right stick y: ", gamepad2.right_stick_y);
        telemetry.addData("gamepad1 left stick x: ", gamepad1.left_stick_x);
        telemetry.addData("gamepad1 left stick y: ", gamepad1.left_stick_y);
        telemetry.addData("gamepad1 right stick x: ", gamepad1.right_stick_x);
        telemetry.addData("gamepad1 right stick y: ", gamepad1.right_stick_y);
        telemetry.addData("power factor:", powerFactor);
        telemetry.addLine(" ");
        telemetry.addLine(" ");
        telemetry.addData("gamepad2 right bumper: ", gamepad2.right_bumper);
        telemetry.addData("gamepad2 left bumper: ", gamepad2.left_bumper);
        telemetry.addData("gamepad2 dpad up: ", gamepad2.dpad_up);
        telemetry.addData("gamepad2 dpad down: ", gamepad2.dpad_down);
        telemetry.addData("gamepad1 dpad up: ", gamepad1.dpad_down);
        telemetry.addData("gamepad1 dpad down: ", gamepad1.dpad_down);
        telemetry.addData("gamepad2 a: ", gamepad2.a);
        telemetry.addData("gamepad2 b: ", gamepad2.b);
        telemetry.addData("gamepad2 y: ", gamepad2.y);
        telemetry.update();

    }
}
