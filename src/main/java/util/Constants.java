package util;

import model.*;

/**
 * Created by Roman on 28.11.2016.
 */
public class Constants{
    public static int temper;//0 - normal;1 - irritated зилий; -1 - frightened напуганий
    public static final double WAYPOINT_RADIUS = 20.0D;
    public static final double LOW_HP_FACTOR = 0.4D;
    public static final double permeabilityCoff=0.00005;
    public static final double preoccupancyCoff=0;

    public static Wizard self;
    public static World world;
    public static Game game;
    public static int waypointIndex;
    public static int toTurn;
    public static LaneType lane;
    public static Point2D[] waypoints;
    public static Move move;
    public static double MyTiks;
    public static boolean isgo;
    public static boolean isturn;


    public static void initConstants(Wizard self,World world,Game game){
        Constants.self=self;
        Constants.world=world;
        Constants.game=game;
    }
}
