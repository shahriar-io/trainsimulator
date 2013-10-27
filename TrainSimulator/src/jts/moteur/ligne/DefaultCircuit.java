package jts.moteur.ligne;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointPassage;

public class DefaultCircuit extends Circuit {

	public DefaultCircuit(){
		super();
		
		PointPassage pp1 = new PointPassage(-50, 2, 0, 0);
		PointPassage pp2 = new PointPassage(-25, 2, 0, 0);
		PointPassage pp3 = new PointPassage(-10, 2, 0, 0.1);
		PointPassage pp4 = new PointPassage(10, 2, 0, 0.1);
		PointPassage pp5 = new PointPassage(25, 2, 0, 0);
		PointPassage pp6 = new PointPassage(-50, -2, 0, 0);
		PointPassage pp7 = new PointPassage(25, -2, 0, 0);
		
		PointPassage pp8 = new PointPassage(50, 2, 0, 0);
		PointPassage pp9 = new PointPassage(50, -2, 0, 0);
		
		Point centre = new Point(25, -50);
		
		this.addPointPassage(pp1);
		this.addPointPassage(pp2);
		this.addPointPassage(pp3);
		this.addPointPassage(pp4);
		this.addPointPassage(pp5);
		this.addPointPassage(pp6);
		this.addPointPassage(pp7);
		this.addPointPassage(pp8);
		this.addPointPassage(pp9);
		
		this.addSegment(0, 1);
		this.addSegment(1, 2);
		this.addSegment(2, 3);
		this.addSegment(3, 4);
		this.addSegment(5, 6);
		
		this.addArc(4, 7, 0.0, centre, 52.0, 0.0, 0.5);
		this.addArc(6, 8, 0.0, centre, 48.0, 0.0, 0.5);
	}
}
