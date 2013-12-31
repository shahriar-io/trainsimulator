package jts.util.obj;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;

public class Patron {
	
	private List<Point> patron;

	public Patron(List<Point> patron){
		this.patron = patron;
	}
	
	public List<Point> getPatron(){
		return this.patron;
	}
	
	public static Patron getCercle(int nbPoints, double rayon){
		List<Point> pts = new ArrayList<Point>();
		
		for(int i=0; i<nbPoints; i++){
			double angle = i/(double)nbPoints*2*Math.PI;
			pts.add(new Point(rayon*Math.cos(-angle), rayon*Math.sin(-angle)));
		}
		
		return new Patron(pts);
	}

}
