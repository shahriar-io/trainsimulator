package jts.util.section;

import java.io.File;

import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.util.obj.Extrusion;
import jts.util.obj.Objet3D;
import jts.util.obj.Patron;
import jts.util.obj.VertexTexture;

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
	
	private Objet3D[] obj;
	
	public ModeleObjCubique(int nbVoies, double rayon, double ouverture, double roulis, double sFin, String[] nom){
		this.nbVoies = nbVoies;
		this.ouverture = ouverture;
		this.roulis = roulis;
		this.rayon = rayon;
		this.sFin = sFin;
		this.nom = nom;
		this.obj = new Objet3D[2];
	}
	
	public void createAndSave(String folder, Section[] sections){
		obj[0] = this.createObjet(true, sections[0]);
		obj[1] = this.createObjet(false, sections[1]);
		obj[0].writeObj(new File(folder + "/" + nom[0] + ".obj"));
		obj[1].writeObj(new File(folder + "/" + nom[1] + ".obj"));
	}

	private Objet3D createObjet(boolean versionDroite, Section section) {
		nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		double maxTexture = (sFin/5)/nbSousSections;
		maxTexture = ((int)((maxTexture+0.5)*2))/2.0;
		Objet3D obj = new Objet3D("textures_sections.mtl");
		obj.addTexture(new VertexTexture(0, 0));
		obj.addTexture(new VertexTexture(0.21, 0));
		obj.addTexture(new VertexTexture(0.35, 0));
		obj.addTexture(new VertexTexture(0.65, 0));
		obj.addTexture(new VertexTexture(0.79, 0));
		obj.addTexture(new VertexTexture(1, 0));
		obj.addTexture(new VertexTexture(0, maxTexture));
		obj.addTexture(new VertexTexture(0.21, maxTexture));
		obj.addTexture(new VertexTexture(0.35, maxTexture));
		obj.addTexture(new VertexTexture(0.65, maxTexture));
		obj.addTexture(new VertexTexture(0.79, maxTexture));
		obj.addTexture(new VertexTexture(1, maxTexture));
		
		for(int i=0; i<nbVoies; i++){
			double xVoie = 2*(2*i+1-nbVoies);
			
			//Création du ballast
			Cubique arc = (Cubique)section.getElements().get(i);
			
			Patron patron = createBallast();
			Extrusion ballast = new Extrusion(obj, false, "ballast" + (i+1), "Ballast", arc, patron, false);
			ballast.build(nbSousSections);
			obj.addGroupe(ballast);
			for(int j=0; j<nbSousSections; j++){
				paint(obj, ballast, 5*j);
			}
			
			patron = createRail(xVoie - 0.75);
			Extrusion railG = new Extrusion(obj, false, "rail_gauche_" + (i+1), "Rail", arc, patron, false);
			railG.build(nbSousSections);
			obj.addGroupe(railG);
			
			patron = createRail(xVoie + 0.75);
			Extrusion railD = new Extrusion(obj, false, "rail_droite_" + (i+1), "Rail", arc, patron, false);
			railD.build(nbSousSections);
			obj.addGroupe(railD);
		}
		
		return obj;
	}
	
	public Objet3D[] getObj(){
		return this.obj;
	}
}
