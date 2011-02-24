package jts.ihm;

import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.InterfaceJoystick;

/**Cette interface décrit les services que l'on attend de l'interface homme-machine.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceHommeMachine {

	public InterfaceGraphique getInterfaceGraphique();
	
	public InterfaceClavier getInterfaceClavier();
	
	public InterfaceJoystick getIntefaceJoystick();
	
	/**Permet de lancer un écran pour temporiser, le temps du chargement du reste de l'application.
	 * 
	 */
	public void preinit();
	
	/**Initialise toute l'interface homme machine.
	 * 
	 */
	public void init();
}
