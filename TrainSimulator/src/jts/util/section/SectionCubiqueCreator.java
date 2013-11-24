package jts.util.section;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.util.BasicConvert;

public class SectionCubiqueCreator {
	
	private int nbVoies;
	private double courbureFin;
	private double psiFin;
	private double roulis;
	private String[] nom;
	private ModeleObjCubique moc;
	
	private double sFin;
	
	public SectionCubiqueCreator(int nbVoies, double rayon, double ouvertureDeg){
		this(nbVoies, rayon, ouvertureDeg, 0);
	}

	public SectionCubiqueCreator(int nbVoies, double rayon, double ouvertureDeg, double roulisDeg){
		this.nbVoies = nbVoies;
		this.courbureFin = 1/rayon;
		this.psiFin = BasicConvert.degToRad(ouvertureDeg);
		this.roulis = BasicConvert.degToRad(roulisDeg);
		this.nom = new String[2];
		if(roulisDeg!=0){
			this.nom[0] = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "d" + (int)roulisDeg + "drcubd";
			this.nom[1] = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "d" + (int)roulisDeg + "drcubg";
		} else {
			this.nom[0] = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "dcubd";
			this.nom[1] = "Std" + nbVoies + "V" + (int)rayon + "m" + (int)ouvertureDeg + "dcubg";
		}
		this.sFin = 2*psiFin/courbureFin;
		this.moc = new ModeleObjCubique(nbVoies, rayon, psiFin, roulis, sFin, nom);
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
		
		moc.createAndSave(folder + "/objets");
		System.out.println(nom[0] + " cree");
		System.out.println(nom[1] + " cree");
	}

	public Section getSection(boolean versionDroite) {
		Section section = new Section();
		if(versionDroite){
			section.setNomObjet(nom[0]);
		} else {
			section.setNomObjet(nom[1]);
		}
		
		
		PointFrontiere p1;
		PointFrontiere p2;
		
		p1 = new PointFrontiere(0, 0, 0, 0);
		CourbeElementaire cubique;
		if(versionDroite){
			p2 = new PointFrontiere(courbureFin*Math.pow(sFin, 2)/6, sFin, 0, 0);
			p2.setPhi(roulis);
			cubique = new Cubique(p1, p2, 0, courbureFin, psiFin);
		} else {
			p2 = new PointFrontiere(-courbureFin*Math.pow(sFin, 2)/6, sFin, 0, 0);
			p2.setPhi(-roulis);
			cubique = new Cubique(p1, p2, 0, -courbureFin, -psiFin);
		}
		
		section.getPointsExtremites().add(p1);
		section.getPointsExtremites().add(p2);
		section.addElement(cubique);
		
		int nbSousSections = (int)(courbureFin*psiFin)/50;
		double ouvertureElem = psiFin/(double)nbSousSections;
		double xFrGauche = -1.5 + 0;
		
		List<Point> frontiere = new ArrayList<Point>();
		
		frontiere.add(new Point(-xFrGauche, 0));
		for(int i=1; i<=nbSousSections; i++){
			Point pf = new Point(-xFrGauche - courbureFin, 0);
			pf.transformer(new Point(courbureFin, 0), (double)(i*ouvertureElem));
			frontiere.add(pf);
		}
		for(int i=nbSousSections; i>=1; i--){
			Point pf = new Point(xFrGauche - courbureFin, 0);
			pf.transformer(new Point(courbureFin, 0), (double)(i*ouvertureElem));
			frontiere.add(pf);
		}
		frontiere.add(new Point(xFrGauche, 0));
		section.creerFrontiere(frontiere);
		return section;
	}
	
	public static void main(String[] args) {
		SectionCubiqueCreator scc;
		
		scc = new SectionCubiqueCreator(1, 1000, 5, 12);
		scc.createAndSave("");
		scc = new SectionCubiqueCreator(1, 1500, 5, 12);
		scc.createAndSave("");
		scc = new SectionCubiqueCreator(1, 2000, 5, 12);
		scc.createAndSave("");
		
		System.out.println("Fin création");
	}

}
