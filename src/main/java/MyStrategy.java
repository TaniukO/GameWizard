 import model.*;
 import util.*;

 import static java.lang.StrictMath.abs;
 import static util.Constants.*;
 import static util.ObhodAlgoritm.cutLineBeetwenWPoint;
 import static util.StaticUtil.getNearestBonus;
 import static util.StaticUtil.toPoint2D;
 import static util.Walking.*;


 import java.util.*;
public final class MyStrategy implements Strategy {
	WaveAlgoritm alg;
	private static Random random;

    private final Map<LaneType, Point2D[]> waypointsByLane = new EnumMap<>(LaneType.class);


	@Override
    public void move(Wizard self, World world, Game game, Move move) {
        initializeStrategy(self, game,world);
        initializeTick(self, world, game, move);
		
		StaticUtil.study();



		int[] selfRemainstInActions = self.getRemainingCooldownTicksByAction();
		int remainstTisk = self.getRemainingActionCooldownTicks();

		LivingUnit nearestTree=StaticUtil.getNearestTree();
		LivingUnit nearestTarget=StaticUtil.getNearestTarget();
		LivingUnit nearestWizard = StaticUtil.getNearestWizard();
		Building nearestBuilding=StaticUtil.getNearestBuildings();
		LivingUnit lowLifeTarget = StaticUtil.getLifeLowTarget();
		Minion nearestMinion = StaticUtil.getNearestMinion();
		
		// Если видим противника
		if (nearestTarget != null) {
		
			if (self.getLife() <( self.getMaxLife() * Constants.LOW_HP_FACTOR ) && self.getDistanceTo(nearestTarget) < self.getCastRange()+nearestTarget.getRadius() + 200 ){
				Constants.temper=-1;
					if(nearestBuilding != null){
						if((nearestBuilding.getDistanceTo(self)>400) && ! (nearestBuilding.getType()==BuildingType.FACTION_BASE)){
							goTo(getNextWaypointByPath(cutLineBeetwenWPoint(getPreviousWaypoint())));
							isgo=false;
						}
					}else{
						goTo(getNextWaypointByPath(cutLineBeetwenWPoint(getPreviousWaypoint())));
						isgo=false;
					}
			}
            moveStrafe();

		
			if (self.getDistanceTo(nearestTarget) <= game.getStaffRange()+nearestTarget.getRadius()+3 ) {
				Constants.temper=2;
				if(selfRemainstInActions[1]==0 && remainstTisk==0){
					double angle = self.getAngleTo(nearestTarget);
					//поворачиваемся к цели.
					move.setTurn(angle);
					isturn=false;
					
					if(abs(angle) < game.getStaffSector() / 2.0D) {
						move.setAction(ActionType.STAFF);
						
					}
				return;	
				}
			}
		}
		
		

			if(nearestMinion != null){
				if(nearestMinion.getDistanceTo(self)<self.getCastRange()){
					nearestTarget=nearestMinion;
				}
			}
		

			
			if(nearestBuilding != null ){
				if(nearestBuilding.getDistanceTo(self)<self.getCastRange()+nearestBuilding.getRadius()){
					nearestTarget = nearestBuilding;
				}
			}
			
			if(nearestWizard != null){
				if(nearestWizard.getDistanceTo(self)<self.getCastRange()+nearestWizard.getRadius()){
					nearestTarget=nearestWizard;
				}
			}
			if(lowLifeTarget != null ){
				if(lowLifeTarget.getDistanceTo(self)<self.getCastRange()+lowLifeTarget.getRadius()+100){
                    if(lowLifeTarget.getDistanceTo(self)<self.getCastRange()+lowLifeTarget.getRadius()){
				        nearestTarget=lowLifeTarget;
                    }
				    if(lowLifeTarget.getLife()<24 && Constants.temper >= 0){
						if(lowLifeTarget.getDistanceTo(self)>self.getCastRange()-100){
							goTo(getNextWaypointByPath(cutLineBeetwenWPoint(toPoint2D(nearestTarget))));
						}
					}
				}
			}


		if(nearestMinion != null){
			if(nearestMinion.getType() == MinionType.ORC_WOODCUTTER && 	nearestMinion.getDistanceTo(self) <= (game.getStaffRange()+nearestMinion.getRadius())*2){
				Constants.temper=2;
				//goTo(getNextWaypointByPath(cutLineBeetwenWPoint(getPreviousWaypoint())));

				goTo(StaticUtil.chengeDistanseAlongLine(toPoint2D(self),nearestMinion.getAngleTo(self)+nearestMinion.getAngle(),nearestMinion.getRadius()*5));
				isgo=false;
				nearestTarget=nearestMinion;
			}
		}


		//____________________________________________________________
        //____________________________________________________________

	



        // Если видим противника
			if (nearestTarget != null) {
			
			double distance=self.getDistanceTo(nearestTarget);
			
				if (distance <= self.getCastRange()+nearestTarget.getRadius()) {		
				Constants.temper=1;
					if(remainstTisk==0){
					if(selfRemainstInActions[2]==0){
					Constants.temper=2;
						atack(nearestTarget);
					}}
				}
			}


			if(nearestTree!=null){
				if (self.getDistanceTo(nearestTree) <= game.getStaffRange()+nearestTree.getRadius() && isturn  ) {

					if(selfRemainstInActions[1]==0 && remainstTisk==0){
						double angle = self.getAngleTo(nearestTree);
						//поворачиваемся к цели.
						move.setTurn(angle);
						isturn=false;
						if(abs(angle) < game.getStaffSector() / 2.0D) {
							move.setAction(ActionType.STAFF);

						}

					}
				}
			}	
			

			



        // двигаемся вперёд.
		
		if(isgo){
			if(nearestBuilding != null){
			if(self.getDistanceTo(game.getMapSize()-100D,100D)<35+Constants.WAYPOINT_RADIUS){
				if(nearestBuilding.getDistanceTo(self)<=600 && StaticUtil.getNearestBuildings().getType()==BuildingType.FACTION_BASE && Constants.temper>=0 &&self.getLife() >( self.getMaxLife() * 0.7D )){
					goTo(getNextWaypointByPath(cutLineBeetwenWPoint(toPoint2D(game.getMapSize()-100D,100D))));
					Constants.temper=2;
					isgo=false;
				}
			}
		}}

		Bonus nearestBonus = getNearestBonus();
		if (nearestBonus !=null && isgo){
			if(nearestBonus.getDistanceTo(self)<=self.getVisionRange()+nearestBonus.getRadius() && Constants.temper<=1){
				isgo=false;
				goTo(getNextWaypointByPath(cutLineBeetwenWPoint(new Point2D(nearestBonus.getX(),nearestBonus.getY()))));
			}
		}

		if(Constants.temper==0 ){
			Point2D pointt=getNextWaypointByPath(alg.getPath());
            System.out.println("goto  x: " +pointt.getX()+", y "+pointt.getY());
		goTo(pointt);//getNextWaypointByPath(cutLineBeetwenWPoint(
		}

    }


	 
//----------------------------------- //-----------------------------------
	 
	private void atack(LivingUnit nearestTarget){
		
			double angle = self.getAngleTo(nearestTarget);
				//поворачиваемся к цели.
            move.setTurn(angle);
				
			if(abs(angle) < game.getStaffSector() / 2.0D) {
				// атакуем.
				double distance = self.getDistanceTo(nearestTarget);
				move.setAction(ActionType.MAGIC_MISSILE);
				move.setCastAngle(angle);
				move.setMinCastDistance(distance - nearestTarget.getRadius() + game.getMagicMissileRadius());
			}
	}
		
		
	 //----------------------------------- //-----------------------------------
	 
    private void initializeStrategy(Wizard self, Game game,World world) {
        if (random == null) {
			Constants.self = self;
			Constants.game = game;
			Constants.world = world;

			alg = new WaveAlgoritm();
			alg.initialize();


            MyTiks=game.getTickCount();
            random = new Random(game.getRandomSeed());

            Constants.waypointIndex=0;
            double mapSize = game.getMapSize();

            waypointsByLane.put(LaneType.MIDDLE, new Point2D[]{
                    new Point2D(100.0D, mapSize - 100.0D),
                    random.nextBoolean()
                            ? new Point2D(600.0D, mapSize - 200.0D)
                            : new Point2D(200.0D, mapSize - 600.0D),

                    new Point2D(800.0D, mapSize - 800.0D),
                    new Point2D(mapSize * 0.4D, mapSize * 0.6D),
                    random.nextBoolean()
                            ? new Point2D(1600, 1600)
                            : new Point2D(2400, 2400),
                    new Point2D(mapSize * 0.55D, mapSize * 0.45D),
                    new Point2D(800.0D, mapSize - 800.0D),
                    random.nextBoolean()
                            ? new Point2D(mapSize - 200.0D,600.0D )
                            : new Point2D( mapSize - 600.0D,200.0D)
            });

            waypointsByLane.put(LaneType.TOP, new Point2D[]{


                    new Point2D(200.0D, mapSize - 400.0D),
                    new Point2D(300.0D, mapSize - 1000.0D),
                    new Point2D(300.0D, mapSize - 1400.0D),
                    new Point2D(200.0D, mapSize - 1800.0D),
                    new Point2D(200.0D, mapSize - 2200.0D),
                    new Point2D(200.0D, mapSize - 2600.0D),
                    new Point2D(200.0D, mapSize - 3000.0D),

                    new Point2D(300.0D, mapSize - 3200.0D),

                    new Point2D(900, 900),


                    new Point2D( 600.0D ,200.0D),
                    new Point2D( 1000.0D ,200.0D),
                    new Point2D( 1400.0D ,200.0D),
                    new Point2D( 1800.0D ,200.0D),
                    new Point2D( 2200.0D ,200.0D),
                    new Point2D( 2600.0D ,200.0D),
                    new Point2D( 3000.0D ,200.0D),
                    new Point2D( 3400.0D ,200.0D),
                    new Point2D( 3800.0D ,200.0D),            });

            waypointsByLane.put(LaneType.BOTTOM, new Point2D[]{
                    new Point2D(100.0D, mapSize - 100.0D),
                    new Point2D(400.0D, mapSize - 100.0D),
                    new Point2D(800.0D, mapSize - 200.0D),
                    new Point2D(mapSize * 0.25D, mapSize - 200.0D),
                    new Point2D(mapSize * 0.5D, mapSize - 200.0D),
                    new Point2D(mapSize * 0.75D, mapSize - 200.0D),
                    new Point2D(mapSize - 200.0D, mapSize - 200.0D),
                    new Point2D(3200,3200),
                    new Point2D(mapSize - 200.0D, mapSize * 0.75D),
                    new Point2D(mapSize - 200.0D, mapSize * 0.5D),
                    new Point2D(mapSize - 200.0D, mapSize * 0.25D),
                    new Point2D(mapSize - 200.0D, 200.0D)
            });

            switch ((int) self.getId()) {
                case 1:Constants.lane = LaneType.TOP;
                    break;
                case 2:Constants.lane = LaneType.TOP;
                    break;
				case 3:Constants.lane = LaneType.MIDDLE;
                    break;
				case 4:Constants.lane = LaneType.BOTTOM;
                    break;
                case 5:Constants.lane = LaneType.BOTTOM;
                    break;
                case 6:Constants.lane = LaneType.TOP;
                    break;
                case 7:Constants.lane = LaneType.TOP;
                    break;
                case 8:Constants.lane = LaneType.MIDDLE;
                    break;
                case 9:Constants.lane = LaneType.BOTTOM;
                    break;
                case 10:Constants.lane = LaneType.BOTTOM;
                    break;
                    
                default:
            }

            Constants.waypoints = waypointsByLane.get(Constants.lane);

            // Наша стратегия исходит из предположения, что заданные нами ключевые точки упорядочены по убыванию
            // дальности до последней ключевой точки. Сейчас проверка этого факта отключена, однако вы можете
            // написать свою проверку, если решите изменить координаты ключевых точек.

            /*Point2D lastWaypoint = waypoints[waypoints.length - 1];

            Preconditions.checkState(ArrayUtils.isSorted(waypoints, (waypointA, waypointB) -> Double.compare(
                    waypointB.getDistanceTo(lastWaypoint), waypointA.getDistanceTo(lastWaypoint)
            )));*/
        }
    }

    /**
     * Сохраняем все входные данные в полях класса для упрощения доступа к ним.
     */
    private void initializeTick(Wizard self, World world, Game game, Move move) {
        Constants.self = self;
        Constants.world = world;
        Constants.game = game;
        Constants.move = move;
		isturn=true;
        isgo=true;
		Constants.temper=0;
        if((MyTiks+1)==world.getTickIndex()){
            MyTiks+=1;

        }else{
            MyTiks=world.getTickIndex();
            System.out.println("start tisk is "+MyTiks);
			alg.update(toPoint2D(self),toPoint2D(1200,1200));


            Constants.waypointIndex=0;
/*

			double nt=0;
			long ntStart = System.nanoTime();
			for(int j=0;j<20;j++){
				for(int i=0;i<20;i++) {
					WaveAlgoritm alg = new WaveAlgoritm();
					alg.initialize();
					//alg.waving(alg.getNode(i, j));
					alg.update(toPoint2D(self),toPoint2D(1200,1200));
					//alg.print();
				}
			}
			nt=(System.nanoTime() - ntStart)* 0.000000001D;

			System.out.println("time for algoritm "+(nt/400));
*/
        }
        if(MyTiks%50==0){
			alg.update(toPoint2D(self));
		}


    }


}