package jts.moteur.ligne;

import java.util.ArrayList;
import java.util.List;

/**Cette classe représente une ligne ferroviaire parcourable dans la simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ligne {

	private CircuitSections circuit;
	private List<ObjetScene> objets;
	
	public Ligne(){
		this.objets = new ArrayList<ObjetScene>();
	}
	
	public CircuitSections getCircuit(){ return this.circuit; }
	
	public void setCircuit(CircuitSections circuit){ this.circuit = circuit; }
	
	public List<ObjetScene> getObjets(){ return this.objets; }
	
	public void addObjet(ObjetScene objet){ this.objets.add(objet); }
}
