package jts.ihm.joystick;

import java.util.List;

/**Cette interface décrit les services que l'on attend de l'interface joystick.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceJoystick {

	/**Permet d'initialiser l'interface joystick;
	 * 
	 */
	public void init();
	
	/**Renvoie la liste des noms des joysticks disponibles.
	 * 
	 * @return
	 */
	public List<String> getJoystickNames();
}
