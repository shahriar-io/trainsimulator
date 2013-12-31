package jts.util.obj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.PointExtremite;
import jts.moteur.ligne.voie.points.PointPassage;

public class Extrusion extends Groupe {
	
	private CourbeElementaire directeur;
	private Patron patron;
	private boolean ferme;


	public Extrusion(Objet3D objet, boolean adouci, String nom, String nomMateriel, CourbeElementaire directeur, Patron patron, boolean ferme){
		super(objet, adouci, nom, nomMateriel);
		this.directeur = directeur;
		this.patron = patron;
		this.ferme = ferme;
	}
	
	public void build(int discretisation){
		//int discretisation = 300;
		AngleEuler angle = new AngleEuler();
		
		for(int i=0; i<discretisation+1; i++){
			double ratio = i/(double)discretisation;
			Point pt = new Point();
			directeur.recupererPoint(pt, ratio);
			directeur.recupererAngle(angle, ratio);
			for(Point p : patron.getPatron()){
				Point newP = new Point(p.getX(), 0, p.getY());
				newP.transformer(pt, angle);
				super.addPosition(new VertexPosition(newP.getY(), newP.getZ(), newP.getX()));
			}
		}
		
		directeur.recupererAngle(angle, 0);
		double psip = angle.getPsi();
		int taille = patron.getPatron().size();
		for(int i=0; i<discretisation; i++){
			double ratio = (i+1)/(double)(discretisation);
			directeur.recupererAngle(angle, ratio);
			if(Math.abs(angle.getPsi() - psip)<Math.PI/2 || Math.abs(angle.getTheta())<Math.PI/3){
				for(int j=0; j<taille-1; j++){
					this.addFacette(new Facette(super.positions, taille*(i+1)+j, taille*i+j, taille*i+j+1, taille*(i+1)+j+1));
				}
				if(ferme){
					int j = taille-1;
					this.addFacette(new Facette(super.positions, taille*(i+1)+j, taille*i+j, taille*i, taille*(i+1)));
				}
			} else {
				for(int j=0; j<taille-1; j++){
					int j0 = (j+(taille/2))%taille;
					int j1 = (j+1+(taille/2))%taille;
					this.addFacette(new Facette(super.positions, taille*(i+1)+j, taille*i+j0, taille*i+j1, taille*(i+1)+j+1));
				}
				if(ferme){
					int j = taille-1;
					int j0 = (j+(taille/2))%taille;
					int j1 = (j+1+(taille/2))%taille;
					this.addFacette(new Facette(super.positions, taille*(i+1)+j, taille*i+j0, taille*i+j1, taille*(i+1)));
				}
			}
			psip = angle.getPsi();
		}
	}
	
	public static void main(String[] args){
		/*List<Point> ptsCtrl = new ArrayList<Point>();	
		ptsCtrl.add(new Point(2, 0, 11));
		ptsCtrl.add(new Point(2, 0, 10));
		ptsCtrl.add(new Point(-2, 0, 10));
		ptsCtrl.add(new Point(-2, 0, 7));
		ptsCtrl.add(new Point(2, 0, 7));
		ptsCtrl.add(new Point(2, 0, 9));
		ptsCtrl.add(new Point(-2, 0, 9));
		ptsCtrl.add(new Point(-2, 0, 4));
		ptsCtrl.add(new Point(2, 0, 4));
		ptsCtrl.add(new Point(-2, 0, 4));
		ptsCtrl.add(new Point(-2, 0, 0));
		ptsCtrl.add(new Point(3, 0, 0));
		
		ptsCtrl.add(new Point(3, 3, 0));
		ptsCtrl.add(new Point(-3, 3, 0));
		ptsCtrl.add(new Point(-3, -3, 0));
		
		ptsCtrl.add(new Point(0, -3, 0));
		ptsCtrl.add(new Point(0, 2, 0));
		ptsCtrl.add(new Point(0, 2, 12.5));
		ptsCtrl.add(new Point(0, 2, 4));
		ptsCtrl.add(new Point(0, -2, 4));
		ptsCtrl.add(new Point(0, -2, 10));
		ptsCtrl.add(new Point(0, -3, 10));
		ptsCtrl.add(new Point(0, -3, 6));
		ptsCtrl.add(new Point(0, -2.5, 6));
		ptsCtrl.add(new Point(0, -2.25, 6.5));
		
		List<Double> noeuds = new ArrayList<Double>();
		noeuds.add(0.);
		noeuds.add(0.);
		noeuds.add(0.);
		noeuds.add(0.1);
		noeuds.add(0.2);
		noeuds.add(0.3);
		noeuds.add(0.4);
		noeuds.add(0.5);
		noeuds.add(0.6);
		noeuds.add(0.7);
		noeuds.add(0.8);
		noeuds.add(0.9);
		noeuds.add(1.);
		noeuds.add(1.);
		noeuds.add(1.);
		
		BSpline bs = new BSpline(new PointPassage(0, 0, 0, 0), new PointPassage(0, 10, 0, 0), 2, ptsCtrl);
		Objet3D epy = new Objet3D("");
		Extrusion extru = new Extrusion(epy, true, "branches", "Ballast", bs, Patron.getCercle(16, 0.2), true);
		extru.build();

		epy.writeObj(new File("test_bs.obj"));*/
		
		Objet3D section = new Objet3D("textures_sections.mtl");
		PointExtremite p1 = new PointPassage(0, -5, 0, 0);
		PointExtremite p2 = new PointPassage(0, 5, 0, 0);
		Segment segment = new Segment(p1, p2, 0);
		
		List<Point> pts = new ArrayList<Point>();
		pts.add(new Point(-2.3, -0.5));
		pts.add(new Point(-1.5, 0.3));
		pts.add(new Point(1.5, 0.3));
		pts.add(new Point(2.3, -0.5));
		Patron patron = new Patron(pts);
		Extrusion ballast = new Extrusion(section, false, "ballast", "Ballast", segment, patron, false);
		ballast.build(1);
		section.addGroupe(ballast);
		ballast.addTexture(new VertexTexture(0, 0));
		ballast.addTexture(new VertexTexture(0.21, 0));
		ballast.addTexture(new VertexTexture(0.79, 0));
		ballast.addTexture(new VertexTexture(1, 0));
		ballast.addTexture(new VertexTexture(0, 2));
		ballast.addTexture(new VertexTexture(0.21, 2));
		ballast.addTexture(new VertexTexture(0.79, 2));
		ballast.addTexture(new VertexTexture(1, 2));
		Facette facette;
		facette = ballast.getFacettes().get(0);
		facette.getSommets()[0].setTexture(ballast.textures.get(4));
		facette.getSommets()[1].setTexture(ballast.textures.get(0));
		facette.getSommets()[2].setTexture(ballast.textures.get(1));
		facette.getSommets()[3].setTexture(ballast.textures.get(5));
		facette = ballast.getFacettes().get(1);
		facette.getSommets()[0].setTexture(ballast.textures.get(5));
		facette.getSommets()[1].setTexture(ballast.textures.get(1));
		facette.getSommets()[2].setTexture(ballast.textures.get(2));
		facette.getSommets()[3].setTexture(ballast.textures.get(6));
		facette = ballast.getFacettes().get(2);
		facette.getSommets()[0].setTexture(ballast.textures.get(6));
		facette.getSommets()[1].setTexture(ballast.textures.get(2));
		facette.getSommets()[2].setTexture(ballast.textures.get(3));
		facette.getSommets()[3].setTexture(ballast.textures.get(7));
		
		pts = new ArrayList<Point>();
		pts.add(new Point(-0.075, 0.3));
		pts.add(new Point(-0.008, 0.3 + 0.02));
		pts.add(new Point(-0.008, 0.3 + 0.1));
		pts.add(new Point(-0.037, 0.3 + 0.122));
		pts.add(new Point(-0.037, 0.3 + 0.172));
		pts.add(new Point(0.037, 0.3 + 0.172));
		pts.add(new Point(0.037, 0.3 + 0.122));
		pts.add(new Point(0.008, 0.3 + 0.1));
		pts.add(new Point(0.008, 0.3 + 0.02));
		pts.add(new Point(0.075, 0.3));
		patron = new Patron(pts);
		p1 = new PointPassage(-0.75, -5, 0, 0);
		p2 = new PointPassage(-0.75, 5, 0, 0);
		segment = new Segment(p1, p2, 0);
		Extrusion railG = new Extrusion(section, false, "rail_gauche", "Rail", segment, patron, false);
		railG.build(1);
		section.addGroupe(railG);
		
		p1 = new PointPassage(0.75, -5, 0, 0);
		p2 = new PointPassage(0.75, 5, 0, 0);
		segment = new Segment(p1, p2, 0);
		Extrusion railD = new Extrusion(section, false, "rail_droite", "Rail", segment, patron, false);
		railD.build(1);
		section.addGroupe(railD);
		
		section.writeObj(new File("Std1V10m.obj"));
	}
}
