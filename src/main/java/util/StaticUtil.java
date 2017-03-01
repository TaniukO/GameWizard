package util;

import model.*;

import java.util.*;

import static java.lang.StrictMath.PI;
import static util.Constants.self;
import static util.Constants.world;

/**
 * Created by sAnCho on 03.12.2016.
 */
public class StaticUtil {

    /**
     * Находим ближайшую цель для атаки, независимо от её типа и других характеристик.
     */
    public static  LivingUnit getNearestTarget() {
        List<LivingUnit> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));
        targets.addAll(Arrays.asList(world.getWizards()));
        targets.addAll(Arrays.asList(world.getMinions()));

        LivingUnit nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (LivingUnit target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }
    public static  List<LivingUnit> getObjs() {
        List<LivingUnit> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));
        targets.addAll(Arrays.asList(world.getWizards()));
        targets.addAll(Arrays.asList(world.getMinions()));
        targets.addAll(Arrays.asList(world.getTrees()));
        return targets;
    }
    public static  CircularUnit getNearestObj(Point2D point) {
        List<LivingUnit> targets = getObjs();

        CircularUnit nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;
        double distance;

        for (CircularUnit target : targets) {

            distance = point.getDistanceTo(target)-target.getRadius();

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }

    public static  List<LivingUnit> getObjInBounding(int minX,int maxX,int minY,int maxY) {
        List<LivingUnit> targets = getObjs();
        List<LivingUnit> returnList=new LinkedList<>();
        double x;
        double y;
        for (LivingUnit target : targets) {
             x = target.getX();
             y = target.getY();

            if(minX>=x) continue;
            if(maxX<=x) continue;
            if(minY>=y) continue;
            if(maxY<=y) continue;
            returnList.add(target);
        }

        return returnList;
    }

    public static  LivingUnit getLifeLowTarget() {
        List<LivingUnit> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));
        targets.addAll(Arrays.asList(world.getWizards()));


        LivingUnit nearestTarget = null;
        double minTargetLife = Double.MAX_VALUE;

        for (LivingUnit target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double life = target.getLife();

            if (life < minTargetLife) {
                nearestTarget = target;
                minTargetLife = life;
            }
        }

        return nearestTarget;
    }

    public static  Bonus getNearestBonus() {
        List<Bonus> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBonuses()));

        Bonus nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (Bonus target : targets) {
            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }
    public static  LivingUnit getNearestTree() {
        //      List<LivingUnit> targets = new ArrayList<>();
        List<LivingUnit> targetsTree = new ArrayList<>();
        targetsTree.addAll(Arrays.asList(world.getTrees()));
        targetsTree.addAll(Arrays.asList(world.getMinions()));

        LivingUnit nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

       for (LivingUnit target : targetsTree) {
			if (target.getFaction() != Faction.NEUTRAL || target.getFaction() != Faction.OTHER) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }
/*

        for (LivingUnit target : targetsTree) {
            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }
*/

        return nearestTarget;
    }

    public static  Wizard getNearestWizard() {
        List<Wizard> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getWizards()));

        Wizard nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (Wizard target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }


    public static  Minion getNearestMinion() {
        List<Minion> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getMinions()));

        Minion nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (Minion target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }

    public static  Building getNearestBuildings(){
        List<Building> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));

        Building nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (Building target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;

    }




    public static Point2D chengeDistanseAlongLine(Point2D point1, Point2D point2, double gipot){
        return chengeDistanseAlongLine(point1,point1.getAbsoluteAngleTo(point2), gipot);
    }

    public static Point2D chengeDistanseAlongLine(Point2D point,double angle , double gipot){

        double dx=point.getX()+gipot*StrictMath.cos(angle);
        double dy=point.getY()+gipot*StrictMath.sin(angle);
        return new Point2D(dx,dy);
    }




    public static Point2D toPoint2D(Unit unit){
        return new Point2D(unit.getX(),unit.getY());
    }
    public static Point2D toPoint2D(double x,double y){
        return new Point2D(x,y);
    }


    public static double[] komputeBounding(int minX,int maxX,int minY,int maxY){
        float permeability=0;
        float preoccupancy=0;
        List<LivingUnit> objs=getObjInBounding( minX, maxX, minY, maxY);
        for(LivingUnit obj:objs){
            permeability+=obj.getRadius()*obj.getRadius()*PI;
            if(obj.getFaction()==Faction.RENEGADES){
                preoccupancy+=obj.getLife();
            }else{
            if(obj.getFaction()==Faction.ACADEMY){
                preoccupancy-=obj.getLife();
            }}
        }

        return new double[]{permeability * Constants.permeabilityCoff,preoccupancy*Constants.preoccupancyCoff};
    }

    public static  void study(){

    }

}
