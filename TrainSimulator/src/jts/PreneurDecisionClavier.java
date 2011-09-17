package jts;

import jts.ihm.clavier.ToucheClavier;
import jts.moteur.MoteurPhysique;

/**Cette classe permet de générer des évènements dans la simulation en fonction des entrées clavier.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PreneurDecisionClavier {

	private MoteurPhysique moteurPhysique;
	private boolean[] touchesPrecedentes;
	
	public PreneurDecisionClavier(MoteurPhysique moteurPhysique){
		this.moteurPhysique = moteurPhysique;
		this.touchesPrecedentes = new boolean[ToucheClavier.values().length];
	}
	
	public void prendreDecisions(boolean[] touchesClavier){
		if(touchesClavier[ToucheClavier.D.ordinal()]){
			this.moteurPhysique.setDeltaCommandeVolant(0.5f);
		}
		if(touchesClavier[ToucheClavier.Q.ordinal()]){
			this.moteurPhysique.setDeltaCommandeVolant(-0.5f);
		}
		if(touchesClavier[ToucheClavier.G.ordinal()]&&!touchesPrecedentes[ToucheClavier.G.ordinal()]){
			this.moteurPhysique.getLigne().getCircuit().getTrainJoueur().switchNextDivergence();
		}
		
		for(int i=0; i< touchesPrecedentes.length; i++){
			touchesPrecedentes[i] = touchesClavier[i];
		}
	}
}
