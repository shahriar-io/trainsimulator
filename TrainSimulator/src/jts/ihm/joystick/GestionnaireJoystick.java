package jts.ihm.joystick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.log.Log;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class GestionnaireJoystick {
	
	private ConfigurationJoystick confJoystick;
	private Controller joystick;
	private ValeursJoystick valeurs;
	
	/*private boolean finInit;
	private float initX;
	private float initY;
	private float initZ;*/
	
	public GestionnaireJoystick(){
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
		} catch (LWJGLException e) {
			Log.getInstance().logInfo("Problème d'initialisation de la liste des joysticks.");
			e.printStackTrace();
		}
	}
	
	public void selectJoystick(int i){
		if(confJoystick.isUtiliserJoystick()){
			joystick = Controllers.getController(i);
			joystick.poll();
			valeurs.init(joystick.getAxisCount(), joystick.getButtonCount());
			
			Log.getInstance().logWarning(joystick.getName() + " selectionne : "
					+ joystick.getAxisCount() + " axes / " + joystick.getButtonCount() + " boutons", false);
		} else {
			Log.getInstance().logWarning("Pas de joystick sélectionné", false);
		}
	}
	
	public ConfigurationJoystick getConfJoystick(){ return this.confJoystick; }
	
	public void refreshValeurs(){
		if(confJoystick.isUtiliserJoystick()){
			joystick.poll();

			for(int i=0; i<valeurs.getNbAxes(); i++){
				valeurs.setAxe(i, joystick.getAxisValue(i));
			}

			for(int i=0; i<valeurs.getNbBoutons(); i++){
				valeurs.setBouton(i, joystick.isButtonPressed(i));
			}
		}
	}
	
	public List<String> getJoystickNames(){
		List<String> noms = new ArrayList<String>();
		int numberOfControllers = Controllers.getControllerCount();

		for (int i=0;i<numberOfControllers;i++)	{
			String nom = Controllers.getController(i).getName();
			noms.add(nom);
		}
		
		return noms;
	}
	
	public float[] getValeursVolantFrein(){
		float[] valeursVF = new float[2];
		refreshValeurs();
		valeursVF[0] = valeurs.getAction(confJoystick.commandeVolant, false);
		valeursVF[1] = valeurs.getAction(confJoystick.commandeFrein, false);
		return valeursVF;
	}
	
	/*private void checkFinInit(){
		boolean x = joystick.getXAxisValue() == initX;
		boolean y = joystick.getYAxisValue() == initY;
		boolean z = joystick.getAxisValue(2) == initZ;
		
		finInit = !(x && y && z);
	}*/
}
