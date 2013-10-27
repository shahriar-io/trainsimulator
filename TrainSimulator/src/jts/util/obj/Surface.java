package jts.util.obj;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;

public class Surface {
	
	private List<Point> points;
	
	public Surface(){
		this.points = new ArrayList<Point>();
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

	public void addPoint(Point point){
		points.add(point);
	}
	
	public List<Point> getPoints(){
		return this.points;
	}
}
