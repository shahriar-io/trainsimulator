package jts.moteur.ligne.voie.elements;

/**Cette classe représente une transition : vers quel <code>LigneElementaire</code> a lieu la transition
 * et si elle se fera en sens direct ou non.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Transition {

	public boolean sensDirect;
	public CourbeElementaire element;
	
	public Transition(boolean sensDirect, CourbeElementaire element){
		this.sensDirect = sensDirect;
		this.element = element;
	}
}
