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
	
	private boolean[] prevTouches;
	
	public MoteurPhysique(long dt){
		this.dt = dt/1000.0;
		//this.prevTouches = new boolean[ToucheClavier.values().length];
	}
	
	public void nextStep(/*boolean[] touches*/){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		//System.out.println(touches[ToucheClavier.G.ordinal()] + "/" + prevTouches[ToucheClavier.G.ordinal()]);
		/*if((touches[ToucheClavier.G.ordinal()])&&(!prevTouches[ToucheClavier.G.ordinal()])){
			trainJoueur.switchNextDivergence();
		}*/
		trainJoueur.avancer(dt);
		/*for(int i=0; i< prevTouches.length; i++){
			prevTouches[i] = touches[i];
		}*/
	}
	
	public void setDeltaCommandeVolant(float deltaCommandeVolant){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.modifierCommandeVolant(deltaCommandeVolant*dt);
	}
	
	public void setDeltaCommandeFrein(float deltaCommandeFrein){
		Train trainJoueur = this.ligne.getCircuit().getTrainJoueur();
		trainJoueur.modifierCommandeFrein(deltaCommandeFrein*dt);
	}

	public Ligne getLigne() { return ligne;	}

	public void setLigne(Ligne ligne) {	this.ligne = ligne;	}
}
