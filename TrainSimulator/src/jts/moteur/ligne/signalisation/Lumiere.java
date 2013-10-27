package jts.moteur.ligne.signalisation;

import java.awt.Color;

import jts.moteur.geometrie.Point;

public class Lumiere {
	
	protected Color couleur;
	protected double rayon3D;
	protected Point point3D;
	protected double rayon2D;
	protected Point point2D;
	
	protected Allumage allumage;

	public Lumiere(Color couleur, double rayon3D, Point point3D, double rayon2D, Point point2D){
		this.couleur = couleur;
		this.rayon3D = rayon3D;
		this.point3D = point3D;
		this.rayon2D = rayon2D;
		this.point2D = point2D;
		this.allumage = Allumage.ETEINT;
	}

	public Color getCouleur() { return couleur; }
	
	public void setCouleur(Color couleur) { this.couleur = couleur; }

	public double getRayon3D() { return rayon3D; }

	public Point getPoint3D() { return point3D; }

	public double getRayon2D() { return rayon2D; }

	public Point getPoint2D() { return point2D; }

	public Allumage getAllumage() { return allumage; }
	
	public void setAllumage(Allumage allumage) { this.allumage = allumage; }
}
