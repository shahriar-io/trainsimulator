package jts.moteur.ligne;

/**Cette classe représente une ligne ferroviaire parcourable dans la simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ligne {

	private CircuitSections circuit;
	
	public Ligne(){
		
	}
	
	public CircuitSections getCircuit(){ return this.circuit; }
	
	public void setCircuit(CircuitSections circuit){ this.circuit = circuit; }
}
