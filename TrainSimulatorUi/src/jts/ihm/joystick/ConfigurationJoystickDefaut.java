package jts.ihm.joystick;

/**Cette classe représente une configuration joystick par défaut, sur un joystick "standard".
 * 
 * @author Yannick BISIAUX
 *
 */
public class ConfigurationJoystickDefaut extends ConfigurationJoystick {
	
	public ConfigurationJoystickDefaut(){
		utiliserJoystick = true;
		numeroJoystick = 0;
		commandeVolant = new ConfigurationAction(0, -1, 1);
		commandeFrein = new ConfigurationAction(1, -1, 1);
	}
}
