package jts.ihm.joystick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.log.Log;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

/**Cette classe gère le joystick grâce à la librairie LWJGL.
 * 
 * @author Yannick BISIAUX
 *
 */
public class GestionnaireJoysticks implements InterfaceJoystick {
	
	private ConfigurationJoystick confJoystick;
	private Controller joystick;
	private ValeursJoystick valeurs;
	private int numberOfControllers;
	
	public GestionnaireJoysticks(){
		valeurs = new ValeursJoystick();
	}
	
	public void init(){
		try {
			confJoystick = ConfigurationJoystick.readConf();
		} catch (IOException e1) {
			Log.getInstance().logInfo("Impossible de lire la configuration joystick !");
			e1.printStackTrace();
		}
		try {
			Controllers.create();
			numberOfControllers = Controllers.getControllerCount();
		} catch (LWJGLException e) {
			Log.getInstance().logInfo("Problème d'initialisation de la liste des joysticks.");
			e.printStackTrace();
		}
	}
	
	public void selectJoystick(int numeroJoystick){
		if(numeroJoystick<numberOfControllers){
			joystick = Controllers.getController(numeroJoystick);
			joystick.poll();
			valeurs.init(joystick.getAxisCount(), joystick.getButtonCount());
			joystick.poll();
			for(int i=0; i<valeurs.getNbAxes(); i++){
				valeurs.initAxe(i, joystick.getAxisValue(i));
			}

			Log.getInstance().logWarning(joystick.getName() + " selectionne : "
					+ joystick.getAxisCount() + " axes / " + joystick.getButtonCount() + " boutons", false);
		}
	}
	
	public ConfigurationJoystick getConfJoystick(){ return this.confJoystick; }
	
	public void refreshValeurs(){
		joystick.poll();

		for(int i=0; i<valeurs.getNbAxes(); i++){
			float joystickValue = joystick.getAxisValue(i);
			//Etablit une plage morte du joystick autour de 0
			if(Math.abs(joystickValue)>confJoystick.getPlageMorte()){
				valeurs.setAxe(i, joystick.getAxisValue(i));
			} else {
				valeurs.setAxe(i, 0);
			}
		}

		for(int i=0; i<valeurs.getNbBoutons(); i++){
			valeurs.setBouton(i, joystick.isButtonPressed(i));
		}
	}
	
	public List<String> getJoystickNames(){
		List<String> noms = new ArrayList<String>();

		for (int i=0;i<numberOfControllers;i++)	{
			String nom = Controllers.getController(i).getName();
			noms.add(nom);
		}
		
		return noms;
	}
	
	public ValeursJoystick getValeurs(){ return this.valeurs; }
	
	public float[] getValeursVolantFrein(){
		float[] valeursVF = new float[2];
		refreshValeurs();
		valeursVF[0] = valeurs.getAction(confJoystick.commandeVolant, false);
		valeursVF[1] = valeurs.getAction(confJoystick.commandeFrein, false);
		return valeursVF;
	}
}
