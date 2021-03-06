package jts.moteur;

import jts.moteur.ligne.Ligne;
import jts.moteur.train.Train;

/**Cette classe représente le moteur physique de la simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class MoteurPhysique {
	
	/**Delta T en secondes*/
	private double dt;

	private Ligne ligne;
	
	public MoteurPhysique(long dt){
		this.dt = dt/1000.0;
		//this.prevTouches = new boolean[ToucheClavier.values().length];
	}
	
	public void nextStep(/*boolean[] touches*/){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.avancer(dt);
	}
	
	public void setDeltaCommandeVolant(double deltaCommandeVolant){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.modifierCommandeVolant(deltaCommandeVolant*dt*2);
	}
	
	public void setDeltaCommandeFrein(double deltaCommandeFrein){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.modifierCommandeFrein(deltaCommandeFrein*dt);
	}
	
	public void changerProchainAiguillage(){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.switchNextDivergence();
	}

	public Ligne getLigne() { return ligne;	}

	public void setLigne(Ligne ligne) {	this.ligne = ligne;	}
}
