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
	
	/**Joue une fr�qence sur une certaine p�riode.
	 * 
	 * @param frequence la fr�quence � jouer en Hz
	 * @param duree la dur�e pendant laquelle on doit jouer en s.
	 */
	public void jouerFrequence(double frequence, double duree);
	
	/**Joue un ensemble de fr�qences sur une certaine p�riode.
	 * 
	 * @param frequences les fr�quences � jouer en Hz
	 * @param amplitudes leurs amplitudes respectives [0;1]
	 * @param duree la dur�e pendant laquelle on doit jouer en s.
	 */
	public void jouerFrequences(double frequences[], double amplitudes[], double duree);

	/**Envoie un paquet de donn�es vers la carte son.
	 * 
	 */
	public void flush();
}
