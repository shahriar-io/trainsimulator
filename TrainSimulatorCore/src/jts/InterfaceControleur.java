package jts;

import java.io.File;

import jts.conf.InterfaceConfiguration;

/**Cette interface décrit les services que l'on attend du Controleur.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceControleur {

	/**Permet de lancer l'initialisation du logiciel.
	 * 
	 */
	public void init();
	
	/**Permet de récupérer la configuration du logiciel.
	 * 
	 * @return
	 */
	public InterfaceConfiguration getConfiguration();
	
	/**Lance le chargement d'une ligne et d'un scénario lié à cette ligne.
	 * 
	 * @param fichierLigne
	 * @param fichierScenario
	 */
	public void lancerSimu(File fichierLigne, File fichierScenario);
	
	/**Réalise une boucle de simulation avec l'appel des moteurs à appeler (physique, réseau, ihm).
	 * 
	 */
	public void boucler();
	
	/**Sauvegarde la configuration voulue par l'utilisateur.
	 * 
	 */
	public void sauvegarderConfiguration();
	
	/**Permet de quitter le logiciel.
	 * 
	 */
	public void quitter();
}
