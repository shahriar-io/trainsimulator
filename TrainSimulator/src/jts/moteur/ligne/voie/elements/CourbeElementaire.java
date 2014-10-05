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
import jts.moteur.ligne.voie.points.PointFrontiere;


/**Cette classe représente un élément de base de voie ferrée. C'est un élément géométrique
 * qui peut être de plusieurs types. On doit pouvoir récupérer de manière générique
 * un certain nombre d'informations géométriques sur cet élément.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class CourbeElementaire implements SauvegardableBinaire{

	/**Origine*/
	protected PointExtremite p1;
	protected boolean inversionPhi1;
	/**Fin*/
	protected PointExtremite p2;
	protected boolean inversionPhi2;
	protected TypeElement type;
	/**Pente de la courbe*/
	protected double theta;
	protected double longueur;
	
	protected CourbeElementaire(PointExtremite p1, PointExtremite p2, TypeElement type, double theta){
		this.p1 = p1;
		this.p2 = p2;
		this.type = type;
		this.theta = theta;
	}
	
	protected CourbeElementaire(PointExtremite p1, PointExtremite p2, boolean inversionPhi1, boolean inversionPhi2, TypeElement type, double theta){
		this(p1, p2, type, theta);
		this.inversionPhi1 = inversionPhi1;
		this.inversionPhi2 = inversionPhi2;
	}

	public PointExtremite getP1() { return p1; }

	public PointExtremite getP2() { return p2; }
	
	public void setP1(PointExtremite p1) { this.p1 = p1; }

	public void setP2(PointExtremite p2) { this.p2 = p2; }
	
	public void inverserPhi1() { this.inversionPhi1 = true; }
	
	public void inverserPhi2() { this.inversionPhi2 = true; }
	
	public void replace(PointExtremite pOld, PointExtremite pNew){
		if(this.p1.equals(pOld)){
			this.p1 = pNew;
		} else if(this.p2.equals(pOld)) {
			this.p2 = pNew;
		}
	}
	
	public TypeElement getType(){ return type; }
	
	public double getTheta() { return theta; }
	
	public double getLongueur() { return longueur; }
	
	/**Permet d'effectuer une transformation affine sur cet élément.
	 * 
	 * @param translation les coordonnées de la translation
	 * @param rotation l'angle de rotation en radians
	 * @param theta angle de rotation (assiette) en radians
	 */
	public void transformer(Point translation, double rotation, double theta){}
	
	/**Permet de récupérer la position et l'angle d'une voiture située à une abscisse curviligne sur cet élément.
	 * 
	 * @param point la position de la voiture (maj)
	 * @param angle l'angle de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 * @param indique si on parcourt l'élément en sens direct ou non
	 * @return true si l'abscisse correspond à une position sur cet élément
	 */
	public boolean recupererPosition(Point point, AngleEuler angle, double abscisse, boolean sensDirect){
		boolean dansIntervalle;
		double ratio = abscisse/longueur;
		
		//Si on est en sens inverse, le ratio doit être changé (0->1 => 1->0)
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
		
		//En sens non direct, les angles changent de signe
		if(!sensDirect){
			angle.opposer();
		}
		
		return dansIntervalle;
	}
	
	/**Renvoie des points remarquables de cet élément, au moins le début et la fin.
	 * 
	 * @return une liste de points
	 */
	public List<Point> getPointsRemarquables(){
		List<Point> ptsRemarquables = new ArrayList<Point>();
		ptsRemarquables.add(p1);
		ptsRemarquables.add(p2);
		return ptsRemarquables;
	}
	
	/**Permet de calculer la longueur de cet élément.
	 * 
	 */
	public abstract void calculerLongueur();
	
	/**Permet de récupérer la position d'une voiture située à une abscisse curviligne sur cet élément.
	 * 
	 * @param point la position de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 */
	public abstract void recupererPoint(Point point, double ratio);
	
	/**Permet de récupérer l'angle d'une voiture située à une abscisse curviligne sur cet élément.
	 * 
	 * @param angle l'angle de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 */
	public void recupererAngle(AngleEuler angle, double ratio){
		//Si nécessaire on échange le signe de phis stocké dans les points extrémités. On calcule ensuite le phi normalement.
		double phi1 = inversionPhi1?-p1.getPhi():p1.getPhi();
		double phi2 = inversionPhi2?-p2.getPhi():p2.getPhi();
		angle.setPhi((phi2 - phi1)*ratio + phi1);
		angle.setTheta(theta);
	}
	
	/**Permet de créer un segment.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	@Deprecated
	public static Segment createSegment(PointExtremite p1, PointExtremite p2){
		double theta = Math.asin((p2.getZ() - p1.getZ())/p1.getDistance(p2));
		Segment segment = new Segment(p1, p2, theta);
		segment.calculerLongueur();
		
		return segment;
	}
	
	@Deprecated
	public static Arc createArc(double theta, Point centre, double rayon, double angleOrigine, double ouverture){
		//On crée deux points bidons
		PointExtremite p1 = new PointFrontiere();
		PointExtremite p2 = new PointFrontiere();
		
		//On crée l'arc et on récupère ses origine/fin vrais
		Arc arc = new Arc(p1, p2, theta, centre, rayon, angleOrigine, ouverture);
		arc.recupererPoint(p1, 0);
		arc.recupererPoint(p2, 1);
		arc.calculerLongueur();
		
		return arc;
	}
	
	public void load(DataInputStream dis) throws IOException {
		this.theta = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeShort(type.ordinal());
		dos.writeDouble(this.theta);
	}
	
	//public abstract void save(String indent, BufferedWriter writer, int p1, int p2) throws IOException;
	
	public ElementXml save(int p1, int p2){
		ElementXml element = new ElementXml("CourbeElementaire");
		element.addAttribut(new AttributXml("p1", "#" + p1));
		element.addAttribut(new AttributXml("p2", "#" + p2));
		element.addAttribut(new AttributXml("inversionPhi1", Boolean.toString(inversionPhi1)));
		element.addAttribut(new AttributXml("inversionPhi2", Boolean.toString(inversionPhi2)));
		return element;
	}
}
