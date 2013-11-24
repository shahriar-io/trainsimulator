package jts.util.obj;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;

public class Surface {
	
	private List<Point> points;
	private List<Point> pointsTexture;
	
	public Surface(){
		this.points = new ArrayList<Point>();
		this.pointsTexture = new ArrayList<Point>();
	}
	
	public Surface(Point p1, Point p2, Point p3){
		this();
		this.points.add(p1);
		this.points.add(p2);
		this.points.add(p3);
	}
	
	public Surface(Point p1, Point p2, Point p3, Point p4){
		this(p1, p2, p3);
		this.points.add(p4);
	}
	
	public Surface(Point p1, Point p2, Point p3, Point pt1, Point pt2, Point pt3){
		this(p1, p2, p3);
		this.pointsTexture.add(pt1);
		this.pointsTexture.add(pt2);
		this.pointsTexture.add(pt3);
	}
	
	public Surface(Point p1, Point p2, Point p3, Point p4, Point pt1, Point pt2, Point pt3, Point pt4){
		this(p1, p2, p3, pt1, pt2, pt3);
		this.points.add(p4);
		this.pointsTexture.add(pt4);
	}

	public void addPoint(Point point){
		points.add(point);
	}
	
	public List<Point> getPoints(){
		return this.points;
	}
	
	public void addPointTexture(Point point){
		pointsTexture.add(point);
	}
	
	public List<Point> getPointsTexture(){
		return this.pointsTexture;
	}
}
