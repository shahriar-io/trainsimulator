package jts.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**Cette interface d�finit les services rendus par une classe sauvegardable sous forme binaire.
 * Un �l�ment sauvegardable doit avoir un constructeur par d�faut.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface SauvegardableBinaire {

	/**Permet de charger les attributs d'un �l�ment*/
	public void load(DataInputStream dis) throws IOException;
	
	/**Permet de sauvegarder un �l�ment*/
	public void save(DataOutputStream dos) throws IOException;
}
