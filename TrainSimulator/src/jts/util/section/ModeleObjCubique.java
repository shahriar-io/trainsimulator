package jts.util.section;

import java.io.File;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;

public class ModeleObjCubique extends ModeleObjCreator {
	
	private static double ANGLE_DISCRETISATION = 0.5*Math.PI/360;
	
	private int nbVoies;
	private double ouverture;
	private double roulis;
	private double rayon;
	private double sFin;
	private String[] nom;
	private int nbSousSections;
	private Cubique cubique;
	
	private ModeleObj[] obj;
	
	public ModeleObjCubique(int nbVoies, double rayon, double ouverture, double roulis, double sFin, String[] nom){
		this.nbVoies = nbVoies;
		this.ouverture = ouverture;
		this.roulis = roulis;
		this.rayon = rayon;
		this.sFin = sFin;
		this.nom = nom;
		this.obj = new ModeleObj[2];
	}
	
	public void createAndSave(String folder){
		obj[0] = this.createObjet(true);
		obj[1] = this.createObjet(false);
		obj[0].write(new File(folder + "/" + nom[0] + ".obj"));
		obj[1].write(new File(folder + "/" + nom[1] + ".obj"));
	}

	private ModeleObj createObjet(boolean versionDroite) {
		ModeleObj obj;
		PointFrontiere pf1 = new PointFrontiere();
		PointFrontiere pf2 = new PointFrontiere();
		if(versionDroite){
			pf2.setPhi(roulis);
		} else {
			pf2.setPhi(-roulis);
		}
		
		nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		double maxTexture = (sFin/5)/nbSousSections;
		maxTexture = ((int)((maxTexture+0.5)*2))/2.0;
		if(versionDroite){
			cubique = new Cubique(pf1, pf2, 0, 1/rayon, ouverture);
		} else {
			cubique = new Cubique(pf1, pf2, 0, -1/rayon, -ouverture);
		}
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
		Point p = new Point();
		AngleEuler angle = new AngleEuler();
		p1 = new Point(-X_BAS+xVoie, 0, Y_BAS);
		p1.transformer(new Point(), new AngleEuler(0, 0, 0));
		p2 = new Point(-X_HAUT+xVoie, 0, Y_HAUT);
		p2.transformer(new Point(), new AngleEuler(0, 0, 0));
		p3 = new Point(X_HAUT+xVoie, 0, Y_HAUT);
		p3.transformer(new Point(), new AngleEuler(0, 0, 0));
		p4 = new Point(X_BAS+xVoie, 0, Y_BAS);
		p4.transformer(new Point(), new AngleEuler(0, 0, 0));
		p5 = new Point();
		p6 = new Point();
		p7 = new Point();
		p8 = new Point();
		
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			for(int j=0; j<nbSousSections; j++){
				double ratio = (j+1.0)/nbSousSections;
				cubique.recupererPoint(p, ratio);
				cubique.recupererAngle(angle, ratio);
				
				p5 = new Point(-X_BAS+xVoie, 0, Y_BAS);
				p5.transformer(p, angle);
				p6 = new Point(-X_HAUT+xVoie, 0, Y_HAUT);
				p6.transformer(p, angle);
				p7 = new Point(X_HAUT+xVoie, 0,  Y_HAUT);
				p7.transformer(p, angle);
				p8 = new Point(X_BAS+xVoie, 0, Y_BAS);
				p8.transformer(p, angle);
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
			createRail(obj, groupe, -0.75+xVoie);
			createRail(obj, groupe, +0.75+xVoie);
			obj.addGroupe(groupe);
		}
		return obj;
	}
	
	private void createRail(ModeleObj obj, Groupe groupe, double x0) {
		Point p = new Point();
		AngleEuler angle = new AngleEuler();
		Point p1 = new Point(x0 - 0.0325, 0, Y_HAUT);
		p1.transformer(new Point(), new AngleEuler(0, 0, 0));
		Point p2 = new Point(x0 - 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		p2.transformer(new Point(), new AngleEuler(0, 0, 0));
		Point p3 = new Point(x0 + 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		p3.transformer(new Point(), new AngleEuler(0, 0, 0));
		Point p4 = new Point(x0 + 0.0325, 0, Y_HAUT);
		p4.transformer(new Point(), new AngleEuler(0, 0, 0));
		Point p5 = new Point();
		Point p6 = new Point();
		Point p7 = new Point();
		Point p8 = new Point();
		for(int j=0; j<nbSousSections; j++){
			double ratio = (j+1.0)/(double)nbSousSections;
			//cubique.recupererPosition(p, angle, ratio, true);
			cubique.recupererPoint(p, ratio);
			cubique.recupererAngle(angle, ratio);
			
			p5 = new Point(+x0-0.0325, 0, Y_HAUT);
			//p5.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			//p5.transformer(new Point(rayon, 0), psi);
			p5.transformer(p, angle);
			p6 = new Point(+x0-0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
			//p6.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			//p6.transformer(new Point(rayon, 0), psi);
			p6.transformer(p, angle);
			p7 = new Point(+x0+0.0325, 0,  Y_HAUT + HAUTEUR_RAIL);
			//p7.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			//p7.transformer(new Point(rayon, 0), psi);
			p7.transformer(p, angle);
			p8 = new Point(+x0+0.0325, 0, Y_HAUT);
			//p8.transformer(new Point(-rayon, 0),  new AngleEuler(0, roulis, 0));
			//p8.transformer(new Point(rayon, 0), psi);
			p8.transformer(p, angle);
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
	
	public ModeleObj[] getObj(){
		return this.obj;
	}
}
