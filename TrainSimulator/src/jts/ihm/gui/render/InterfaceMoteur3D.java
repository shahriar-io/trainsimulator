package jts.ihm.gui.render;

import java.awt.Canvas;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.Parcelle;


public interface InterfaceMoteur3D {
	
	public Canvas getCanvas();

	public void deplacerCamera(float x, float y, float z, float theta, float phi);
	
	public void dessinerLigne(List<Point> ligne);
	
	public void dessinerSurface(List<Point> frontiere);
	
	public void ajouterParcelle(Parcelle parcelle);
	
	public void supprimerParcelle(Parcelle parcelle);
	
	public void setHeure(float heure);
}
