package jts.moteur.ligne.voie.elements;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.Sauvegardable;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointPassage;


/**Cette classe repr�sente un �l�ment de base de voie ferr�e. C'est un �l�ment g�om�trique
 * qui peut �tre de plusieurs types. On doit pouvoir r�cup�rer de mani�re g�n�rique
 * un certain nombre d'informations g�om�triques sur cet �l�ment.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class CourbeElementaire implements Sauvegardable{

	/**Origine*/
	protected PointPassage p1;
	protected double phi1;
	/**Fin*/
	protected PointPassage p2;
	protected double phi2;
	
	protected TypeElement type;
	/**Pente de la courbe*/
	protected double theta;
	protected double longueur;
	
	protected CourbeElementaire(PointPassage p1, PointPassage p2, double phi1, double phi2, TypeElement type, double theta){
		this.p1 = p1;
		this.p2 = p2;
		this.phi1 = phi1;
		this.phi2 = phi2;
		this.type = type;
		this.theta = theta;
	}

	public PointPassage getP1() { return p1; }

	public PointPassage getP2() { return p2; }
	
	public TypeElement getType(){ return type; }
	
	public double getLongueur() { return longueur; }
	
	/**Permet de r�cup�rer la position et l'angle d'une voiture situ�e � une abscisse curviligne sur cet �l�ment.
	 * 
	 * @param point la position de la voiture (maj)
	 * @param angle l'angle de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 * @param indique si on parcourt l'�l�ment en sens direct ou non
	 * @return true si l'abscisse correspond � une position sur cet �l�ment
	 */
	public boolean recupererPosition(Point point, AngleEuler angle, double abscisse, boolean sensDirect){
		boolean dansIntervalle;
		double ratio = abscisse/longueur;
		
		//Si on est en sens inverse, le ratio doit �tre chang� (0->1 => 1->0)
		if(!sensDirect){
			ratio = 1 - ratio;
		}
		
		if (abscisse<longueur){
			dansIntervalle = true;
			recupererPoint(point, ratio);
			recupererAngle(angle, ratio);
		} else {
			dansIntervalle = false;
		}
		
		return dansIntervalle;
	}
	
	/**Renvoie des points remarquables de cet �l�ment, au moins le d�but et la fin.
	 * 
	 * @return une liste de points
	 */
	public List<Point> getPointsRemarquables(){
		List<Point> ptsRemarquables = new ArrayList<Point>();
		ptsRemarquables.add(p1);
		ptsRemarquables.add(p2);
		return ptsRemarquables;
	}
	
	/**Permet de calculer la longueur de cet �l�ment.
	 * 
	 */
	public abstract void calculerLongueur();
	
	/**Permet de r�cup�rer la position d'une voiture situ�e � une abscisse curviligne sur cet �l�ment.
	 * 
	 * @param point la position de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 */
	protected abstract void recupererPoint(Point point, double ratio);
	
	/**Permet de r�cup�rer l'angle d'une voiture situ�e � une abscisse curviligne sur cet �l�ment.
	 * 
	 * @param angle l'angle de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 */
	public void recupererAngle(AngleEuler angle, double ratio){
		angle.setPhi((phi2 - phi1)*ratio + phi1);
		angle.setTheta(theta);
	}
	
	/**Permet de cr�er un segment.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Segment createSegment(PointPassage p1, PointPassage p2, double phi1, double phi2, double theta){
		Segment segment = new Segment(p1, p2, phi1, phi2, theta);
		segment.calculerLongueur();
		
		return segment;
	}
	
	@Deprecated
	public static Arc createArc(double phi1, double phi2, double theta, Point centre, double rayon, double angleOrigine, double ouverture){
		//On cr�e deux points bidons
		PointPassage p1 = new PointFrontiere();
		PointPassage p2 = new PointFrontiere();
		
		//On cr�e l'arc et on r�cup�re ses origine/fin vrais
		Arc arc = new Arc(p1, p2, phi1, phi2, theta, centre, rayon, angleOrigine, ouverture);
		arc.recupererPoint(p1, 0);
		arc.recupererPoint(p2, 1);
		arc.calculerLongueur();
		
		return arc;
	}
	
	public void load(DataInputStream dis) throws IOException {
		this.theta = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeDouble(this.theta);
	}
}
