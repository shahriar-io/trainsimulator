package jts.util.section;

import java.io.File;

import jts.moteur.geometrie.Point;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;

public class ModeleObjLigneDroite extends ModeleObjCreator {
	
	private double longueur;
	private int nbVoies;
	private String nom;
	
	private ModeleObj obj;
	
	public ModeleObjLigneDroite(int nbVoies, double longueur, String nom){
		this.nbVoies = nbVoies;
		this.longueur = longueur;
		this.nom = nom;
	}
	
	public void createAndSave(String folder){
		this.createAndSave(this.nom, folder);
	}
	
	public void createAndSave(String nom, String folder){
		this.createObjet();
		obj.write(new File(folder + "/" + nom + ".obj"));
	}

	private void createObjet() {
		obj = new ModeleObj();
		obj.addPointTexture(new Point(0, 0));
		obj.addPointTexture(new Point(0.21, 0));
		obj.addPointTexture(new Point(0.79, 0));
		obj.addPointTexture(new Point(1, 0));
		obj.addPointTexture(new Point(0, longueur/5));
		obj.addPointTexture(new Point(0.21, longueur/5));
		obj.addPointTexture(new Point(0.79, longueur/5));
		obj.addPointTexture(new Point(1, longueur/5));
		Groupe groupe;
		Point p1, p2, p3, p4, p5, p6, p7, p8;
		
		double xVoie = -2*(nbVoies-1);
		groupe = new Groupe("ballast", "Ballast");
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			
			p1 = new Point(-X_BAS+xVoie, -longueur/2, Y_BAS);
			p2 = new Point(-X_HAUT+xVoie, -longueur/2, Y_HAUT);
			p3 = new Point(X_HAUT+xVoie, -longueur/2, Y_HAUT);
			p4 = new Point(X_BAS+xVoie, -longueur/2, Y_BAS);
			p5 = new Point(-X_BAS+xVoie, longueur/2, Y_BAS);
			p6 = new Point(-X_HAUT+xVoie, longueur/2, Y_HAUT);
			p7 = new Point(X_HAUT+xVoie, longueur/2, Y_HAUT);
			p8 = new Point(X_BAS+xVoie, longueur/2, Y_BAS);
			
			ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
			obj.addPoint(p5);
			obj.addPoint(p6);
			obj.addPoint(p7);
			obj.addPoint(p8);
			obj.addGroupe(groupe);
		}
		
		groupe = new Groupe("rails", "Rail");
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			createRail(groupe, X_RAIL+xVoie);
			createRail(groupe, -X_RAIL+xVoie);
			obj.addGroupe(groupe);
		}
	}

	private void createRail(Groupe groupe, double x0) {
		Point p1 = new Point(x0 - 0.0325, -longueur/2, Y_HAUT);
		Point p2 = new Point(x0 - 0.0325, -longueur/2, Y_HAUT + HAUTEUR_RAIL);
		Point p3 = new Point(x0 + 0.0325, -longueur/2, Y_HAUT + HAUTEUR_RAIL);
		Point p4 = new Point(x0 + 0.0325, -longueur/2, Y_HAUT);
		Point p5 = new Point(x0 - 0.0325, longueur/2, Y_HAUT);
		Point p6 = new Point(x0 - 0.0325, longueur/2, Y_HAUT + HAUTEUR_RAIL);
		Point p7 = new Point(x0 + 0.0325, longueur/2, Y_HAUT + HAUTEUR_RAIL);
		Point p8 = new Point(x0 + 0.0325, longueur/2, Y_HAUT);
		ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
		obj.addPoint(p5);
		obj.addPoint(p6);
		obj.addPoint(p7);
		obj.addPoint(p8);
	}
	
	public ModeleObj getObj(){
		return this.obj;
	}
}
