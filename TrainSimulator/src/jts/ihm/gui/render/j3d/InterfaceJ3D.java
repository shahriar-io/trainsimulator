package jts.ihm.gui.render.j3d;

import java.util.List;

import jts.moteur.geometrie.Point;


public interface InterfaceJ3D {

	public void deplacerCamera(float x, float y, float z, float theta, float phi);
	
	public void dessinerLigne(List<Point> ligne);
	
	public void dessinerSurface(List<Point> frontiere);
	
	public void chargerObjet(float x, float y, float z, String nomObjet);
	
	public void creerVoiture1(float x, float y, float z);
	
	public void deplacerVoiture1(float x, float y, float z, float theta, float phi);
	
	public void creerVoiture2(float x, float y, float z);
	
	public void deplacerVoiture2(float x, float y, float z, float theta, float phi);
	
	public void setHeure(float heure);
}
