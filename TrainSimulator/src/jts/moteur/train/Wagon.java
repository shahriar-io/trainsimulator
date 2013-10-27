package jts.moteur.train;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;

/**Cette classe représente un wagon d'un train.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Wagon {
	
	protected float longueur;
	protected float largeur;
	protected float hauteur;
	protected float empattement;
	protected float masse;
	
	protected Bogie bogieAvant;
	protected Bogie bogieArriere;
	
	protected Point position;
	protected AngleEuler orientation;
	
	private float xObservation;
	private float yObservation;
	private float zObservation;
	protected Point observation;
	
	public Wagon(float longueur, float largeur, float hauteur, float empattement, float masse){
		this.longueur = longueur;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.empattement = empattement;
		this.masse = masse;
		
		this.position = new Point();
		this.orientation = new AngleEuler();
		this.observation = new Point();
	}
	
	public void initPosition(CourbeElementaire element, double abscisse, boolean sensDirect){
		if(sensDirect){
			this.bogieAvant = new Bogie(element, abscisse + empattement/2, sensDirect);
			this.bogieArriere = new Bogie(element, abscisse - empattement/2, sensDirect);
		} else {
			this.bogieAvant = new Bogie(element, abscisse - empattement/2, sensDirect);
			this.bogieArriere = new Bogie(element, abscisse + empattement/2, sensDirect);
		}
		//On recale les bogies
		this.bogieAvant.avancer(0);
		this.bogieArriere.avancer(0);
		
		Point pAvant = bogieAvant.getPoint();
		Point pArriere = bogieArriere.getPoint();
		this.position.setBarycentre(pAvant, pArriere);
		orientation.setPsi(BasicGeo.getCap(pAvant, pArriere));
	}
	
	public boolean avancer(double ds){
		boolean changementElement;
		changementElement = this.bogieAvant.avancer(ds)[0];
		this.bogieArriere.avancer(ds);
		this.position.setBarycentre(bogieAvant.getPoint(), bogieArriere.getPoint());
		
		Point pAvant = bogieAvant.getPoint();
		Point pArriere = bogieArriere.getPoint();
		this.position.setBarycentre(pAvant, pArriere);
		orientation.setPsi(BasicGeo.getCap(pAvant, pArriere));
		orientation.setPhi(0.5*(bogieAvant.getAngle().getPhi()+bogieArriere.getAngle().getPhi()));
		
		observation.setXYZ(xObservation, yObservation, zObservation);
		observation.transformer(position, orientation.getPsi(), orientation.getTheta());
		
		return changementElement;
	}
	
	public float getLongueur(){ return this.longueur; }
	
	public float getLargeur(){ return this.largeur; }
	
	public float getHauteur(){ return this.hauteur; }
	
	public float getMasse(){ return this.masse; }
	
	public Point getPosition(){ return this.position; }
	
	public void setOrientation(float x, float y, float z){
		this.xObservation = x;
		this.yObservation = y;
		this.zObservation = z;
	}
	
	public AngleEuler getOrientation(){ return this.orientation; }
	
	public Bogie getBogieAvant(){ return this.bogieAvant; }
	
	public Bogie getBogieArriere(){ return this.bogieArriere; }
	
	public Point getObservation(){ return this.observation; }
}
