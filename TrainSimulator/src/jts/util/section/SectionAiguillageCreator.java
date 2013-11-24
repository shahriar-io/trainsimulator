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
	private String nom;
	private ModeleObjCourbe moc;

	public SectionAiguillageCreator(double ouvertureDeg, double longueurPrincipale, double longueurDeviation){
		this.ouverture = BasicConvert.degToRad(ouvertureDeg);
		this.longueurPrincipale = longueurPrincipale;
		this.longueurDeviation = longueurDeviation;
		this.nom = "StdAig" + (int)ouvertureDeg + "dDtest";
		this.rayon = ECARTEMENT_STD/(2*(1-Math.cos(ouverture)));
		System.out.println(this.rayon);
		System.out.println(longueurDeviation-rayon*Math.sin(ouverture));
		//this.moc = new ModeleObjCourbe(nbVoies, rayon, ouverture, roulis, nom);
	}

	public Section getSection() {
		Section section = new Section();
		section.setNomObjet(nom);

		double yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		PointFrontiere p1 = new PointFrontiere();
		PointFrontiere p2 = new PointFrontiere(0, longueurPrincipale, 0, 0);
		Divergence p3 = new Divergence(0, yCercle, 0, 0, false);
		PointFrontiere p4 = new PointFrontiere(ECARTEMENT_STD/2, longueurDeviation, 0, 0);
		Point centre = new Point(rayon, yCercle);

		section.getPointsExtremites().add(p1);
		section.getPointsExtremites().add(p2);
		section.getPointsExtremites().add(p3);
		section.getPointsExtremites().add(p4);
		section.getElements().add(new Segment(p1, p3, 0));
		section.getElements().add(new Segment(p3, p2, 0));
		section.getElements().add(new Arc(p3, p4, 0, centre, rayon, -Math.PI/2, ouverture));


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
		Section section = getSection();

		try{
			FileWriter fw = new FileWriter(new File(folder + "/sections/" + nom + ".xml"));
			BufferedWriter writer = new BufferedWriter(fw);
			section.save("", writer, nom);
			writer.close();
			fw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		//moc.createAndSave(folder + "/objets");
		System.out.println(nom + " cree");
	}

	public static void main(String[] args) {
		SectionAiguillageCreator sac = new SectionAiguillageCreator(10, 30, 25);
		sac.createAndSave("data");
	}
}
