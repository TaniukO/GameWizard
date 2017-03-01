package util;

/**
 * Created by Roman on 28.11.2016.
 */
public class PathNode {

    private int x;
    private int y;
    private double preoccupancy;
    private double permeability;
    private double distnceToSelf=1000000;

    private PathNode[] nearestNode90Deg;
    private PathNode[] nearestNode45Deg;


    public double getDistanceFrom() {
        return distnceToSelf;
    }

    void setDistanceFrom(double distnceToSelf) {
        this.distnceToSelf = distnceToSelf;
    }


   public PathNode(){
   }
    public PathNode(int x, int y){
        this.x = x;
        this.y = y;
    }

   public PathNode(int x, int y, float preoccupancy, float permeability, PathNode[] nearestNode90Deg, PathNode[] nearestNode45Deg) {
        this.x = x;
        this.y = y;
        this.preoccupancy = preoccupancy;
        this.permeability = permeability;
        this.nearestNode90Deg = nearestNode90Deg;
        this.nearestNode45Deg = nearestNode45Deg;

    }

public void updateContstants(double preoccupancy, double permeability){
    this.preoccupancy = preoccupancy;
    this.permeability = permeability;
}

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public double getPreoccupancy() {
        return preoccupancy;
    }

    public void setPreoccupancy(double preoccupancy) {
        this.preoccupancy = preoccupancy;
    }

    public double getPermeability() {
        return permeability;
    }

    public void setPermeability(double permeability) {
        this.permeability = permeability;
    }

    public PathNode[] getNearestNode90Deg() {
        return nearestNode90Deg;
    }


    public PathNode[] getNearestNode45Deg() {
        return nearestNode45Deg;
    }


    public void setNearestNode90Deg(PathNode[] nearestNode90Deg) {
        this.nearestNode90Deg = nearestNode90Deg;
    }

    public void setNearestNode45Deg(PathNode[] nearestNode45Deg) {
        this.nearestNode45Deg = nearestNode45Deg;
    }


    @Override
    public String toString() {
        return  "y "+y+" x "+x+" dist "+distnceToSelf;//" preoccupancy " +preoccupancy+" permeability " +permeability+
    }

}
