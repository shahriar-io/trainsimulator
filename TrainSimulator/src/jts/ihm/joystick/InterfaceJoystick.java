package jts.ihm.joystick;

import java.util.List;

/**Cette interface d�crit les services que l'on attend de l'interface joystick.
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
	
	/**S�lectionne l'utilisation du joystick num�ro 1.
	 * 
	 * @param i le num�ro du joystick
	 */
	public void selectJoystick(int i);
	
	/**Rafra�chit les valeurs connues de la position du joystick.
	 * 
	 */
	public void refreshValeurs();
	
	/**Renvoie les valeurs courantes du joystick.
	 * 
	 * @return
	 */
	public ValeursJoystick getValeurs();
}
