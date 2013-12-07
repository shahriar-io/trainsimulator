package jts.ihm.audio;

/**Cette classe liste les services rendus par l'interface audio.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceAudio {
	
	/**Initialise l'interface audio.
	 * 
	 * @param duree la durée d'un pas de temps en s.
	 */
	public void init(double duree);
	
	/**Joue la note La (440 Hz) pendant une seconde.
	 * 
	 */
	public void jouerLa();
	
	/**Joue une fréqence sur une certaine période.
	 * 
	 * @param frequence la fréquence à jouer en Hz
	 */
	public void jouerFrequence(double frequence);
	
	/**Joue un ensemble de fréqences sur une certaine période.
	 * 
	 * @param frequences les fréquences à jouer en Hz
	 * @param amplitudes leurs amplitudes respectives [0;1]
	 */
	public void jouerFrequences(double frequences[], double amplitudes[]);
	
	/**Joue un son sur la duréee définie précédemment. Modifie la phase des fréquances du son
	 * par celle de la sinusoïde correspondante en fin d'écriture.
	 * 
	 * @param son un <code>Son</code> à jouer
	 */
	public void jouerSon(Son son);

	/**Envoie un paquet de données vers la carte son.
	 * 
	 */
	public void flush();
}
