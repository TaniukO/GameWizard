package util;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.StrictMath.PI;
import static util.Constants.*;


/**
 * Created by sAnCho on 03.12.2016.
 */
public class Walking {






    public static  void moveStrafe(){

        List<Projectile> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getProjectiles()));
        for (Projectile target : targets) {
            if(target.getDistanceTo(self)>self.getCastRange()+self.getRadius()){
                continue;
            }
            Point2D point1=new Point2D(target.getX(),target.getY());
            double angle=point1.getAbsoluteAngleTo(target.getX()+target.getSpeedX(),target.getY()+target.getSpeedY());
            //        if((self.getAngle()+self.getAngleTo(target))<=(angle+PI/34 )&& (self.getAngle()+self.getAngleTo(target))>=(angle-PI/34)){
            if(point1.getAbsoluteAngleTo(self)<=angle+PI/24 && point1.getAbsoluteAngleTo(self)>=angle-PI/24){
                Constants.temper=-1;
                isturn=false;
                isgo=false;
                if(toTurn<=13){
                    goTo(StaticUtil.chengeDistanseAlongLine(StaticUtil.toPoint2D(self),angle+PI/2,46));
                }else{
                    goTo(StaticUtil.chengeDistanseAlongLine(StaticUtil.toPoint2D(self),angle-PI/2,46));
                }
                if(toTurn>=26){toTurn=0;}
                toTurn=toTurn+1;
            }
        }
    }

    public static  void goTo(double x,double y){
        goTo(x,y,(float)(4*1.5));
    }



    public static  void goTo(double x,double y,double speed){
        goTo(x,y,(float) speed);
    }

    public static  void goTo(Point2D point){
        goTo(point.getX(),point.getY(),(float)(4*1.5));
    }

    public static  void goTo(Point2D point ,double speed){
        goTo(point.getX(),point.getY(),(float) speed);
    }
    /**
     *  способ перемещения волшебника.
     */
    private static void goTo(double x,double y,float speed) {
/*
        if(MyTiks%25==0){
            System.out.println("goto  x: " +(x+1)+", y "+(y+1));
        }
*/

            double alfa=self.getAngleTo(x,y);

            if(isturn&&Constants.temper<=0 || self.getLife()<( self.getMaxLife() * Constants.LOW_HP_FACTOR )){
                move.setTurn(alfa);
                isturn=false;
            }

            double strToSet=StrictMath.sin(alfa)*self.getDistanceTo(x,y);
            double forwardToSet=StrictMath.cos(alfa)*self.getDistanceTo(x,y);

            double pifagora=StrictMath.sqrt(strToSet*strToSet+forwardToSet*forwardToSet);
            if(pifagora>speed){
                double koof=pifagora/speed;
                strToSet/=koof;
                forwardToSet/=koof;
            }


            move.setStrafeSpeed(strToSet);
            move.setSpeed(forwardToSet);


    }






    public static  Point2D getNextWaypointByPath(Point2D[] path) {
        if(waypointIndex>path.length-1 || waypointIndex<0){
            waypointIndex=0;
        }

        if (path[waypointIndex].getDistanceTo(self) <= Constants.WAYPOINT_RADIUS) {
            return path[++waypointIndex];
        }
        Point2D waypoint = path[waypointIndex];
        if(waypoint.getDistanceTo(self)>300+WAYPOINT_RADIUS) {
            for (int i = 0; i < path.length-1; i++) {
                if (waypoint.getDistanceTo(self) <= path[i+1].getDistanceTo(self)) {
                    waypoint=path[i+1];
                }
            }
        }
        return waypoint;
    }

    /**
     * Данный метод предполагает, что все ключевые точки на линии упорядочены по уменьшению дистанции до последней
     * ключевой точки. Перебирая их по порядку, находим первую попавшуюся точку, которая находится ближе к последней
     * точке на линии, чем волшебник. Это и будет следующей ключевой точкой.
     * <p>
     * Дополнительно проверяем, не находится ли волшебник достаточно близко к какой-либо из ключевых точек. Если это
     * так, то мы сразу возвращаем следующую ключевую точку.
     */


    public static  Point2D getNextWaypoint() {
        Point2D waypoint = waypoints[waypointIndex];
        if (waypoint.getDistanceTo(self) <= Constants.WAYPOINT_RADIUS) {
            if (waypoints.length>waypointIndex) {
                waypointIndex += 1;
            }
        }
        return waypoints[waypointIndex];
    }



    /**
     * Действие данного метода абсолютно идентично действию метода {@code getNextWaypoint}, если перевернуть массив
     * {@code waypoints}.
     */
    public static  Point2D getPreviousWaypoint() {
        Point2D waypoint = waypoints[waypointIndex];
        if (waypoint.getDistanceTo(self) <= Constants.WAYPOINT_RADIUS) {
            if (0<waypointIndex) {
                waypointIndex -= 1;
            }
        }
        return waypoints[waypointIndex];
    }


}
