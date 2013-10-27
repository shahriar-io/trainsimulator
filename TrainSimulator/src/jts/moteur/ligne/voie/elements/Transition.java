package jts.moteur.ligne.voie.elements;

/**Cette classe représente une transition : vers quel <code>CourbeElementaire</code> a lieu la transition
 * et si elle se fera en attaquant la <code>CourbeElementaire</code> en sens direct ou non.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Transition {

	private boolean sensDirect;
	private CourbeElementaire element;
	
	public Transition(boolean sensDirect, CourbeElementaire element){
		this.sensDirect = sensDirect;
		this.element = element;
	}
	
	public boolean isSensDirect(){ return this.sensDirect; }
	
	public CourbeElementaire getElement(){ return this.element; }
}
