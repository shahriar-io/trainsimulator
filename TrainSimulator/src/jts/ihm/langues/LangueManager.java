package jts.ihm.langues;

import jts.ihm.langues.traduction.Traduction;

/**Permet de sélectionner un langue pour l'interface graphique du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface LangueManager {

	/**Permet d'initialiser le manager.
	 * 
	 */
	public void init();
	
	/**Permet d'indiquer la langue à utiliser.
	 * 
	 * @param langue
	 */
	public void setLangue(Langue langue);
	
	/**Charge en mémoire les textes correspondants à la langue sélectionnée.
	 * 
	 */
	public void chargerTraduction();
	
	/**Permet de récupérer les textes dans la langue sélectionnée.
	 * 
	 * @return <code>Traduction</code>
	 */
	public Traduction getTraduction();
}
