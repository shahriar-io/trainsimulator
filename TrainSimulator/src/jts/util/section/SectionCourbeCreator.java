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
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.BasicConvert;

public class SectionCourbeCreator {
	
	private static double ECARTEMENT_STD = 4.0;
	
	private int nbVoies;
	private double rayon;
	private double ouverture;
	private double roulis;
	private String nom;
	private ModeleObjCourbe moc;

	public SectionCourbeCreator(int nbVoies, double rayon, double ouvertureDeg){
		this(nbVoies, rayon, ouvertureDeg, 0);
	}
	
	public SectionCourbeCreator(int nbVoies, double rayon, double ouvertureDeg, double roulisDeg){
		this.nbVoies = nbVoies;
		this.rayon = rayon;
		this.ouverture = BasicConvert.degToRad(ouvertureDeg);
		this.roulis = BasicConvert.degToRad(roulisDeg);
		if(roulisDeg!=0){
			this.nom = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "d" + (int)roulisDeg + "dr";
		} else {
			this.nom = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "d";
		}
		this.moc = new ModeleObjCourbe(nbVoies, rayon, ouverture, roulis, nom);
	}
	
	public Section getSection() {
		Section section = new Section();
		section.setNomObjet(nom);
		
		PointFrontiere p1;
		PointFrontiere p2;
		
		double debutGauche = -ECARTEMENT_STD/2*(nbVoies-1);
		Point centre = new Point(rayon, 0);
		for(int i=0; i<nbVoies; i++){
			double xCourant = debutGauche + i*ECARTEMENT_STD;
			
			//On crée deux points bidons
			p1 = new PointFrontiere(0, 0, 0, roulis);
			p2 = new PointFrontiere(0, 0, 0, roulis);
			
			//On crée l'arc et on récupère ses origine/fin vrais
			Arc arc = new Arc(p1, p2, 0, centre, rayon-xCourant, -Math.PI/2, ouverture);
			arc.recupererPoint(p1, 0);
			arc.recupererPoint(p2, 1);
			arc.calculerLongueur();
			
			//p1 = new PointFrontiere(xCourant, 0, 0, 0);
			//p2 = new PointFrontiere(-rayon + xCourant, 0, 0, 0);
			//p2.transformer(new Point(rayon - xCourant, 0), ouverture);
			section.getPointsExtremites().add(p1);
			section.getPointsExtremites().add(p2);
			section.getElements().add(arc);
		}
		
		int nbSousSections = (int)(rayon*ouverture)/50;
		double ouvertureElem = ouverture/(double)nbSousSections;
		double xFrGauche = -1.5 + debutGauche;
		
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
		moc.createAndSave(folder + "/objets");
		System.out.println(nom + " cree");
	}

}
