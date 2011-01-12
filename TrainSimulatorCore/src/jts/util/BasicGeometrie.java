package jts.util;

import jts.moteur.geometrie.Point;

public class BasicGeometrie {
	
	public static double getAngle(Point p1, Point p2){
		double cap;
		if (p1.getX() != p2.getX()){
			//Cas général
			cap = Math.atan((p2.getY()-p1.getY())/(p2.getX()-p1.getX()));
			if (p1.getX()>p2.getX()){
				//On fait un demi-tour sur le cercle
				cap = cap + Math.PI;
			}
		} else {
			//Cas ou la tangente vaut l'infini
			if (p1.getY()>p2.getY()){
				cap = -Math.PI/2;
			} else {
				cap = Math.PI/2;
			}
		}
		return cap;
	}
}
