package jts.util.section;

import java.io.File;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;

public class ModeleObjAiguillage extends ModeleObjCreator {

	private double longueurPrincipale;
	private double longueurDeviation;
	private double rayon;
	private double ouverture;
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
		PointFrontiere pf1 = new PointFrontiere();
		PointFrontiere pf2 = new PointFrontiere();
		double yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		if(versionDroite){
			arc = new Arc(pf1, pf2, 0, new Point(rayon, yCercle), rayon, -Math.PI/2, ouverture);
		} else {
			arc = new Arc(pf1, pf2, 0, new Point(-rayon, yCercle), rayon, Math.PI/2, -ouverture);
		}
		
		double maxTexture = longueurPrincipale/5;
		ModeleObj obj = new ModeleObj();
		obj.addPointTexture(new Point(0, 0));
		obj.addPointTexture(new Point(0.21, 0));
		obj.addPointTexture(new Point(0.79, 0));
		obj.addPointTexture(new Point(1, 0));
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
		ModeleObjCreator.createParallelepipede(obj, groupe, p5, p6, p7, p8, p9, p10, p11, p12);
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
		ModeleObjCreator.createParallelepipede(obj, groupe, p13, p14, p15, p16, p17, p18, p19, p20);
		obj.addPoint(p17);
		obj.addPoint(p18);
		obj.addPoint(p19);
		obj.addPoint(p20);
		
		/*p1 = new Point(-X_BAS, yCercle, Y_BAS);
		p2 = new Point(-X_HAUT, yCercle, Y_HAUT);
		p3 = new Point(X_HAUT, yCercle, Y_HAUT);
		p4 = new Point(X_BAS, yCercle, Y_BAS);
		
		Point translation = new Point();
		AngleEuler angle = new AngleEuler();
		arc.recupererPoint(translation, 0.5);
		arc.recupererAngle(angle, 0.5);
		p5 = new Point(-X_BAS, 0, Y_BAS);
		p5.transformer(translation, angle);
		p6 = new Point(-X_HAUT, 0, Y_HAUT);
		p6.transformer(translation, angle);
		p7 = new Point(X_HAUT, 0, Y_HAUT);
		p7.transformer(translation, angle);
		p8 = new Point(X_BAS, 0, Y_BAS);
		p8.transformer(translation, angle);
		
		arc.recupererPoint(translation, 1.0);
		arc.recupererAngle(angle, 1.0);
		p9 = new Point(-X_BAS, 0, Y_BAS);
		p9.transformer(translation, angle);
		p10 = new Point(-X_HAUT, 0, Y_HAUT);
		p10.transformer(translation, angle);
		p11 = new Point(X_HAUT, 0, Y_HAUT);
		p11.transformer(translation, angle);
		p12 = new Point(X_BAS, 0, Y_BAS);
		p12.transformer(translation, angle);

		ModeleObjCreator.createParallelepipede(obj, groupe, p1, p2, p3, p4, p5, p6, p7, p8);
		ModeleObjCreator.createParallelepipede(obj, groupe, p5, p6, p7, p8, p9, p10, p11, p12);
		obj.addPoint(p9);
		obj.addPoint(p10);
		obj.addPoint(p11);
		obj.addPoint(p12);*/
		
		obj.addGroupe(groupe);
		
		return obj;
	}

	public ModeleObj[] getObj(){
		return this.obj;
	}
}
