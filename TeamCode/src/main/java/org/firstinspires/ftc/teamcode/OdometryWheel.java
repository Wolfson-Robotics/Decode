package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.auto.AutoBase;

@Autonomous(name = "Odometry Pod", group = "Auto")
public class OdometryWheel extends AutoBase {

    //Add it here
//    DcMotor podMotor;
    DcMotor RbMotor;

    @Override
    public void init() {
        super.init();
        RbMotor = hardwareMap.get(DcMotor.class, "rb_drive");
//        podMotor = hardwareMap.get(DcMotor.class, "pod");
//        podMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        podMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        int podPosition = RbMotor.getCurrentPosition();
        telemetry.addData("Odometry Pod Position", podPosition);
        telemetry.update();
    }

}
