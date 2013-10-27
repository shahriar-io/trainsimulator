package jts.ihm.langues;

import jts.ihm.langues.traduction.Traduction;

/**Permet de s�lectionner un langue pour l'interface graphique du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface LangueManager {

	/**Permet d'initialiser le manager.
	 * 
	 */
	public void init();
	
	/**Permet d'indiquer la langue � utiliser.
	 * 
	 * @param langue
	 */
	public void setLangue(Langue langue);
	
	/**Charge en m�moire les textes correspondants � la langue s�lectionn�e.
	 * 
	 */
	public void chargerTraduction();
	
	/**Permet de r�cup�rer les textes dans la langue s�lectionn�e.
	 * 
	 * @return <code>Traduction</code>
	 */
	public Traduction getTraduction();
}
