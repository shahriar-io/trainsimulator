package jts.ihm.gui;

/**Cette interface décrit les services que l'on attend de l'interface graphique.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceGraphique {
	
	/**Permet d'initialiser l'interface graphique;
	 * 
	 */
	public void init();
	
	/**Met l'interface graphique dans l'état de jeu.
	 * 
	 */
	public void afficherEcranJeu();
}
