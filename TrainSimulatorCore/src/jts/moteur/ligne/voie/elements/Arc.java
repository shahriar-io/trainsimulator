package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.Sauvegardable;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.geometrie.Vecteur;
import jts.moteur.ligne.voie.points.PointPassage;


/**Cette classe représente un élément de type arc.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Arc extends CourbeElementaire implements Sauvegardable{

	protected Point centre;
	protected double rayon;
	/**Cap depuis le nord*/
	protected double angleOrigine;
	/**Positif vers l'est*/
	protected double ouverture;
	
	public Arc(){
		super(null, null, TypeElement.ARC);
	}
	
	public Arc(PointPassage p1, PointPassage p2, Point centre, double rayon, double angleOrigine, double ouverture) {
		super(p1, p2, TypeElement.ARC);
		this.centre = centre;
		this.rayon = rayon;
		this.angleOrigine = angleOrigine;
		this.ouverture = ouverture;
		this.calculerLongueur();
	}
	
	public void calculerLongueur() {
		this.longueur = Math.abs(rayon*ouverture);
	}
	
	public void recupererAngle(AngleEuler angle, double ratio) {
		double cap = - (angleOrigine + ratio*ouverture);
		if (ouverture<0){
			cap += Math.PI;
		}
		angle.setPsi(cap);
	}
	
	public void recupererPoint(Point point, double ratio) {
		double theta = angleOrigine + ratio*ouverture;
		point.setX(centre.getX() + rayon*Math.sin(theta));
		point.setY(centre.getY() + rayon*Math.cos(theta));
	}
	
	/**Permet d'effectuer une transformation affine sur cet élément.
	 * 
	 * @param translation les coordonnées de la translation
	 * @param rotation l'angle de rotation en radians
	 */
	public void transformer(Point translation, double rotation){
		centre.transformer(translation, rotation);
		angleOrigine -= rotation;
	}
	
	public double projeter(Point point){
		Vecteur vecteur = new Vecteur(centre, point);
		double angle = BasicGeo.liPi(Math.PI/2 - vecteur.getAngle() - angleOrigine);
		double alpha = angle/ouverture;
		return alpha;
	}
	
	public double projeterAbsCurv(Point point) {
		//La création du vecteur peut consommer du temps
		Vecteur vecteur = new Vecteur(centre, point);
		double angle = BasicGeo.liPi(Math.PI/2 - vecteur.getAngle() - angleOrigine);
		double absCurv;
		if(ouverture>0){
			absCurv = angle*rayon;
		} else {
			absCurv = -angle*rayon;
		}
		return absCurv;
	}
	
	public double projeterDistance(Point point) {
		double distance = Math.abs(point.getDistance(centre) - rayon);
		return distance;
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
		dos.writeShort(TypeElement.ARC.ordinal());
		super.save(dos);
		this.centre.save(dos);
		dos.writeDouble(this.rayon);
		dos.writeDouble(this.angleOrigine);
		dos.writeDouble(this.ouverture);
	}
}
