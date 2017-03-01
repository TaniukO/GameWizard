package util;

/**
 * Created by sAnCho on 03.12.2016.
 */
public class PluralAngle {

    private double angleMin;
    private double angleMax;

    public double getAngleMin() {
        return angleMin;
    }

    public void setAngleMin(double angleMin) {
        this.angleMin = angleMin;
    }

    public double getAngleMax() {
        return angleMax;
    }

    public void setAngleMax(double angleMax) {
        this.angleMax = angleMax;
    }



    public PluralAngle(double angleMin, double angleMax) {
        this.angleMin = angleMin;
        this.angleMax = angleMax;
    }
}
