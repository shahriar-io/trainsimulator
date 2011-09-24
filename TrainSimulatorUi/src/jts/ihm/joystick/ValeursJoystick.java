package jts.ihm.joystick;


/**Cette classe représente l'ensemble des informations utiles renvoyées par le joystick.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ValeursJoystick {

	private int nbAxes;
	private float[] axes;
	private float[] axesInit;
	/**Tant que le boolean est à vrai, le joystick n'a pas été bougé.*/
	private boolean[] initValues;
	
	private int nbBoutons;
	private boolean[] boutons;
	private boolean[] boutonsPrecedents;
	
	public ValeursJoystick(){}
	
	/**Initialise la classe avec le nombre d'axes du joystick considéré ainsi que son nombre de boutons.
	 * 
	 * @param nbAxes
	 * @param nbBoutons
	 */
	public void init(int nbAxes, int nbBoutons){
		this.nbAxes = nbAxes;
		axes = new float[nbAxes];
		axesInit = new float[nbAxes];
		initValues = new boolean[nbAxes];
		for(int i=0; i<nbAxes; i++){
			axes[i] = 0;
			axesInit[i] = 0;
			initValues[i] = true;
		}
		
		this.nbBoutons = nbBoutons;
		boutons = new boolean[nbBoutons];
		boutonsPrecedents = new boolean[nbBoutons];
	}
	
	public void initAxe(int index, float valeur){
		axesInit[index] = valeur;
	}
	
	public void setAxe(int index, float valeur){
		if(!initValues[index]){
			axes[index] = valeur;
		} else {
			if(Math.abs(valeur - axesInit[index])>0.01){
				initValues[index] = false;
			}
		}
	}
	
	public void setBouton(int index, boolean appuye){
		boutonsPrecedents[index] = boutons[index];
		boutons[index] = appuye;
	}
	
	/**Renvoie l'information de valeur d'une action.
	 * 
	 * @param action
	 * @param positive indique si le résultat est entre 0 et 1 (sinon entre -1 et 1).
	 * @return
	 */
	public float getAction(ConfigurationAction action, boolean positive){
		float valeur = 0;
		float valeurBrute = axes[action.getNumero()];
		
		float a;
		if(positive){
			a = action.getMax() - action.getMin();
		} else {
			a = (action.getMax() - action.getMin())/2;
		}
		
		float b;
		if(positive){
			b = - a*action.getMin();
		} else {
			b = -1 - a*action.getMin();
		}
		
		valeur = valeurBrute*a + b;
		
		return valeur;
	}
	
	/**Renvoie l'information brute en sortie d'axe.
	 * 
	 * @param i numéro de l'axe
	 * @return
	 */
	public float getAxe(int i){ return axes[i];	}
	
	public int getNbAxes(){ return this.nbAxes; }
	
	public boolean getBouton(int i){ return boutons[i];	}
	
	public int getNbBoutons(){ return this.nbBoutons; }
	
	/**Indique si le bouton a été appuyé (mais pas maintenu) sur le cycle courant.
	 * 
	 * @param i
	 * @return
	 */
	public boolean getFrontMontant(int i){
		return (boutons[i]&&!boutonsPrecedents[i]);
	}
}
