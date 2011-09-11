package jts.ihm;

import jts.InterfaceControleur;
import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.InterfaceJoystick;
import jts.ihm.langues.Langue;
import jts.ihm.langues.LangueManager;

/**Cette interface décrit les services que l'on attend de l'interface homme-machine.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceHommeMachine {
	
	public InterfaceControleur getControleur();

	public InterfaceGraphique getInterfaceGraphique();
	
	public InterfaceClavier getInterfaceClavier();
	
	public InterfaceJoystick getIntefaceJoystick();
	
	public LangueManager getLangueManager();
	
	/**Permet de lancer un écran pour temporiser, le temps du chargement du reste de l'application.
	 * 
	 */
	public void preinit();
	
	/**Initialise toute l'interface homme machine.
	 * 
	 */
	public void init();
	
	/**Permet de modifier la langue utilisé par le logiciel.
	 * 
	 * @param langue
	 */
	public void modifierLangue(Langue langue);
}
