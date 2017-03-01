package util;

import model.Unit;

import static java.lang.StrictMath.atan2;

/**
 * Created by Roman on 29.11.2016.
 */

public final class Point2D {
        private final double x;
        private final double y;

        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getAbsoluteAngleTo(Point2D point){
            return this.getAbsoluteAngleTo( point.getX(),point.getY());
        };
        public double getAbsoluteAngleTo(Unit point){
            return this.getAbsoluteAngleTo( point.getX(),point.getY());
        };

        public double getAbsoluteAngleTo(double x,double y){
            if(this.x-x==0){
                x+=0.00000000001;
            }   return atan2((y - this.y),( x - this.x));
        };

        public double getDistanceTo(double x, double y) {
            return StrictMath.hypot(this.x - x, this.y - y);
        }

        public double getDistanceTo(Point2D point) {
            return getDistanceTo(point.x, point.y);
        }

        public double getDistanceTo(Unit unit) {
            return getDistanceTo(unit.getX(), unit.getY());
        }

    @Override
    public String toString() {
        return " x "+x+" y "+y+" ";
    }
}
