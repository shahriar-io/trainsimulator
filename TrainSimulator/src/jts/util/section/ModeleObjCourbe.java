package jts.util.section;

import java.io.File;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;

public class ModeleObjCourbe extends ModeleObjCreator {
	
	private static double ANGLE_DISCRETISATION = 2.0*Math.PI/360;
	
	private int nbVoies;
	private double ouverture;
	private double roulis;
	private double rayon;
	private String nom;
	private int nbSousSections;
	
	private ModeleObj obj;
	
	public ModeleObjCourbe(int nbVoies, double rayon, double ouverture, double roulis, String nom){
		this.nbVoies = nbVoies;
		this.ouverture = ouverture;
		this.roulis = roulis;
		this.rayon = rayon;
		this.nom = nom;
	}
	
	public void createAndSave(String folder){
		this.createObjet();
		obj.write(new File(folder + "/" + nom + ".obj"));
	}

	private void createObjet() {
		nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		double maxTexture = (rayon*ouverture/5)/nbSousSections;
		obj = new ModeleObj();
		obj.addPointTexture(new Point(0, 0));
		obj.addPointTexture(new Point(0.21, 0));
		obj.addPointTexture(new Point(0.79, 0));
		obj.addPointTexture(new Point(1, 0));
		obj.addPointTexture(new Point(0, maxTexture));
		obj.addPointTexture(new Point(0.21, maxTexture));
		obj.addPointTexture(new Point(0.79, maxTexture));
		obj.addPointTexture(new Point(1, maxTexture));
		Groupe groupe;
		Point p1, p2, p3, p4, p5, p6, p7, p8;
		
		double xVoie = -2*(nbVoies-1);
		groupe = new Groupe("ballast", "Ballast");
		p1 = new Point(-X_BAS+xVoie, 0, Y_BAS);
		p1.transformer(new Point(), new AngleEuler(0, roulis, 0));
		p2 = new Point(-X_HAUT+xVoie, 0, Y_HAUT);
		p2.transformer(new Point(), new AngleEuler(0, roulis, 0));
		p3 = new Point(X_HAUT+xVoie, 0, Y_HAUT);
		p3.transformer(new Point(), new AngleEuler(0, roulis, 0));
		p4 = new Point(X_BAS+xVoie, 0, Y_BAS);
		p4.transformer(new Point(), new AngleEuler(0, roulis, 0));
		p5 = new Point();
		p6 = new Point();
		p7 = new Point();
		p8 = new Point();
		
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			for(int j=0; j<nbSousSections; j++){
				double psi = (j+1)*ouverture/nbSousSections;
				
				p5 = new Point(-X_BAS+xVoie, 0, Y_BAS);
				p5.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
				p5.transformer(new Point(rayon, 0), psi);
				p6 = new Point(-X_HAUT+xVoie, 0, Y_HAUT);
				p6.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
				p6.transformer(new Point(rayon, 0), psi);
				p7 = new Point(X_HAUT+xVoie, 0,  Y_HAUT);
				p7.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
				p7.transformer(new Point(rayon, 0), psi);
				p8 = new Point(X_BAS+xVoie, 0, Y_BAS);
				p8.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
				p8.transformer(new Point(rayon, 0), psi);
				ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
				
				p1 = p5;
				p2 = p6;
				p3 = p7;
				p4 = p8;
			}
			obj.addPoint(p5);
			obj.addPoint(p6);
			obj.addPoint(p7);
			obj.addPoint(p8);
			obj.addGroupe(groupe);
		}
		
		groupe = new Groupe("rails", "Rail");
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			createRail(groupe, -X_RAIL+xVoie);
			createRail(groupe, X_RAIL+xVoie);
			obj.addGroupe(groupe);
		}
	}
	
	private void createRail(Groupe groupe, double x0) {
		Point p1 = new Point(x0 - 0.0325, 0, Y_HAUT);
		p1.transformer(new Point(), new AngleEuler(0, roulis, 0));
		Point p2 = new Point(x0 - 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		p2.transformer(new Point(), new AngleEuler(0, roulis, 0));
		Point p3 = new Point(x0 + 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		p3.transformer(new Point(), new AngleEuler(0, roulis, 0));
		Point p4 = new Point(x0 + 0.0325, 0, Y_HAUT);
		p4.transformer(new Point(), new AngleEuler(0, roulis, 0));
		Point p5 = new Point();
		Point p6 = new Point();
		Point p7 = new Point();
		Point p8 = new Point();
		for(int j=0; j<nbSousSections; j++){
			double psi = (j+1)*ouverture/nbSousSections;
			
			p5 = new Point(+x0-0.0325, 0, Y_HAUT);
			p5.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			p5.transformer(new Point(rayon, 0), psi);
			p6 = new Point(+x0-0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
			p6.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			p6.transformer(new Point(rayon, 0), psi);
			p7 = new Point(+x0+0.0325, 0,  Y_HAUT + HAUTEUR_RAIL);
			p7.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			p7.transformer(new Point(rayon, 0), psi);
			p8 = new Point(+x0+0.0325, 0, Y_HAUT);
			p8.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			p8.transformer(new Point(rayon, 0), psi);
			ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
			
			p1 = p5;
			p2 = p6;
			p3 = p7;
			p4 = p8;
		}
		obj.addPoint(p5);
		obj.addPoint(p6);
		obj.addPoint(p7);
		obj.addPoint(p8);
	}
	
	public ModeleObj getObj(){
		return this.obj;
	}
}
