package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.SauvegardableBinaire;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointExtremite;


/**Cette classe représente une courbe de type arc.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Arc extends CourbeElementaire implements SauvegardableBinaire{

	protected Point centre;
	protected double rayon;
	/**Cap depuis le nord*/
	protected double angleOrigine;
	/**Positif vers l'est*/
	protected double ouverture;
	
	public Arc(){
		this(null, null, 0, null, 0, 0, 0);		
	}
	
	public Arc(PointExtremite p1, PointExtremite p2, double theta, Point centre, double rayon, double angleOrigine, double ouverture) {
		super(p1, p2, TypeElement.ARC, theta);
		this.centre = centre;
		this.rayon = rayon;
		this.angleOrigine = angleOrigine;
		this.ouverture = ouverture;
		this.theta = theta;
		this.calculerLongueur();
	}
	
	public void calculerLongueur() {
		this.longueur = Math.abs(rayon*ouverture);
	}
	
	public void recupererAngle(AngleEuler angle, double ratio) {
		super.recupererAngle(angle, ratio);
		double cap = angleOrigine + ratio*ouverture + Math.PI/2;
		if (ouverture<0){
			cap += Math.PI;
		}
		angle.setPsi(cap);
	}
	
	public void recupererPoint(Point point, double ratio) {
		double psi = angleOrigine + ratio*ouverture;
		point.setX(centre.getX() + rayon*Math.sin(psi));	//On néglige ici le cos(theta)
		point.setY(centre.getY() + rayon*Math.cos(psi));
		point.setZ(centre.getZ() + rayon*Math.sin(theta)*Math.sin(ratio*ouverture));
	}
	
	/**Permet d'effectuer une transformation affine sur cet élément.
	 * 
	 * @param translation les coordonnées de la translation
	 * @param rotation l'angle de rotation en radians
	 * @param theta angle de rotation (assiette) en radians
	 */
	public void transformer(Point translation, double rotation, double theta){
		centre.transformer(translation, rotation, theta);
		angleOrigine += rotation;
	}
	
	public List<Point> getPointsRemarquables(){
		List<Point> ptsRemarquables = new ArrayList<Point>();
		Point p3 = new Point();
		Point p4 = new Point();
		ptsRemarquables.add(p1);
		recupererPoint(p3, 0.3);
		ptsRemarquables.add(p3);
		recupererPoint(p4, 0.7);
		ptsRemarquables.add(p4);
		ptsRemarquables.add(p2);
		return ptsRemarquables;
	}
	
	public Point getCentre(){ return this.centre; }
	
	public double getRayon(){ return this.rayon; }
	
	public double getAngleOrigine(){ return this.angleOrigine; }
	
	public double getOuverture(){ return this.ouverture; }

	public void load(DataInputStream dis) throws IOException {
		super.load(dis);
		this.centre = new Point();
		this.centre.load(dis);
		this.rayon = dis.readDouble();
		this.angleOrigine = dis.readDouble();
		this.ouverture = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		super.save(dos);
		this.centre.save(dos);
		dos.writeDouble(this.rayon);
		dos.writeDouble(this.angleOrigine);
		dos.writeDouble(this.ouverture);
	}
	
	/*public void save(String indent, BufferedWriter writer, int p1, int p2) throws IOException {
		writer.write(indent + "<Arc p1=\"#" + p1 + "\" p2=\"#" + p2 + "\" rayon=\"" + rayon + "\" origine=\"" + angleOrigine + "\" ouverture=\"" + ouverture + "\">");
		writer.newLine();
		writer.write(indent + "\t<Centre x=\"" + centre.getX() + "\" y=\"" + centre.getY() + "\"/>");
		writer.newLine();
		writer.write(indent + "</Arc>");
		writer.newLine();
	}*/
	
	public ElementXml save(int p1, int p2){
		ElementXml element = super.save(p1, p2);
		element.setNom("Arc");
		element.addAttribut(new AttributXml("rayon", Double.toString(rayon)));
		element.addAttribut(new AttributXml("origine", Double.toString(angleOrigine)));
		element.addAttribut(new AttributXml("ouverture", Double.toString(ouverture)));
		ElementXml elementCentre = centre.save();
		elementCentre.setNom("Centre");
		element.addElement(elementCentre);
		return element;
	}
}
