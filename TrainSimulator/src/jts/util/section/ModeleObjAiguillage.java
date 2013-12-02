package jts.util.section;

import java.io.File;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;

public class ModeleObjAiguillage extends ModeleObjCreator {
	
	private static double ANGLE_DISCRETISATION = Math.PI/360;

	private double longueurPrincipale;
	private double longueurDeviation;
	private double rayon;
	private double yCercle;
	private double ouverture;
	private int nbSousSections;
	private String[] nom;
	private Arc arc;

	private ModeleObj[] obj;

	public ModeleObjAiguillage(double longueurPrincipale, double longueurDeviation, double rayon, double ouverture, String[] nom){
		this.longueurPrincipale = longueurPrincipale;
		this.longueurDeviation = longueurDeviation;
		this.rayon = rayon;
		this.ouverture = ouverture;
		this.nom = nom;
		this.obj = new ModeleObj[2];
	}

	public void createAndSave(String folder){
		obj[0] = this.createObjet(true);
		obj[1] = this.createObjet(false);
		obj[0].write(new File(folder + "/" + nom[0] + ".obj"));
		obj[1].write(new File(folder + "/" + nom[1] + ".obj"));
	}

	/*public void createAndSave(String nom, String folder){
		this.createObjet();
		obj.write(new File(folder + "/" + nom + ".obj"));
	}*/

	private ModeleObj createObjet(boolean versionDroite) {
		nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		PointFrontiere pf1 = new PointFrontiere();
		PointFrontiere pf2 = new PointFrontiere();
		yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		if(versionDroite){
			arc = new Arc(pf1, pf2, 0, new Point(rayon, yCercle), rayon, -Math.PI/2, ouverture);
		} else {
			arc = new Arc(pf1, pf2, 0, new Point(-rayon, yCercle), rayon, Math.PI/2, -ouverture);
		}
		if(!versionDroite){
			rayon = -rayon;
		}
		
		ModeleObj obj = new ModeleObj();
		obj.addPointTexture(new Point(0, 0));
		obj.addPointTexture(new Point(0.21, 0));
		obj.addPointTexture(new Point(0.79, 0));
		obj.addPointTexture(new Point(1, 0));
		
		double maxTexture = yCercle/5;
		maxTexture = ((int)((maxTexture+0.5)*10))/10.0;
		obj.addPointTexture(new Point(0, maxTexture));
		obj.addPointTexture(new Point(0.21, maxTexture));
		obj.addPointTexture(new Point(0.79, maxTexture));
		obj.addPointTexture(new Point(1, maxTexture));
		
		maxTexture = (longueurDeviation-yCercle)/5;
		maxTexture = ((int)((maxTexture+0.5)*10))/10.0;
		obj.addPointTexture(new Point(0, maxTexture));
		obj.addPointTexture(new Point(0.25, maxTexture));
		obj.addPointTexture(new Point(0.75, maxTexture));
		obj.addPointTexture(new Point(1, maxTexture));
		
		maxTexture = (longueurPrincipale-longueurDeviation)/5;
		maxTexture = ((int)((maxTexture+0.5)*10))/10.0;
		obj.addPointTexture(new Point(0, maxTexture));
		obj.addPointTexture(new Point(0.21, maxTexture));
		obj.addPointTexture(new Point(0.79, maxTexture));
		obj.addPointTexture(new Point(1, maxTexture));
		
		Groupe groupe;
		Point p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20;
		groupe = new Groupe("ballast", "Ballast");
		
		p1 = new Point(-X_BAS, 0, Y_BAS);
		p2 = new Point(-X_HAUT, 0, Y_HAUT);
		p3 = new Point(X_HAUT, 0, Y_HAUT);
		p4 = new Point(X_BAS, 0, Y_BAS);
		p5 = new Point(-X_BAS, yCercle, Y_BAS);
		p6 = new Point(-X_HAUT, yCercle, Y_HAUT);
		p7 = new Point(X_HAUT, yCercle, Y_HAUT);
		p8 = new Point(X_BAS, yCercle, Y_BAS);
		ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
		if(versionDroite){
			p9 = new Point(-X_BAS, longueurDeviation, Y_BAS);
			p10 = new Point(-X_HAUT, longueurDeviation, Y_HAUT);
			p11 = new Point(X_HAUT+1, longueurDeviation, Y_HAUT);
			p12 = new Point(X_BAS+1, longueurDeviation, Y_BAS);
		} else {
			p9 = new Point(-X_BAS-1, longueurDeviation, Y_BAS);
			p10 = new Point(-X_HAUT-1, longueurDeviation, Y_HAUT);
			p11 = new Point(X_HAUT, longueurDeviation, Y_HAUT);
			p12 = new Point(X_BAS, longueurDeviation, Y_BAS);
		}
		ModeleObjCreator.createParallelepipede(obj, groupe, p5, p6, p7, p8, p9, p10, p11, p12, 4);
		obj.addPoint(p9);
		obj.addPoint(p10);
		obj.addPoint(p11);
		obj.addPoint(p12);
		p13 = new Point(-X_BAS, longueurDeviation, Y_BAS);
		p14 = new Point(-X_HAUT, longueurDeviation, Y_HAUT);
		p15 = new Point(X_HAUT, longueurDeviation, Y_HAUT);
		p16 = new Point(X_BAS, longueurDeviation, Y_BAS);
		p17 = new Point(-X_BAS, longueurPrincipale, Y_BAS);
		p18 = new Point(-X_HAUT, longueurPrincipale, Y_HAUT);
		p19 = new Point(X_HAUT, longueurPrincipale, Y_HAUT);
		p20 = new Point(X_BAS, longueurPrincipale, Y_BAS);
		ModeleObjCreator.createParallelepipede(obj, groupe, p13, p14, p15, p16, p17, p18, p19, p20, 8);
		obj.addPoint(p17);
		obj.addPoint(p18);
		obj.addPoint(p19);
		obj.addPoint(p20);
		
		obj.addGroupe(groupe);
		
		groupe = new Groupe("rails", "Rail");
		this.createRailDroit(obj, groupe, X_RAIL);
		this.createRailDroit(obj, groupe, -X_RAIL);
		this.createRailCourbe(obj, groupe, X_RAIL, versionDroite);
		this.createRailCourbe(obj, groupe, -X_RAIL, versionDroite);
		obj.addGroupe(groupe);
		
		return obj;
	}
	
	private void createRailDroit(ModeleObj obj, Groupe groupe, double x0) {
		Point p1 = new Point(x0 - 0.0325, 0, Y_HAUT);
		Point p2 = new Point(x0 - 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		Point p3 = new Point(x0 + 0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
		Point p4 = new Point(x0 + 0.0325, 0, Y_HAUT);
		Point p5 = new Point(x0 - 0.0325, longueurPrincipale, Y_HAUT);
		Point p6 = new Point(x0 - 0.0325, longueurPrincipale, Y_HAUT + HAUTEUR_RAIL);
		Point p7 = new Point(x0 + 0.0325, longueurPrincipale, Y_HAUT + HAUTEUR_RAIL);
		Point p8 = new Point(x0 + 0.0325, longueurPrincipale, Y_HAUT);
		ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
		obj.addPoint(p5);
		obj.addPoint(p6);
		obj.addPoint(p7);
		obj.addPoint(p8);
	}
	
	private void createRailCourbe(ModeleObj obj, Groupe groupe, double x0, boolean versionDroite) {
		Point p1 = new Point(x0 - 0.0325, yCercle, Y_HAUT);
		Point p2 = new Point(x0 - 0.0325, yCercle, Y_HAUT + HAUTEUR_RAIL);
		Point p3 = new Point(x0 + 0.0325, yCercle, Y_HAUT + HAUTEUR_RAIL);
		Point p4 = new Point(x0 + 0.0325, yCercle, Y_HAUT);
		Point p5 = new Point();
		Point p6 = new Point();
		Point p7 = new Point();
		Point p8 = new Point();
		for(int j=0; j<nbSousSections; j++){
			double psi;
			if(versionDroite){
				psi = (j+1)*ouverture/nbSousSections;
			} else {
				psi = -(j+1)*ouverture/nbSousSections;
			}
			
			p5 = new Point(+x0-0.0325, 0, Y_HAUT);
			p5.transformer(new Point(-rayon, 0));
			p5.transformer(new Point(rayon, yCercle), psi);
			p6 = new Point(+x0-0.0325, 0, Y_HAUT + HAUTEUR_RAIL);
			p6.transformer(new Point(-rayon, 0));
			p6.transformer(new Point(rayon, yCercle), psi);
			p7 = new Point(+x0+0.0325, 0,  Y_HAUT + HAUTEUR_RAIL);
			p7.transformer(new Point(-rayon, 0));
			p7.transformer(new Point(rayon, yCercle), psi);
			p8 = new Point(+x0+0.0325, 0, Y_HAUT);
			p8.transformer(new Point(-rayon, 0));
			p8.transformer(new Point(rayon, yCercle), psi);
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
