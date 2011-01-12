package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.Sauvegardable;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.geometrie.Vecteur;
import jts.moteur.ligne.voie.points.PointPassage;
import jts.util.BasicGeometrie;


/**Cette classe représente un élément de type segment.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Segment extends CourbeElementaire implements Sauvegardable{

	public Segment(){
		this(null, null);
	}
	
	public Segment(PointPassage p1, PointPassage p2) {
		super(p1, p2, TypeElement.SEGMENT);
		this.calculerLongueur();
	}
	
	public void calculerLongueur() {
		this.longueur = p1.getDistance(p2);
	}
	
	public void recupererAngle(AngleEuler angle, double ratio) {
		angle.setPsi(BasicGeometrie.getAngle(p1, p2));
	}
	
	public void recupererPoint(Point point, double ratio) {
		point.setXYZ(
				p1.getX() + ratio*(p2.getX() - p1.getX()),
				p1.getY() + ratio*(p2.getY() - p1.getY()),
				p1.getZ() + ratio*(p2.getZ() - p1.getZ()));
	}
	
	public double projeter(Point point){
		double X = p2.getX() - p1.getX();
		double Y = p2.getY() - p1.getY();
		double Z = p2.getZ() - p1.getZ();
		double c = (p2.getX() - point.getX())*X + (p2.getY() - point.getY())*Y + (p2.getZ() - point.getZ())*Z;
		double alpha = c/(X*X+Y*Y+Z*Z);
		return alpha;
	}
	
	public double projeterAbsCurv(Point point) {
		//La création de vecteur peut consommer du temps
		/*Vecteur vecteur1 = new Vecteur(p1, point);
		Vecteur vecteur2 = new Vecteur(p1, p2);
	    double angle = BasicGeo.liPi(vecteur1.getAngle() - vecteur2.getAngle());
	    double absCurv = Math.cos(angle)*vecteur1.getNorme();*/
		//return absCurv;
		
		double X = p2.getX() - p1.getX();
		double Y = p2.getY() - p1.getY();
		double Z = p2.getZ() - p1.getZ();
		double c = (p2.getX() - point.getX())*X + (p2.getY() - point.getY())*Y + (p2.getZ() - point.getZ())*Z;
		double alpha = c/(X*X+Y*Y+Z*Z);
		return ((1-alpha)*longueur);
	}
	
	public double projeterDistance(Point point) {
		//La création de vecteur peut consommer du temps
		Vecteur vecteur1 = new Vecteur(p1, point);
		Vecteur vecteur2 = new Vecteur(p1, p2);
	    double angle = BasicGeo.liPi(vecteur1.getAngle() - vecteur2.getAngle());
	    double distance = Math.abs(Math.sin(angle)*vecteur1.getNorme());
		return distance;
	}

	public void load(DataInputStream dis) throws IOException {
		super.load(dis);
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeShort(TypeElement.SEGMENT.ordinal());
		super.save(dos);
	}
}
