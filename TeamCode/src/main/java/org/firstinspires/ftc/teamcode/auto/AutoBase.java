package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.util.GeneralUtils.signClamp;

import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.handlers.DcMotorExHandler;

public abstract class AutoBase extends RobotBase {

    //April Tag Id's
    public final int BLUE_TAG = 20;
    public final int RED_TAG = 24;
    public final int GPP_TAG = 21;
    public final int PGP_TAG = 22;
    public final int PPG_TAG = 23;

    @Override
    public void init() {
        super.init();
        lift.resetEncoder();
        this.powerFactor = 0.725;
    }


    // notes:
    // rf_drive, when going forward, goes into the negative direction positionally
    // 12 inches (or a foot) is 73.6770894730908 robot inches
    /**
     * @param distIN distance in inches to move
     * @param vertical positive value goes forward, negative goes backward
     * @param pivot positive pivot is clockwise, negative pivot is counterclockwise
     * @param horizontal positive horizontal is right, negative horizontal is left
     */
    public void moveBot(double distIN, double vertical, double pivot, double horizontal) {
        // 23 motor tics = 1 IN
        int targetPos;

        // Drive power assignments
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

        // Horizontal strafe
        if (horizontal != 0) {
            targetPos = lf_drive.getCurrentPosition() + (int) ((distIN * TICS_PER_INCH) * signClamp(horizontal));
            while (!lf_drive.reachedPos(targetPos) && opModeIsActive()) {
                idle();
            }
            /*
            if (posNeg == 1) {
                // Right goes negative
                while (lf_drive.getCurrentPosition() < motorTics && opModeIsActive()) {
                    idle();
                }
            } else {
                // Left goes positive
                while (lf_drive.getCurrentPosition() > motorTics && opModeIsActive()) {
                    idle();
                }
            }*/
        }
        // Forward/backward motion
        else {
            targetPos = rf_drive.getCurrentPosition() + (int) ((distIN * TICS_PER_INCH) * signClamp(vertical));
            while (!rf_drive.reachedPos(targetPos) && opModeIsActive()) {
                idle();
            }
/*
            if (posNeg == -1) {
                while (rf_drive.getCurrentPosition() > motorTics && opModeIsActive()) {
                    idle();
                }
            } else {
                while (rf_drive.getCurrentPosition() < motorTics && opModeIsActive()) {
                    idle();
                }
            }*/
        }

        removeWheelPower();
    }

    protected void moveBotDiagonal(double horizIN, double vertIN, double vertical, double horizontal) {
        rf_drive.setPower(powerFactor * ((vertical - horizontal)));
        rb_drive.setPower(powerFactor * (vertical + horizontal));
        lf_drive.setPower(powerFactor * (vertical + horizontal));
        lb_drive.setPower(powerFactor * ((vertical - horizontal)));

        int horizDiff = (int) (horizIN * TICS_PER_INCH), vertDiff = (int) (vertIN * TICS_PER_INCH);
        int prevHorizPos = rf_drive.getCurrentPosition(), prevVertPos = lf_drive.getCurrentPosition();
        if (horizDiff > vertDiff) {
            moveBot(lf_drive.getCurrentPosition() + (vertDiff * signClamp(horizontal)), vertical, 0, horizontal);
            moveBot(horizDiff - (rf_drive.getCurrentPosition() - prevHorizPos), 0, 0, horizontal);
        } else {
            moveBot(rf_drive.getCurrentPosition() + (horizDiff * signClamp(vertical)), vertical, 0, horizontal);
            moveBot(vertDiff - (lf_drive.getCurrentPosition() - prevVertPos), vertical, 0, horizontal);
        }
    }
    public void moveBotDiagonal(double horizIN, double vertIN) {
        double angle = Math.toDegrees(Math.atan(vertIN/horizIN));
        moveBot(Math.sqrt(Math.pow(horizIN, 2) + Math.pow(vertIN, 2)), signClamp(vertIN), 0, signClamp(horizIN)*((double)1/45)*(angle-45));
    }

    public void moveBot(double lfPower, double lbPower, double rfPower, double rbPower, DcMotorExHandler cmp, double cmpPos) {
        rf_drive.setPower(powerFactor * rfPower);
        rb_drive.setPower(powerFactor * rbPower);
        lf_drive.setPower(powerFactor * lfPower);
        lb_drive.setPower(powerFactor * lbPower);
        while (!cmp.reachedPos(cmpPos) && opModeIsActive()) {
            idle();
        }
        removeWheelPower();
    }


    protected void turnBot(double degrees) {
        // 13.62 inches is default robot length
        double distUnit = ROBOT_LENGTH/Math.cos(45);
        double distIN = (Math.abs((distUnit * ((degrees*1.75)))/90))*DEG_CONV;
        int motorTics;
        int pivot = signClamp(degrees);
        rf_drive.setPower(powerFactor * (-pivot));
        rb_drive.setPower(powerFactor * (-pivot));
        lf_drive.setPower(powerFactor * (pivot));
        lb_drive.setPower(powerFactor * (pivot));
        motorTics = lf_drive.getCurrentPosition() + (int) Math.round((distIN * TICS_PER_INCH)* pivot);
        while (!lf_drive.reachedPos(motorTics) && opModeIsActive()) {
            idle();
        }
        /*
        if (pivot == 1) {
            while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                idle();
            }
        }
        if (pivot == -1) {
            while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                idle();
            }
        }*/
        removeWheelPower();

    }


    protected double ticsToInches(double tics) {
        return tics/TICS_PER_INCH;
    }


}
