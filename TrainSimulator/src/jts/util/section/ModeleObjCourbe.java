package jts.util.section;

import java.io.File;

import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Arc;
import jts.util.obj.Extrusion;
import jts.util.obj.Objet3D;
import jts.util.obj.Patron;
import jts.util.obj.VertexTexture;

public class ModeleObjCourbe extends ModeleObjCreator {
	
	private static double ANGLE_DISCRETISATION = Math.PI/360;
	
	private int nbVoies;
	private double ouverture;
	private double roulis;
	private double rayon;
	private String nom;
	private int nbSousSections;
	
	private Objet3D obj;
	
	public ModeleObjCourbe(int nbVoies, double rayon, double ouverture, double roulis, String nom){
		this.nbVoies = nbVoies;
		this.ouverture = ouverture;
		this.roulis = roulis;
		this.rayon = rayon;
		this.nom = nom;
	}
	
	public void createAndSave(String folder, Section section){
		this.createObjet(section);
		obj.writeObj(new File(folder + "/" + nom + ".obj"));
	}

	private void createObjet(Section section) {
		nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		double maxTexture = (rayon*ouverture/5)/nbSousSections;
		maxTexture = ((int)((maxTexture+0.5)*2))/2.0;
		obj = new Objet3D("textures_sections.mtl");
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
			Arc arc = (Arc)section.getElements().get(i);
			
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
	}
	
	public Objet3D getObj(){
		return this.obj;
	}
}
