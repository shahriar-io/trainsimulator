package jts.conf;

import java.io.File;
import java.io.IOException;

/**Cette interface décrit les renseignements qu'on s'attend à trouver dans la configuration du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceConfiguration {
	
	/**Indique si le logiciel est utilisé pour la première fois.
	 * 
	 * @return
	 */
	public boolean isFirstUse();
	
	/**Permet de forcer l'état "première utilisation" du logiciel.
	 * 
	 * @param firstUse
	 */
	public void setFirstUse(boolean firstUse);
	
	/**Donne le code de la langue choisie.
	 * 
	 * @return
	 */
	public String getLangueCode();
	
	/**Permet d'indiquer le code de la alngue utilisé par le logiciel.
	 * 
	 * @param code
	 */
	public void setLangueCode(String code);
	
	/**Renvoie la partie de la configuration liée au joystick.
	 * 
	 * @return
	 */
	public ConfigurationJoystick getConfigurationJoystick();
	
	public ConfigurationGraphique getConfigurationGraphique();
	
	/**Charge le fichier de configuration correspondant.
	 * 
	 * @param file
	 */
	public void load(File file) throws IOException;
	
	/**Sauvegarde la configuration dans le fichier correspondant.
	 * 
	 * @param file
	 */
	public void save(File file) throws IOException;
}
