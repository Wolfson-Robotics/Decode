package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.handlers.camera.VisionPortalCameraHandler;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

//github.com/Wolfson-Robotics/NextYear/blob/main/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/RobotAutoDriveToAprilTagOmni.java
@Autonomous(name = "AprilTagTest", group = "Auto")
public class AprilTagTest extends AutoBase {

    VisionPortal vp;
    AprilTagProcessor aTagProc;


    final double DESIRED_DISTANCE_IN = 12.0; // How close the camera should get in inches


    @Override
    public void init() {
        super.init();

        aTagProc = AprilTagProcessor.easyCreateWithDefaults();
        camera = VisionPortalCameraHandler.createVisionPortalHandler(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aTagProc
        );
    }

    @Override
    public void stop() {
        super.stop();
        camera.closeCamera();
    }

    @Override
    public void loop() {
        List<AprilTagDetection> detections = aTagProc.getDetections();
        if (detections.isEmpty()) { return; }

        AprilTagDetection detectedTag = detections.get(0);

        if (gamepad1.y) {
            ftcPoseTest(detectedTag);
        }
        if (gamepad1.x) {
            ftcDecodeTest(detectedTag);
        }
        telemetry.update();
    }

    void ftcDecodeTest(AprilTagDetection detectedTag) {
        switch (detectedTag.id) {
            case BLUE_TAG:
                telemetry.addLine("Detected blue tag");
                break;
            case RED_TAG:
                telemetry.addLine("Detected red tag");
                break;
            case GPP_TAG:
                telemetry.addLine("Obelisk: GPP");
                break;
            case PGP_TAG:
                telemetry.addLine("Obelisk: PGP");
                break;
            case PPG_TAG:
                telemetry.addLine("Obelisk: PPG");
                break;
        }
    }

    void ftcPoseTest(AprilTagDetection detectedTag) {
        telemetry.addData("Found", "ID %d (%s)", detectedTag.id, detectedTag.metadata.name);
        telemetry.addData("Range",  "%5.1f inches", detectedTag.ftcPose.range);
        telemetry.addData("Bearing","%3.0f degrees", detectedTag.ftcPose.bearing);
        telemetry.addData("Yaw","%3.0f degrees", detectedTag.ftcPose.yaw);

        float drive   = (float) (detectedTag.ftcPose.range - DESIRED_DISTANCE_IN);
        float turn    = (float) detectedTag.ftcPose.bearing;
        float strafe  = (float) detectedTag.ftcPose.yaw;

        moveBot(drive, turn, strafe);
    }

}
