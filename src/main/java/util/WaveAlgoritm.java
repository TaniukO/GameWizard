package util;

import com.sun.javafx.sg.prism.NodePath;

import java.util.*;

import static java.lang.StrictMath.sqrt;
import static util.StaticUtil.komputeBounding;
import static util.StaticUtil.toPoint2D;

/**
 * Created by Roman on 28.11.2016.
 */
public class WaveAlgoritm {

    private PathNode[][] pathNode;
    private Point2D[] paths;


    public PathNode getNode(Point2D point) {
        int x=(int) Math.round((point.getX()-100)/200);
        int y=(int) Math.round((point.getY()-100)/200);
        System.out.println(" target : y "+y+" x "+x);
        return pathNode[y][x];

    }

    public PathNode getNode(int x, int y){
        return pathNode[(int) Math.round((y-100)/200)][(int) Math.round((x-100)/200)];
    }

    public Point2D[] getPath(){

        return paths;
    }

    public void initialize(){

        pathNode=new PathNode[20][20];

        for(int j=0;j<pathNode.length;j++){
            for(int i=0;i<pathNode[0].length;i++) {
                pathNode[j][i] = new PathNode(i, j);
            }
        }
        for(int j=0;j<pathNode.length;j++){
            for(int i=0;i<pathNode[0].length;i++){
                pathNode[j][i].setNearestNode90Deg(new PathNode[]{((j-1)>=0)? pathNode[j-1][i]:null, ((i-1)>=0)? pathNode[j][i-1]:null, ((j+1)<=19)? pathNode[j+1][i]:null, ((i+1)<=19)? pathNode[j][i+1]:null});
                pathNode[j][i].setNearestNode45Deg(new PathNode[]{((j-1)>=0 && (i-1)>=0)? pathNode[j-1][i-1]:null, ((j-1)>=0 && (i+1)<=19)? pathNode[j-1][i+1]:null, ((j+1)<=19 && (i+1)<=19)? pathNode[j+1][i+1]:null, ((j+1)<=19 && (i-1)>=0)? pathNode[j+1][i-1]:null});

            }
        }

    }

    public void update(Point2D pointFrom,Point2D pointTO){
        update(getNode(pointFrom),getNode(pointTO));
    }
    public void update(Point2D pointFrom){
        update(getNode(pointFrom));
    }

    public void update(PathNode from) {
        if(paths!=null)
            update(from,getNode(paths[paths.length-1]));
        }
    private void update(PathNode from,PathNode to){
        double[] kompBounding;
        for(int j=0;j<20;j++){
            for(int i=0;i<20;i++){
                kompBounding=komputeBounding(-50+i*200,i*200+249,-50+j*200,j*200+250);
                pathNode[j][i].setPermeability(kompBounding[0]+1);
                pathNode[j][i].setPreoccupancy(kompBounding[1]);
                pathNode[j][i].setDistanceFrom(1000000);
            }
        }
        waving(from);
        print();
        searsh(to);
    }

    private void searsh(PathNode to) {
        PathNode[] arrayNearestNode90;
        PathNode[] arrayNearestNode45;
        PathNode node=to;
        //System.out.println("  node "+node.toString());
        List<Point2D> pathL=new LinkedList<>();
        pathL.add(toPoint2D(node.getX()*200+99,node.getY()*200+99));
        while(node.getDistanceFrom()>Double.MIN_VALUE+node.getPermeability()){
            arrayNearestNode45=node.getNearestNode45Deg();
            arrayNearestNode90=node.getNearestNode90Deg();
            for(int i=0;i<arrayNearestNode90.length;i++) {
                if(arrayNearestNode90[i]!=null){
                    if(arrayNearestNode90[i].getDistanceFrom()<node.getDistanceFrom()) {
                        node=arrayNearestNode90[i];

                    }
                }
            }
            for(int i=0;i<arrayNearestNode45.length;i++) {
                if(arrayNearestNode45[i]!=null){
                    if(arrayNearestNode45[i].getDistanceFrom()<node.getDistanceFrom()) {
                        node=arrayNearestNode45[i];

                    }
                }
            }
            //System.out.println("  node "+node.toString());
            pathL.add(0,toPoint2D(node.getX()*200+99,node.getY()*200+99));
            paths=pathL.toArray( new Point2D[pathL.size()]);

        }
    }



     public void waving(PathNode node){
        PathNode[] arrayNearestNode90;
        PathNode[] arrayNearestNode45;
        LinkedHashSet<PathNode> bufer1=new LinkedHashSet<PathNode>();
        LinkedHashSet<PathNode> bufer2;
        node.setDistanceFrom(0);
        bufer1.add(node);

         while(bufer1.size()>0){
            bufer2=new LinkedHashSet<PathNode>();
            Iterator<PathNode> it = bufer1.iterator();
             //System.out.println("  yes   "+node.toString());

            while(it.hasNext()){

                node=it.next();
                arrayNearestNode45=node.getNearestNode45Deg();
                arrayNearestNode90=node.getNearestNode90Deg();
                //System.out.println(" length array node "+(arrayNearestNode90.length+arrayNearestNode45.length));
                for(int i=0;i<arrayNearestNode90.length;i++) {
                    if(arrayNearestNode90[i]!=null){
                        if(arrayNearestNode90[i].getDistanceFrom()>999999.0) {
                            //System.out.println("ok1 "+arrayNearestNode90[i].toString());
                            bufer2.add(arrayNearestNode90[i]);
                        }//else{System.out.println("no1 "+arrayNearestNode90[i].toString());}


                        if (node.getDistanceFrom()> (arrayNearestNode90[i].getDistanceFrom() )+1) {
                            if(arrayNearestNode90[i].getDistanceFrom()<9999999)
                                node.setDistanceFrom(arrayNearestNode90[i].getDistanceFrom() + 1);
                        }
                    }//else{System.out.println("null");}
                }
                for(int i=0;i<arrayNearestNode45.length;i++) {
                    if(arrayNearestNode45[i]!=null){
                        if(arrayNearestNode45[i].getDistanceFrom()>999999.0){
                            //System.out.println("ok2 "+arrayNearestNode45[i].toString());
                            bufer2.add(arrayNearestNode45[i]);
                        }//else{System.out.println("no2 "+arrayNearestNode45[i].toString());}

                        if (node.getDistanceFrom()> (arrayNearestNode45[i].getDistanceFrom() + sqrt(2))){
                            if (arrayNearestNode45[i].getDistanceFrom()<999999)
                                node.setDistanceFrom(arrayNearestNode45[i].getDistanceFrom() + (float) sqrt(2));
                        }
                    }//else{System.out.println("null");}
                }
                //System.out.println("     "+node.toString());
                if(node.getPermeability()!=0) {
                    node.setDistanceFrom(node.getPermeability()*node.getDistanceFrom());
                }
                it.remove();
            }
            bufer1=bufer2;
        }

    }

    public void print(){
        for(int y=0;y<pathNode.length;y++){
            for(int x=0;x<pathNode[0].length;x++){
                //System.out.print(pathNode[y][x].toString()+" dist ");
                System.out.format("%.3f", pathNode[y][x].getDistanceFrom());
                System.out.print(",\t");
            }
            System.out.println("");
        }
    }

}

