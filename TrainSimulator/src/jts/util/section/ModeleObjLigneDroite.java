package jts.util.section;

import java.io.File;

import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Segment;
import jts.util.obj.Extrusion;
import jts.util.obj.Objet3D;
import jts.util.obj.Patron;
import jts.util.obj.VertexTexture;

public class ModeleObjLigneDroite extends ModeleObjCreator {
	
	private double longueur;
	private int nbVoies;
	private String nom;
	
	private Objet3D obj;
	
	public ModeleObjLigneDroite(int nbVoies, double longueur, String nom){
		this.nbVoies = nbVoies;
		this.longueur = longueur;
		this.nom = nom;
	}
	
	public void createAndSave(String folder, Section section){
		this.createAndSave(this.nom, folder, section);
	}
	
	public void createAndSave(String nom, String folder, Section section){
		this.createObjet(section);
		obj.writeObj(new File(folder + "/" + nom + ".obj"));
	}

	private void createObjet(Section section) {
		obj = new Objet3D("textures_sections.mtl");
		obj.addTexture(new VertexTexture(0, 0));
		obj.addTexture(new VertexTexture(0.21, 0));
		obj.addTexture(new VertexTexture(0.35, 0));
		obj.addTexture(new VertexTexture(0.65, 0));
		obj.addTexture(new VertexTexture(0.79, 0));
		obj.addTexture(new VertexTexture(1, 0));
		obj.addTexture(new VertexTexture(0, longueur/5));
		obj.addTexture(new VertexTexture(0.21, longueur/5));
		obj.addTexture(new VertexTexture(0.35, longueur/5));
		obj.addTexture(new VertexTexture(0.65, longueur/5));
		obj.addTexture(new VertexTexture(0.79, longueur/5));
		obj.addTexture(new VertexTexture(1, longueur/5));
		
		double xVoie = -2*(nbVoies-1);
		for(int i=0; i<nbVoies; i++){
			xVoie = 2*(2*i+1-nbVoies);
			
			//Création du ballast
			Segment segment = (Segment)section.getElements().get(i);
			
			Patron patron = createBallast();
			Extrusion ballast = new Extrusion(obj, false, "ballast" + (i+1), "Ballast", segment, patron, false);
			ballast.build(1);
			obj.addGroupe(ballast);
			paint(obj, ballast, 0);
			
			//Création des rails
			patron = createRail(-X_RAIL);
			Extrusion railG = new Extrusion(obj, false, "rail_gauche_" + (i+1), "Rail", segment, patron, false);
			railG.build(1);
			obj.addGroupe(railG);
			
			patron = createRail(X_RAIL);
			Extrusion railD = new Extrusion(obj, false, "rail_droite_" + (i+1), "Rail", segment, patron, false);
			railD.build(1);
			obj.addGroupe(railD);
		}
	}
	
	public Objet3D getObj(){
		return this.obj;
	}
}
