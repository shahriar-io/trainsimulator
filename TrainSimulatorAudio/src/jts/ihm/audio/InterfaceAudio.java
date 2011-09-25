package jts.ihm.audio;

/**Cette classe liste les services rendus par l'interface audio.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface InterfaceAudio {
	
	public void init();
	
	/**Joue la note La (440 Hz) pendant une seconde.
	 * 
	 */
	public void jouerLa();
	
	/**Joue une fréqence sur une certaine période.
	 * 
	 * @param frequence la fréquence à jouer en Hz
	 * @param duree la durée pendant laquelle on doit jouer en s.
	 */
	public void jouerFrequence(double frequence, double duree);
	
	/**Joue un ensemble de fréqences sur une certaine période.
	 * 
	 * @param frequences les fréquences à jouer en Hz
	 * @param amplitudes leurs amplitudes respectives [0;1]
	 * @param duree la durée pendant laquelle on doit jouer en s.
	 */
	public void jouerFrequences(double frequences[], double amplitudes[], double duree);

	/**Envoie un paquet de données vers la carte son.
	 * 
	 */
	public void flush();
}
