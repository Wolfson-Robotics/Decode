package org.firstinspires.ftc.teamcode.util;

public class GeneralUtils {

    // Methods borrowed from https://github.com/Wolfson-Robotics/ColorSampleDetection/blob/main/src/main/java/org/wolfsonrobotics/colorsampledetection/util/conv/ImageConverter.java
    /**
     * Normalizes a value between a given minimum and maximum.
     */
    public static double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    /**
     * Takes a normalized value and denormalizes it across a new minimum and maximum.
     */
    public static double denormalize(double normalized, double min, double max) {
        return (normalized * (max - min) + min);
    }

    /**
     * Directly converts a given value on a scale of a certain minimum and maximum to
     * a scale of a new, given minimum and maximum.
     */
    public static double convertScale(double value, double oldMin, double oldMax, double newMin, double newMax) {
        return denormalize(normalize(value, oldMin, oldMax), newMin, newMax);
    }

    /**
     * Clamps a given numeric value to -1 or 1 depending on its sign.
     */
    public static int signClamp(double val) {
        return val < 0 ? -1 : 1;
    }



}
