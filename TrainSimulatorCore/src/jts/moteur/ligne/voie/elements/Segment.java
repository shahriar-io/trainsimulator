package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.Sauvegardable;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointPassage;


/**Cette classe repr�sente un �l�ment de type segment.
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
		super.recupererAngle(angle, ratio);
		angle.setPsi(BasicGeo.getCap(p1, p2));
		angle.setTheta(Math.atan2(p2.getZ() - p1.getZ(), p1.getDistance(p2)));
	}
	
	public void recupererPoint(Point point, double ratio) {
		point.setXYZ(
				p1.getX() + ratio*(p2.getX() - p1.getX()),
				p1.getY() + ratio*(p2.getY() - p1.getY()),
				p1.getZ() + ratio*(p2.getZ() - p1.getZ()));
	}

	public void load(DataInputStream dis) throws IOException {
		super.load(dis);
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeShort(TypeElement.SEGMENT.ordinal());
		super.save(dos);
	}
}
