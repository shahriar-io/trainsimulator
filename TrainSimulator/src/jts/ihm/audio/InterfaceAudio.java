package jts.ihm.audio;

/**Cette classe liste les services rendus par l'interface audio.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceAudio {
	
	/**Initialise l'interface audio.
	 * 
	 * @param duree la dur�e d'un pas de temps en s.
	 */
	public void init(double duree);
	
	/**Joue la note La (440 Hz) pendant une seconde.
	 * 
	 */
	public void jouerLa();
	
	/**Joue une fr�qence sur une certaine p�riode.
	 * 
	 * @param frequence la fr�quence � jouer en Hz
	 */
	public void jouerFrequence(double frequence);
	
	/**Joue un ensemble de fr�qences sur une certaine p�riode.
	 * 
	 * @param frequences les fr�quences � jouer en Hz
	 * @param amplitudes leurs amplitudes respectives [0;1]
	 */
	public void jouerFrequences(double frequences[], double amplitudes[]);
	
	/**Joue un son sur la dur�ee d�finie pr�c�demment. Modifie la phase des fr�quances du son
	 * par celle de la sinuso�de correspondante en fin d'�criture.
	 * 
	 * @param son un <code>Son</code> � jouer
	 */
	public void jouerSon(Son son);

	/**Envoie un paquet de donn�es vers la carte son.
	 * 
	 */
	public void flush();
}
