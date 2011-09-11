package jts;

import java.io.File;

import jts.conf.InterfaceConfiguration;

/**Cette interface d�crit les services que l'on attend du Controleur.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceControleur {

	/**Permet de lancer l'initialisation du logiciel.
	 * 
	 */
	public void init();
	
	/**Permet de r�cup�rer la configuration du logiciel.
	 * 
	 * @return
	 */
	public InterfaceConfiguration getConfiguration();
	
	/**Lance le chargement d'une ligne et d'un sc�nario li� � cette ligne.
	 * 
	 * @param fichierLigne
	 * @param fichierScenario
	 */
	public void lancerSimu(File fichierLigne, File fichierScenario);
	
	/**R�alise une boucle de simulation avec l'appel des moteurs � appeler (physique, r�seau, ihm).
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
