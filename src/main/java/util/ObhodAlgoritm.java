package util;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.StrictMath.abs;
import static util.Constants.self;
import static util.Constants.world;

/**
 * Created by sAnCho on 03.12.2016.
 */
public class ObhodAlgoritm {

    public static Point2D[] cutLineBeetwenWPoint(Point2D point){

        List<LivingUnit> objsInRange=getObjInRange(point);
        List<Point2D> cutPoint=new ArrayList<Point2D>();

        if (objsInRange.size()>0){

            point=recursIsIn(point);
            //System.out.println("goto  x: " +point.getX()+", y "+point.getY());
            Point2D dp;
            cutPoint.add(point);
            for(LivingUnit obj: objsInRange){
                dp=commonSectionCircle(StaticUtil.toPoint2D(self),point,obj);
                if(dp==null){
                    continue;
                }else{
                    cutPoint.add(dp);
                }
            }

            if(cutPoint.size()>=1){
                Point2D t;
                for (int i = 0; i < cutPoint.size(); i++)
                    for (int j = i + 1; j < cutPoint.size(); j++)
                        if (cutPoint.get(j).getDistanceTo(self) <cutPoint.get(i).getDistanceTo(self)) {
                            t = cutPoint.get(i);
                            cutPoint.set(i,cutPoint.get(j));
                            cutPoint.set(j,t);
                        }

                Point2D[] arrCutPoint=cutPoint.toArray(new Point2D[cutPoint.size()]);

                return arrCutPoint;
            }else{
                return new Point2D[]{point};
            }
        }
        return new Point2D[]{point};

    }

    public static Point2D commonSectionCircle(Point2D point1, Point2D point2, LivingUnit unitC){
        double EPS=1;
        double	x1 = point1.getX() - unitC.getX();
        double	y1 = point1.getY() - unitC.getY();
        double	x2 = point2.getX() - unitC.getX();
        double	y2 = point2.getY() - unitC.getY();

        double r=unitC.getRadius()+35;
        double a=y1-y2;
        double b=x2-x1;
        if(a==b&&b==0){
            a=0.1;b=0.1;
        }

        double c=x1*y2-x2*y1;
        double crab=c*c - r*r*(a*a+b*b);


        Point2D point=new Point2D(unitC.getX()-a*c/(a*a+b*b),unitC.getY()-b*c/(a*a+b*b));

        if (crab > EPS){
            return null;
        }
        else if (abs(crab) < EPS) {

            return StaticUtil.chengeDistanseAlongLine(StaticUtil.toPoint2D(unitC),point,35+unitC.getRadius()+4);
        }

        return StaticUtil.chengeDistanseAlongLine(StaticUtil.toPoint2D(unitC),point,35+unitC.getRadius()+4);
    }

    public static Point2D recursIsIn(Point2D point){

        CircularUnit obj=StaticUtil.getNearestObj(point);
        if (obj!=null){
            if(point.getDistanceTo(obj)<obj.getRadius()+self.getRadius()+2){
                //System.out.println("goto  x: " +point.getX()+", y "+point.getY());
                point=StaticUtil.chengeDistanseAlongLine(StaticUtil.toPoint2D(self),point,self.getDistanceTo(point.getX(),point.getY())+3 );//obj.getRadius()-self.getRadius()+point.getDistanceTo(obj));
                //System.out.println(" x  "+self.getX()+" y " +self.getY());
                //.out.println("goto  x: " +point.getX()+", y "+point.getY());

                point=recursIsIn(point);
                //System.out.println("goto  x: " +point.getX()+", y "+point.getY());

            }}
        return point;
    }


    public static List<LivingUnit> getObjInRange(Point2D point){

        List<LivingUnit> objs = new LinkedList<LivingUnit>();
        objs.addAll(Arrays.asList(world.getBuildings()));
        objs.addAll(Arrays.asList(world.getWizards()));
        objs.addAll(Arrays.asList(world.getMinions()));
        objs.addAll(Arrays.asList(world.getTrees()));

        List<LivingUnit> objsInRange = new LinkedList<LivingUnit>();
        for(LivingUnit obj:objs){
            if(obj.getDistanceTo(self) <  (point.getDistanceTo(self)+obj.getRadius()) && point.getDistanceTo(obj)< point.getDistanceTo(self)+obj.getRadius() ) {
                objsInRange.add(obj);
            }

        }
        return objsInRange;
    }
}
