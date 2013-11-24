package jts.ihm.gui.render;

import java.awt.Canvas;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.ObjetScene;


public interface InterfaceMoteur3D {
	
	public Canvas getCanvas();

	public void deplacerCamera(float x, float y, float z, float theta, float phi);
	
	public void dessinerLigne(List<Point> ligne);
	
	public void dessinerSurface(List<Point> frontiere);
	
	public void chargerObjet(ObjetScene objet);
	
	public void setHeure(float heure);
}
