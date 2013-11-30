package jts.util.section;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.BasicConvert;

public class SectionAiguillageCreator {

	private static double ECARTEMENT_STD = 4.0;

	private double ouverture;
	private double longueurPrincipale;
	private double longueurDeviation;
	private double rayon;
	private String[] nom;
	private ModeleObjAiguillage moa;

	public SectionAiguillageCreator(double ouvertureDeg, double longueurPrincipale, double longueurDeviation){
		this.ouverture = BasicConvert.degToRad(ouvertureDeg);
		this.longueurPrincipale = longueurPrincipale;
		this.longueurDeviation = longueurDeviation;
		this.nom = new String[2];
		this.nom[0] = "StdAig" + (int)ouvertureDeg + "dD";
		this.nom[1] = "StdAig" + (int)ouvertureDeg + "dG";
		this.rayon = ECARTEMENT_STD/(2*(1-Math.cos(ouverture)));
		System.out.println(this.rayon);
		System.out.println(longueurDeviation-rayon*Math.sin(ouverture));
		this.moa = new ModeleObjAiguillage(longueurPrincipale, longueurDeviation, rayon, ouverture, nom);
	}

	public Section getSection(boolean versionDroite) {
		Section section = new Section();
		if(versionDroite){
			section.setNomObjet(nom[0]);
		} else {
			section.setNomObjet(nom[1]);
		}

		double yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		PointFrontiere p1 = new PointFrontiere();
		PointFrontiere p2 = new PointFrontiere(0, longueurPrincipale, 0, 0);
		Divergence p3 = new Divergence(0, yCercle, 0, 0, !versionDroite);
		PointFrontiere p4;
		Point centre;
		if(versionDroite){
			p4 = new PointFrontiere(ECARTEMENT_STD/2, longueurDeviation, 0, 0);
			centre = new Point(rayon, yCercle);
		} else {
			p4 = new PointFrontiere(-ECARTEMENT_STD/2, longueurDeviation, 0, 0);
			centre = new Point(-rayon, yCercle);
		}

		section.getPointsExtremites().add(p1);
		section.getPointsExtremites().add(p2);
		section.getPointsExtremites().add(p3);
		section.getPointsExtremites().add(p4);
		section.getElements().add(new Segment(p1, p3, 0));
		section.getElements().add(new Segment(p3, p2, 0));
		if(versionDroite){
			section.getElements().add(new Arc(p3, p4, 0, centre, rayon, -Math.PI/2, ouverture));
		} else {
			section.getElements().add(new Arc(p3, p4, 0, centre, rayon, Math.PI/2, -ouverture));
		}


		int nbSousSections = (int)(rayon*ouverture)/50;
		double ouvertureElem = ouverture/(double)nbSousSections;
		double xFrGauche = -1.5;

		List<Point> frontiere = new ArrayList<Point>();

		frontiere.add(new Point(-xFrGauche, 0));
		for(int i=1; i<=nbSousSections; i++){
			Point pf = new Point(-xFrGauche - rayon, 0);
			pf.transformer(new Point(rayon, 0), (double)(i*ouvertureElem));
			frontiere.add(pf);
		}
		for(int i=nbSousSections; i>=1; i--){
			Point pf = new Point(xFrGauche - rayon, 0);
			pf.transformer(new Point(rayon, 0), (double)(i*ouvertureElem));
			frontiere.add(pf);
		}
		frontiere.add(new Point(xFrGauche, 0));
		section.creerFrontiere(frontiere);
		return section;
	}

	public void createAndSave(String folder){
		Section section = getSection(true);
		try{
			FileWriter fw = new FileWriter(new File(folder + "/sections/" + nom[0] + ".xml"));
			BufferedWriter writer = new BufferedWriter(fw);
			section.save("", writer, nom[0]);
			writer.close();
			fw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		section = getSection(false);
		try{
			FileWriter fw = new FileWriter(new File(folder + "/sections/" + nom[1] + ".xml"));
			BufferedWriter writer = new BufferedWriter(fw);
			section.save("", writer, nom[1]);
			writer.close();
			fw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		moa.createAndSave(folder + "/objets");
		System.out.println(nom[0] + " cree");
		System.out.println(nom[1] + " cree");
	}

	public static void main(String[] args) {
		SectionAiguillageCreator sac = new SectionAiguillageCreator(10, 30, 25);
		sac.createAndSave("data");
	}
}
