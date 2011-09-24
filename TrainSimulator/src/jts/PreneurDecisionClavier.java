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
	private boolean[] touchesCourantes;
	
	public PreneurDecisionClavier(MoteurPhysique moteurPhysique){
		this.moteurPhysique = moteurPhysique;
		this.touchesPrecedentes = new boolean[ToucheClavier.values().length];
	}
	
	public void prendreDecisions(boolean[] touchesClavier){
		touchesCourantes = touchesClavier;
		if(touchesCourantes[ToucheClavier.D.ordinal()]){
			this.moteurPhysique.setDeltaCommandeVolant(0.5f);
		}
		if(touchesCourantes[ToucheClavier.Q.ordinal()]){
			this.moteurPhysique.setDeltaCommandeVolant(-0.5f);
		}
		if(isFrontMontant(ToucheClavier.G)){
			this.moteurPhysique.getLigne().getCircuit().getTrainJoueur().switchNextDivergence();
		}
		
		for(int i=0; i< touchesPrecedentes.length; i++){
			touchesPrecedentes[i] = touchesCourantes[i];
		}
	}
	
	private boolean isFrontMontant(ToucheClavier touche){
		return (touchesCourantes[touche.ordinal()]&&!touchesPrecedentes[touche.ordinal()]);
	}
	
	private boolean isFrontDescendant(ToucheClavier touche){
		return (!touchesCourantes[touche.ordinal()]&&touchesPrecedentes[touche.ordinal()]);
	}
}
