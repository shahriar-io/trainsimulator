package jts.util.section;

import java.util.List;

import jts.moteur.geometrie.Point;
import jts.util.obj.Groupe;
import jts.util.obj.ModeleObj;
import jts.util.obj.Surface;

public class ModeleObjCreator {
	
	public static double ECARTEMENT_STD = 4.0;
	public static double X_HAUT = 1.5;
	public static double X_BAS = 2.3;
	public static double Y_HAUT = 0.3;
	public static double Y_BAS = -0.5;
	public static double X_RAIL = 0.75;
	public static double HAUTEUR_RAIL = 0.15;
	
	public static void createParallelepipede(ModeleObj obj, Groupe groupe, Point p1, Point p2,
			Point p3, Point p4, Point p5, Point p6, Point p7, Point p8) {
		List<Point> texture = obj.getPointsTexture();
		obj.addPoint(p1);
		obj.addPoint(p2);
		obj.addPoint(p3);
		obj.addPoint(p4);
		/*groupe.addSurface(new Surface(p6, p2, p1, p5));
		groupe.addSurface(new Surface(p5, p1, p4, p8));
		groupe.addSurface(new Surface(p8, p4, p3, p7));
		groupe.addSurface(new Surface(p7, p3, p2, p6));*/
		groupe.addSurface(new Surface(p5, p1, p2, p6, texture.get(4), texture.get(0), texture.get(1), texture.get(5)));
		groupe.addSurface(new Surface(p6, p2, p3, p7, texture.get(5), texture.get(1), texture.get(2), texture.get(6)));
		groupe.addSurface(new Surface(p7, p3, p4, p8, texture.get(6), texture.get(2), texture.get(3), texture.get(7)));
	}
}
