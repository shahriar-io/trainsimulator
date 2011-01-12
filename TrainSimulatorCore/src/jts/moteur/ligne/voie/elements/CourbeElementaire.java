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


/**Cette classe représente un élément de base de voie ferrée. C'est un élément géométrique
 * qui peut être de plusieurs types. On doit pouvoir récupérer de manière générique
 * un certain nombre d'informations géométriques sur cet élément.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class CourbeElementaire implements Sauvegardable{

	/**Origine*/
	protected PointPassage p1;
	/**Fin*/
	protected PointPassage p2;
	protected TypeElement type;
	protected double longueur;
	
	protected CourbeElementaire(PointPassage p1, PointPassage p2, TypeElement type){
		this.p1 = p1;
		this.p2 = p2;
		this.type = type;
	}

	public PointPassage getP1() { return p1; }

	public PointPassage getP2() { return p2; }
	
	public TypeElement getType(){ return type; }
	
	public double getLongueur() { return longueur; }
	
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
	protected abstract void recupererPoint(Point point, double ratio);
	
	/**Permet de récupérer l'angle d'une voiture située à une abscisse curviligne sur cet élément.
	 * 
	 * @param angle l'angle de la voiture (maj)
	 * @param abscisse l'abscisse curviligne
	 */
	protected abstract void recupererAngle(AngleEuler angle, double ratio);
	
	/**Permet de créer un segment.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Segment createSegment(PointPassage p1, PointPassage p2){
		Segment segment = new Segment(p1, p2);
		segment.calculerLongueur();
		
		return segment;
	}
	
	@Deprecated
	public static Arc createArc(Point centre, double rayon, double angleOrigine, double ouverture){
		//On crée deux points bidons
		PointPassage p1 = new PointFrontiere();
		PointPassage p2 = new PointFrontiere();
		
		//On crée l'arc et on récupère ses origine/fin vrais
		Arc arc = new Arc(p1, p2, centre, rayon, angleOrigine, ouverture);
		arc.recupererPoint(p1, 0);
		arc.recupererPoint(p2, 1);
		arc.calculerLongueur();
		
		return arc;
	}
	
	public void load(DataInputStream dis) throws IOException {}

	public void save(DataOutputStream dos) throws IOException {}
}
