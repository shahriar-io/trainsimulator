package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.SauvegardableBinaire;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointExtremite;


/**Cette classe représente une courbe de type segment.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Segment extends CourbeElementaire implements SauvegardableBinaire{

	public Segment(){
		this(null, null, 0);
	}
	
	public Segment(PointExtremite p1, PointExtremite p2, double theta) {
		super(p1, p2, TypeElement.SEGMENT, theta);
		this.calculerLongueur();
	}
	
	public void calculerLongueur() {
		this.longueur = p1.getDistance(p2);
	}
	
	public void recupererAngle(AngleEuler angle, double ratio) {
		super.recupererAngle(angle, ratio);
		angle.setPsi(BasicGeo.getCap(p1, p2));
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
		super.save(dos);
	}
	
	/*public void save(String indent, BufferedWriter writer, int p1, int p2) throws IOException{
		writer.write(indent + "<Segment p1=\"#" + p1 + "\" p2=\"#" + p2 + "\"/>");
		writer.newLine();
	}*/
	
	public ElementXml save(int p1, int p2){
		ElementXml element = super.save(p1, p2);
		element.setNom("Segment");
		return element;
	}
}
