package jts.ihm.clavier;

/**Cette interface d�crit les services que l'on attend de l'interface clavier.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceClavier {
	
	/**Permet d'initialiser l'interface clavier;
	 * 
	 */
	public void init();

	/**Permet de r�cup�rer les touches press�es au moment de l'appel. L'ordre est donn�
	 * par l'�num�ration <code>ToucheClavier</code>.
	 * 
	 * @return
	 */
	public boolean[] getTouchePressee();
}
