package jts.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**Cette interface définit les services rendus par une classe sauvegardable.
 * Un élément sauvegardable doit avoir un constructeur par défaut.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface Sauvegardable {

	/**Permet de charger les attributs d'un élément*/
	public void load(DataInputStream dis) throws IOException;
	
	/**Permet de sauvegarder un élément*/
	public void save(DataOutputStream dos) throws IOException;
}
