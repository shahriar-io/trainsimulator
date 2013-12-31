package jts.util.section;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.util.obj.Facette;
import jts.util.obj.Groupe;
import jts.util.obj.Objet3D;
import jts.util.obj.Patron;

public class ModeleObjCreator {
	
	public static double ECARTEMENT_STD = 4.0;
	public static double X_HAUT = 1.5;
	public static double X_BAS = 2.3;
	public static double Y_HAUT = 0.3;
	public static double Y_BAS = -0.5;
	public static double X_RAIL = 0.75;
	public static double HAUTEUR_RAIL = 0.15;
	
	/*public static void createParallelepipede(ModeleObj obj, Groupe groupe, Point p1, Point p2,
			Point p3, Point p4, Point p5, Point p6, Point p7, Point p8) {
		List<Point> texture = obj.getPointsTexture();
		obj.addPoint(p1);
		obj.addPoint(p2);
		obj.addPoint(p3);
		obj.addPoint(p4);
		groupe.addSurface(new Surface(p5, p1, p2, p6, texture.get(4), texture.get(0), texture.get(1), texture.get(5)));
		groupe.addSurface(new Surface(p6, p2, p3, p7, texture.get(5), texture.get(1), texture.get(2), texture.get(6)));
		groupe.addSurface(new Surface(p7, p3, p4, p8, texture.get(6), texture.get(2), texture.get(3), texture.get(7)));
	}
	
	public static void createParallelepipede(ModeleObj obj, Groupe groupe, Point p1, Point p2,
			Point p3, Point p4, Point p5, Point p6, Point p7, Point p8, int textureOffset) {
		List<Point> texture = obj.getPointsTexture();
		obj.addPoint(p1);
		obj.addPoint(p2);
		obj.addPoint(p3);
		obj.addPoint(p4);
		groupe.addSurface(new Surface(p5, p1, p2, p6, texture.get(4 + textureOffset), texture.get(0), texture.get(1), texture.get(5 + textureOffset)));
		groupe.addSurface(new Surface(p6, p2, p3, p7, texture.get(5 + textureOffset), texture.get(1), texture.get(2), texture.get(6 + textureOffset)));
		groupe.addSurface(new Surface(p7, p3, p4, p8, texture.get(6 + textureOffset), texture.get(2), texture.get(3), texture.get(7 + textureOffset)));
	}*/
	
	public static Patron createBallast(){
		List<Point> pts = new ArrayList<Point>();
		pts.add(new Point(-X_BAS, Y_BAS));
		pts.add(new Point(-X_HAUT, Y_HAUT-0.05));
		pts.add(new Point(-X_RAIL, Y_HAUT));
		pts.add(new Point(X_RAIL, Y_HAUT));
		pts.add(new Point(X_HAUT, Y_HAUT-0.05));
		pts.add(new Point(X_BAS, Y_BAS));
		Patron patron = new Patron(pts);
		return patron;
	}
	
	public static Patron createRail(double x){
		List<Point> pts = new ArrayList<Point>();
		pts.add(new Point(x - 0.075, Y_HAUT));
		pts.add(new Point(x - 0.008, Y_HAUT + 0.02));
		pts.add(new Point(x - 0.008, Y_HAUT + 0.1));
		pts.add(new Point(x - 0.037, Y_HAUT + 0.122));
		pts.add(new Point(x - 0.037, Y_HAUT + HAUTEUR_RAIL));
		pts.add(new Point(x + 0.037, Y_HAUT + HAUTEUR_RAIL));
		pts.add(new Point(x + 0.037, Y_HAUT + 0.122));
		pts.add(new Point(x + 0.008, Y_HAUT + 0.1));
		pts.add(new Point(x + 0.008, Y_HAUT + 0.02));
		pts.add(new Point(x + 0.075, Y_HAUT));
		Patron patron = new Patron(pts);
		return patron;
	}
	
	public static void paint(Objet3D obj, Groupe groupe, int offsetFacette, int offsetTexture){
		Facette facette = groupe.getFacettes().get(0 + offsetFacette);
		facette.getSommets()[0].setTexture(obj.getTextures().get(6 + offsetTexture));
		facette.getSommets()[1].setTexture(obj.getTextures().get(0));
		facette.getSommets()[2].setTexture(obj.getTextures().get(1));
		facette.getSommets()[3].setTexture(obj.getTextures().get(7 + offsetTexture));
		facette = groupe.getFacettes().get(1 + offsetFacette);
		facette.getSommets()[0].setTexture(obj.getTextures().get(7 + offsetTexture));
		facette.getSommets()[1].setTexture(obj.getTextures().get(1));
		facette.getSommets()[2].setTexture(obj.getTextures().get(2));
		facette.getSommets()[3].setTexture(obj.getTextures().get(8 + offsetTexture));
		facette = groupe.getFacettes().get(2 + offsetFacette);
		facette.getSommets()[0].setTexture(obj.getTextures().get(8 + offsetTexture));
		facette.getSommets()[1].setTexture(obj.getTextures().get(2));
		facette.getSommets()[2].setTexture(obj.getTextures().get(3));
		facette.getSommets()[3].setTexture(obj.getTextures().get(9 + offsetTexture));
		facette = groupe.getFacettes().get(3 + offsetFacette);
		facette.getSommets()[0].setTexture(obj.getTextures().get(9 + offsetTexture));
		facette.getSommets()[1].setTexture(obj.getTextures().get(3));
		facette.getSommets()[2].setTexture(obj.getTextures().get(4));
		facette.getSommets()[3].setTexture(obj.getTextures().get(10 + offsetTexture));
		facette = groupe.getFacettes().get(4 + offsetFacette);
		facette.getSommets()[0].setTexture(obj.getTextures().get(10 + offsetTexture));
		facette.getSommets()[1].setTexture(obj.getTextures().get(4));
		facette.getSommets()[2].setTexture(obj.getTextures().get(5));
		facette.getSommets()[3].setTexture(obj.getTextures().get(11 + offsetTexture));
	}
	
	public static void paint(Objet3D obj, Groupe groupe, int offsetFacette){
		paint(obj, groupe, offsetFacette, 0);
	}
}
