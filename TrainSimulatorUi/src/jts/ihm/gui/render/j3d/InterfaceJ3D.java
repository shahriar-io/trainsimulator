package jts.ihm.gui.render.j3d;

import java.util.List;

import jts.moteur.geometrie.Point;


public interface InterfaceJ3D {

	public void deplacerCamera(float x, float y, float z, float theta, float phi);
	
	public void dessinerLigne(List<Point> ligne);
	
	public void dessinerSurface(List<Point> frontiere);
	
	public void chargerObjet(float x, float y, float z, String nomObjet);
	
	public void setHeure(float heure);
}
