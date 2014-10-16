package jts.util.section;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.Controleur;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.PointFrontiere;

public class SectionLigneDroiteCreator {
	
	private static double ECARTEMENT_STD = 4.0;
	
	private int nbVoies;
	private double longueur;
	private String nom;
	private ModeleObjLigneDroite mold;

	public SectionLigneDroiteCreator(int nbVoies, double longueur){
		this.nbVoies = nbVoies;
		this.longueur = longueur;
		this.nom = "Std" + nbVoies + "V" + (int)longueur + "m";
		this.mold = new ModeleObjLigneDroite(nbVoies, longueur, nom);
	}
	
	public Section getSection() {
		Section section = new Section();
		section.setNomObjet(nom);
		
		PointFrontiere p1;
		PointFrontiere p2;
		
		double debutGauche = -ECARTEMENT_STD/2*(nbVoies-1);
		for(int i=0; i<nbVoies; i++){
			double xCourant = debutGauche + i*ECARTEMENT_STD;
			
			p1 = new PointFrontiere(xCourant, -longueur/2, 0, 0);
			p2 = new PointFrontiere(xCourant, longueur/2, 0, 0);
			
			Segment segment = new Segment(p1, p2, 0);
			segment.calculerLongueur();
			p1.setElement(segment);
			p2.setElement(segment);
			
			section.getPointsExtremites().add(p1);
			section.getPointsExtremites().add(p2);
			section.getElements().add(segment);
		}
		
		double xFrGauche = -1.5 + debutGauche;
		
		List<Point> frontiere = new ArrayList<Point>();
		
		frontiere.add(new Point(-xFrGauche, -longueur/2));
		frontiere.add(new Point(-xFrGauche, longueur/2));
		frontiere.add(new Point(xFrGauche, longueur/2));
		frontiere.add(new Point(xFrGauche, -longueur/2));
		section.creerFrontiere(frontiere);
		return section;
	}
	
	public void createAndSave(String folder){
		Section section = getSection();
		
		File file = new File(folder + "/sections/" + nom + ".xml");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			section.save("", writer, nom);
			writer.close();
			fw.close();
		} catch (IOException e) {
			Controleur.LOG.error("Erreur d'écriture sur le fichier " + file.toString() + " : " + e.getMessage());
		}
		mold.createAndSave(folder + "/objets", section);
		System.out.println(nom + " cree");
	}

	
	
	/*public static void main(String[] args) {
		SectionCourbeCreator scc;
		
		scc = new SectionCourbeCreator(1, 1000, 10);
		scc.createAndSave("Std1V10d1000m");
		scc = new SectionCourbeCreator(1, 1000, 20);
		scc.createAndSave("Std1V20d1000m");
		scc = new SectionCourbeCreator(1, 1000, 30);
		scc.createAndSave("Std1V30d1000m");
		
		scc = new SectionCourbeCreator(1, 1500, 10);
		scc.createAndSave("Std1V10d1500m");
		scc = new SectionCourbeCreator(1, 1500, 20);
		scc.createAndSave("Std1V20d1500m");
		scc = new SectionCourbeCreator(1, 1500, 30);
		scc.createAndSave("Std1V30d1500m");
		
		scc = new SectionCourbeCreator(1, 2000, 10);
		scc.createAndSave("Std1V10d2000m");
		scc = new SectionCourbeCreator(1, 2000, 20);
		scc.createAndSave("Std1V20d2000m");
		scc = new SectionCourbeCreator(1, 2000, 30);
		scc.createAndSave("Std1V30d2000m");
		
		System.out.println("Fin création");
	}*/

}
