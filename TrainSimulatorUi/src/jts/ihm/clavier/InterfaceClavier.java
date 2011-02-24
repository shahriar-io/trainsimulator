package jts.ihm.clavier;

/**Cette interface décrit les services que l'on attend de l'interface clavier.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceClavier {
	
	/**Permet d'initialiser l'interface clavier;
	 * 
	 */
	public void init();

	/**Permet de récupérer les touches pressées au moment de l'appel. L'ordre est donné
	 * par l'énumération <code>ToucheClavier</code>.
	 * 
	 * @return
	 */
	public boolean[] getTouchePressee();
}
