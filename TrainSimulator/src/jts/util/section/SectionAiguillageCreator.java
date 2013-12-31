package jts.util.section;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointPassage;
import jts.util.BasicConvert;

public class SectionAiguillageCreator {

	private static double ECARTEMENT_STD = 4.0;
	private static DecimalFormat DF = new DecimalFormat("0.##");

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
		this.nom = new String[4];
		String ouvertureDegStr = DF.format(ouvertureDeg).replace(',', 'p');
		this.nom[0] = "StdAig" + ouvertureDegStr + "dD";
		this.nom[1] = "StdAig" + ouvertureDegStr + "dG";
		this.nom[2] = "StdAigFin" + ouvertureDegStr + "dD";
		this.nom[3] = "StdAigFin" + ouvertureDegStr + "dG";
		this.rayon = ECARTEMENT_STD/(2*(1-Math.cos(ouverture)));
		System.out.println(this.rayon);
		System.out.println(longueurDeviation-rayon*Math.sin(ouverture));
		this.moa = new ModeleObjAiguillage(longueurPrincipale, longueurDeviation, rayon, ouverture, nom);
	}

	public Section getSection(boolean versionDroite, boolean versionFin) {
		Section section = new Section();
		if(versionFin){
			if(versionDroite){
				section.setNomObjet(nom[2]);
			} else {
				section.setNomObjet(nom[3]);
			}
		} else {
			if(versionDroite){
				section.setNomObjet(nom[0]);
			} else {
				section.setNomObjet(nom[1]);
			}
		}

		double yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		PointFrontiere p1 = new PointFrontiere();
		PointFrontiere p2 = new PointFrontiere(0, longueurPrincipale, 0, 0);
		PointPassage p3;
		if(versionFin){
			p3 = new PointPassage(0, yCercle, 0, 0);
		} else {
			p3 = new Divergence(0, yCercle, 0, 0, !versionDroite);
		}
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
		if(!versionFin){
			section.getPointsExtremites().add(p2);
		}
		section.getPointsExtremites().add(p3);
		section.getPointsExtremites().add(p4);
		section.getElements().add(new Segment(p1, p3, 0));
		if(!versionFin){
			section.getElements().add(new Segment(p3, p2, 0));
		}
		
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
		Section[] sections = new Section[4];
		sections[0] = getSection(true, false);
		this.createAndSave(folder, sections[0], 0);
		
		sections[1] = getSection(false, false);
		this.createAndSave(folder, sections[1], 1);
		
		sections[2] = getSection(true, true);
		this.createAndSave(folder, sections[2], 2);
		
		sections[3] = getSection(false, true);
		this.createAndSave(folder, sections[3], 3);
		
		moa.createAndSave(folder + "/objets", sections);
		System.out.println(nom[0] + " cree");
		System.out.println(nom[1] + " cree");
	}
	
	private void createAndSave(String folder, Section section, int n){
		try{
			FileWriter fw = new FileWriter(new File(folder + "/sections/" + nom[n] + ".xml"));
			BufferedWriter writer = new BufferedWriter(fw);
			section.save("", writer, nom[n]);
			writer.close();
			fw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SectionAiguillageCreator sac = new SectionAiguillageCreator(10, 30, 25);
		sac.createAndSave("data");
	}
}
