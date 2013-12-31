package jts.util.section;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.PointPassage;
import jts.util.obj.Extrusion;
import jts.util.obj.Facette;
import jts.util.obj.Groupe;
import jts.util.obj.Objet3D;
import jts.util.obj.Patron;
import jts.util.obj.VertexPosition;
import jts.util.obj.VertexTexture;

public class ModeleObjAiguillage extends ModeleObjCreator {
	
	private static double ANGLE_DISCRETISATION = Math.PI/360;

	private double longueurPrincipale;
	private double longueurDeviation;
	private double rayon;
	
	private double yCercle;
	private double alphaInt;
	private double yInt;
	
	private double ouverture;
	private int nbSousSections;
	private String[] nom;
	private Arc arc;

	private Objet3D[] obj;

	public ModeleObjAiguillage(double longueurPrincipale, double longueurDeviation, double rayon, double ouverture, String[] nom){
		this.longueurPrincipale = longueurPrincipale;
		this.longueurDeviation = longueurDeviation;
		this.rayon = rayon;
		this.ouverture = ouverture;
		this.nom = nom;
		this.obj = new Objet3D[4];
	}

	public void createAndSave(String folder, Section[] sections){
		obj[0] = this.createObjet(true, false, sections[0]);
		obj[1] = this.createObjet(false, false, sections[1]);
		obj[2] = this.createObjet(true, true, sections[2]);
		obj[3] = this.createObjet(false, true, sections[3]);
		obj[0].writeObj(new File(folder + "/" + nom[0] + ".obj"));
		obj[1].writeObj(new File(folder + "/" + nom[1] + ".obj"));
		obj[2].writeObj(new File(folder + "/" + nom[2] + ".obj"));
		obj[3].writeObj(new File(folder + "/" + nom[3] + ".obj"));
	}

	/*public void createAndSave(String nom, String folder){
		this.createObjet();
		obj.write(new File(folder + "/" + nom + ".obj"));
	}*/

	private Objet3D createObjet(boolean versionDroite, boolean versionFin, Section section) {
		//nbSousSections = (int)(ouverture/ANGLE_DISCRETISATION);
		yCercle = longueurDeviation-rayon*Math.sin(ouverture);
		alphaInt = Math.acos((rayon - X_RAIL)/(rayon + X_RAIL));
		nbSousSections = (int)(alphaInt/ANGLE_DISCRETISATION);
		yInt = (rayon + X_RAIL)*Math.sin(alphaInt) + yCercle;
		
		arc = (Arc)section.getElements().get(section.getElements().size()-1);
		if(!versionDroite){
			rayon = -rayon;
		}
		
		Objet3D obj = new Objet3D("textures_sections.mtl");
		obj.addTexture(new VertexTexture(0, 0));
		obj.addTexture(new VertexTexture(0.21, 0));
		obj.addTexture(new VertexTexture(0.35, 0));
		obj.addTexture(new VertexTexture(0.65, 0));
		obj.addTexture(new VertexTexture(0.79, 0));
		obj.addTexture(new VertexTexture(1, 0));
		
		double maxTexture = yCercle/5;
		maxTexture = ((int)((maxTexture+0.1)*10))/10.0;
		obj.addTexture(new VertexTexture(0, maxTexture));
		obj.addTexture(new VertexTexture(0.21, maxTexture));
		obj.addTexture(new VertexTexture(0.35, maxTexture));
		obj.addTexture(new VertexTexture(0.65, maxTexture));
		obj.addTexture(new VertexTexture(0.79, maxTexture));
		obj.addTexture(new VertexTexture(1, maxTexture));
		
		maxTexture = (rayon*alphaInt/5)/nbSousSections;
		maxTexture = ((int)((Math.abs(maxTexture)+0.1)*10))/10.0;
		obj.addTexture(new VertexTexture(0, maxTexture));
		obj.addTexture(new VertexTexture(0.21, maxTexture));
		obj.addTexture(new VertexTexture(0.35, maxTexture));
		obj.addTexture(new VertexTexture(0.65, maxTexture));
		obj.addTexture(new VertexTexture(0.79, maxTexture));
		obj.addTexture(new VertexTexture(1, maxTexture));
		
		obj.addTexture(new VertexTexture(0.5, 0));
		obj.addTexture(new VertexTexture(0.5, maxTexture));
		
		maxTexture = (longueurPrincipale-yInt)/5;
		maxTexture = ((int)((Math.abs(maxTexture)+0.1)*10))/10.0;
		obj.addTexture(new VertexTexture(0, maxTexture));
		obj.addTexture(new VertexTexture(0.21, maxTexture));
		obj.addTexture(new VertexTexture(0.35, maxTexture));
		obj.addTexture(new VertexTexture(0.65, maxTexture));
		obj.addTexture(new VertexTexture(0.79, maxTexture));
		obj.addTexture(new VertexTexture(1, maxTexture));
		
		maxTexture = (rayon*(ouverture-alphaInt)/5);
		maxTexture = ((int)((Math.abs(maxTexture)+0.1)*10))/10.0;
		obj.addTexture(new VertexTexture(0, maxTexture));
		obj.addTexture(new VertexTexture(0.21, maxTexture));
		obj.addTexture(new VertexTexture(0.35, maxTexture));
		obj.addTexture(new VertexTexture(0.65, maxTexture));
		obj.addTexture(new VertexTexture(0.79, maxTexture));
		obj.addTexture(new VertexTexture(1, maxTexture));
		
		Patron patron = createBallast();
		Extrusion ballast = new Extrusion(obj, false, "ballast1", "Ballast", section.getElements().get(0), patron, false);
		ballast.build(1);
		obj.addGroupe(ballast);
		paint(obj, ballast, 0);
		
		patron = createRail(-X_RAIL);
		Extrusion railG = new Extrusion(obj, false, "rail_gauche_1", "Rail", section.getElements().get(0), patron, false);
		railG.build(1);
		obj.addGroupe(railG);
		
		patron = createRail(X_RAIL);
		Extrusion railD = new Extrusion(obj, false, "rail_droite_1", "Rail", section.getElements().get(0), patron, false);
		railD.build(1);
		obj.addGroupe(railD);
		
		if(versionFin){
			patron = createBallast();
			ballast = new Extrusion(obj, false, "ballast2", "Ballast", section.getElements().get(1), patron, false);
			ballast.build(nbSousSections);
			obj.addGroupe(ballast);
			for(int i=0; i<nbSousSections; i++){
				paint(obj, ballast, i*5, 6);
			}
			
			patron = createRail(-X_RAIL);
			railG = new Extrusion(obj, false, "rail_gauche_2", "Rail", section.getElements().get(1), patron, false);
			railG.build(nbSousSections);
			obj.addGroupe(railG);
			
			patron = createRail(X_RAIL);
			railD = new Extrusion(obj, false, "rail_droite_2", "Rail", section.getElements().get(1), patron, false);
			railD.build(nbSousSections);
			obj.addGroupe(railD);
		} else {
			if(versionDroite){
				patron = createRail(-X_RAIL);
				railG = new Extrusion(obj, false, "rail_gauche_2", "Rail", section.getElements().get(1), patron, false);
				railG.build(1);
				obj.addGroupe(railG);
				
				patron = createRail(X_RAIL);
				railD = new Extrusion(obj, false, "rail_droite_2", "Rail", section.getElements().get(2), patron, false);
				railD.build(nbSousSections);
				obj.addGroupe(railD);
			} else {
				patron = createRail(-X_RAIL);
				railG = new Extrusion(obj, false, "rail_gauche_2", "Rail", section.getElements().get(2), patron, false);
				railG.build(nbSousSections);
				obj.addGroupe(railG);
				
				patron = createRail(X_RAIL);
				railD = new Extrusion(obj, false, "rail_droite_2", "Rail", section.getElements().get(1), patron, false);
				railD.build(1);
				obj.addGroupe(railD);
			}
			
			nbSousSections = (int)(alphaInt/ANGLE_DISCRETISATION);
			List<Point> pts = new ArrayList<Point>();
			pts.add(new Point(-X_BAS, Y_BAS));
			pts.add(new Point(-X_HAUT, Y_HAUT-0.05));
			pts.add(new Point(-X_RAIL, Y_HAUT));
			pts.add(new Point(0, Y_HAUT));
			pts.add(new Point(X_RAIL, Y_HAUT));
			pts.add(new Point(X_HAUT, Y_HAUT-0.05));
			pts.add(new Point(X_BAS, Y_BAS));
			
			Groupe ballast2 = new Groupe(obj, "ballast2", "Ballast");
			List<VertexPosition> vps = new ArrayList<VertexPosition>();
			
			for(Point p : pts){
				vps.add(addPosition(p, new Point(0, yCercle), 0, ballast2));
			}
			
			if(!versionDroite){
				rayon = -rayon;
			}
			double alpha = 0;
			double yInt = 0;
			for(int i=1; i<=nbSousSections; i++){
				alpha = (i*alphaInt)/nbSousSections;
				yInt = (rayon + X_RAIL)*Math.sin(alpha) + yCercle;
				Point pInt = new Point();
				arc.recupererPoint(pInt, alpha/ouverture);
				if(versionDroite){
					vps.add(addPosition(pts.get(0), new Point(0, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(1), new Point(0, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(2), new Point(0, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(3), new Point(pInt.getX()/2, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(4), pInt, alpha, ballast2));
					vps.add(addPosition(pts.get(5), pInt, alpha, ballast2));
					vps.add(addPosition(pts.get(6), pInt, alpha, ballast2));
				} else {
					vps.add(addPosition(pts.get(0), pInt, -alpha, ballast2));
					vps.add(addPosition(pts.get(1), pInt, -alpha, ballast2));
					vps.add(addPosition(pts.get(2), pInt, -alpha, ballast2));
					vps.add(addPosition(pts.get(3), new Point(pInt.getX()/2, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(4), new Point(0, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(5), new Point(0, yInt), 0, ballast2));
					vps.add(addPosition(pts.get(6), new Point(0, yInt), 0, ballast2));
				}
				Facette facette;
				
				facette = new Facette(vps, 7*i, 7*(i-1), 7*(i-1)+1, 7*i+1);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(12));
				facette.getSommets()[1].setTexture(obj.getTextures().get(0));
				facette.getSommets()[2].setTexture(obj.getTextures().get(1));
				facette.getSommets()[3].setTexture(obj.getTextures().get(13));
				
				facette = new Facette(vps, 7*i+1, 7*(i-1)+1, 7*(i-1)+2, 7*i+2);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(13));
				facette.getSommets()[1].setTexture(obj.getTextures().get(1));
				facette.getSommets()[2].setTexture(obj.getTextures().get(2));
				facette.getSommets()[3].setTexture(obj.getTextures().get(14));
				
				facette = new Facette(vps, 7*i+2, 7*(i-1)+2, 7*(i-1)+3, 7*i+3);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(14));
				facette.getSommets()[1].setTexture(obj.getTextures().get(2));
				facette.getSommets()[2].setTexture(obj.getTextures().get(18));
				facette.getSommets()[3].setTexture(obj.getTextures().get(19));
				
				facette = new Facette(vps, 7*i+3, 7*(i-1)+3, 7*(i-1)+4, 7*i+4);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(19));
				facette.getSommets()[1].setTexture(obj.getTextures().get(18));
				facette.getSommets()[2].setTexture(obj.getTextures().get(3));
				facette.getSommets()[3].setTexture(obj.getTextures().get(15));
				
				facette = new Facette(vps, 7*i+4, 7*(i-1)+4, 7*(i-1)+5, 7*i+5);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(15));
				facette.getSommets()[1].setTexture(obj.getTextures().get(3));
				facette.getSommets()[2].setTexture(obj.getTextures().get(4));
				facette.getSommets()[3].setTexture(obj.getTextures().get(16));
				
				facette = new Facette(vps, 7*i+5, 7*(i-1)+5, 7*(i-1)+6, 7*i+6);
				ballast2.addFacette(facette);
				facette.getSommets()[0].setTexture(obj.getTextures().get(16));
				facette.getSommets()[1].setTexture(obj.getTextures().get(4));
				facette.getSommets()[2].setTexture(obj.getTextures().get(5));
				facette.getSommets()[3].setTexture(obj.getTextures().get(17));
			}
			
			Segment segment = new Segment(new PointPassage(0, yInt, 0, 0), new PointPassage(0, longueurPrincipale, 0, 0), 0);
			Arc newArc = null;
			if(versionDroite){
				newArc = new Arc(arc.getP1(), arc.getP2(), 0, arc.getCentre(), arc.getRayon(), -Math.PI/2+alpha, ouverture-alpha);
				
				patron = createRail(-X_RAIL);
				railG = new Extrusion(obj, false, "rail_gauche_2", "Rail", /*newArc*/section.getElements().get(2), patron, false);
				railG.build(nbSousSections);
				obj.addGroupe(railG);
				
				patron = createRail(X_RAIL);
				railD = new Extrusion(obj, false, "rail_droite_3", "Rail", /*segment*/section.getElements().get(1), patron, false);
				railD.build(1);
				obj.addGroupe(railD);
				
				patron = createBallast();
				ballast = new Extrusion(obj, false, "ballast3", "Ballast", segment, patron, false);
				ballast.build(1);
				obj.addGroupe(ballast);
				paint(obj, ballast, 0, 14);
				
				patron = createBallast();
				ballast = new Extrusion(obj, false, "ballast4", "Ballast", newArc, patron, false);
				ballast.build(1);
				obj.addGroupe(ballast);
				paint(obj, ballast, 0, 20);
			} else {
				newArc = new Arc(arc.getP1(), arc.getP2(), 0, arc.getCentre(), arc.getRayon(), Math.PI/2-alpha, -ouverture+alpha);
				
				patron = createRail(-X_RAIL);
				railG = new Extrusion(obj, false, "rail_gauche_2", "Rail", /*segment*/section.getElements().get(1), patron, false);
				railG.build(1);
				obj.addGroupe(railG);
				
				patron = createRail(X_RAIL);
				railD = new Extrusion(obj, false, "rail_droite_2", "Rail", /*newArc*/section.getElements().get(2), patron, false);
				railD.build(nbSousSections);
				obj.addGroupe(railD);
				
				patron = createBallast();
				ballast = new Extrusion(obj, false, "ballast3", "Ballast", segment, patron, false);
				ballast.build(1);
				obj.addGroupe(ballast);
				paint(obj, ballast, 0, 14);
				
				patron = createBallast();
				ballast = new Extrusion(obj, false, "ballast4", "Ballast", newArc, patron, false);
				ballast.build(1);
				obj.addGroupe(ballast);
				paint(obj, ballast, 0, 20);
			}
			
			obj.addGroupe(ballast2);
		}
		
		return obj;
	}
	
	private VertexPosition addPosition(Point p, Point translation, double psi, Groupe groupe){
		Point newP = new Point(p.getX(), 0, p.getY());
		newP.transformer(translation, psi);
		VertexPosition vp = new VertexPosition(newP);
		groupe.addPosition(vp);
		return vp;
	}

	public Objet3D[] getObj(){
		return this.obj;
	}
}
