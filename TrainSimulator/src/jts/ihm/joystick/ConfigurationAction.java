package jts.ihm.joystick;

/**Cette classe permet de configurer une action sur un axe par le numéro d'axe,
 * les valeurs minimum et maximum attendues sur cet axe.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ConfigurationAction {

	/**Numéro de l'axe joystick*/
	private int numero;
	/**Valeur renvoyée par le joystick correspondant au minimum de l'action*/
	private float min;
	/**Valeur renvoyée par le joystick correspondant au maximum de l'action*/
	private float max;
	
	public ConfigurationAction(int numero, float min, float max) {
		this.numero = numero;
		this.min = min;
		this.max = max;
	}

	public int getNumero() {
		return numero;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}
}
