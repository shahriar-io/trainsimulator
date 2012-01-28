package jts.ihm.joystick;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**Cette classe décrit la configuration joystick du jeu.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ConfigurationJoystick {
	public static final File FICHIER_CONFIGURATION = new File("conf/joystick.cfg");
	
	protected boolean utiliserJoystick;
	protected int numeroJoystick;
	private float plageMorte = 0.1f;
	protected ConfigurationAction commandeVolant;
	protected ConfigurationAction commandeFrein;
	
	public ConfigurationJoystick(){
		
	}
	
	public ConfigurationJoystick(boolean utiliserJoystick, int numeroJoystick, ConfigurationAction commandeVolant, 
			ConfigurationAction commandeFrein){
		this.utiliserJoystick = utiliserJoystick;
		this.numeroJoystick = numeroJoystick;
		this.commandeVolant = commandeVolant;
		this.commandeFrein = commandeFrein;
	}
	
	/**Charge une configuration joystick depuis le fichier de configuration joystick.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ConfigurationJoystick readConf() throws IOException{
		FileReader fr = new FileReader(FICHIER_CONFIGURATION);
		BufferedReader buff = new BufferedReader(fr);
		
		String utiliserStr = buff.readLine().split("=")[1];
		boolean utiliserJoystick = Boolean.parseBoolean(utiliserStr);
		
		String numeroStr = buff.readLine().split("=")[1];
		int numero = Integer.parseInt(numeroStr);
		
		ConfigurationAction commandeVolant = ConfigurationJoystick.getConfAction(buff.readLine());
		ConfigurationAction commandeFrein = ConfigurationJoystick.getConfAction(buff.readLine());
		
		buff.close();
		fr.close();
		
		return new ConfigurationJoystick(utiliserJoystick, numero, commandeVolant, commandeFrein);
	}
	
	private static ConfigurationAction getConfAction(String str){
		String[] actionStr = str.split("=")[1].split(",");
		ConfigurationAction action = new ConfigurationAction(
				Integer.parseInt(actionStr[0]),
				Float.parseFloat(actionStr[1]),
				Float.parseFloat(actionStr[2]));
		return action;
	}
	
	/**Sauvegarde cette configuration joystick dans le fichier de configuration joystick.
	 * 
	 * @throws IOException
	 */
	public void saveConf() throws IOException{
		FileWriter fw = new FileWriter(FICHIER_CONFIGURATION);
		BufferedWriter buff = new BufferedWriter(fw);
		
		buff.write("utilisrJoystick=" + utiliserJoystick);
		buff.newLine();
		buff.write("nbJoystick=" + numeroJoystick);
		buff.newLine();
		buff.write("commandeVolant=" + commandeVolant.getNumero() + "," + commandeVolant.getMin() + "," + commandeVolant.getMax());
		buff.newLine();
		buff.write("commandeFrein=" + commandeFrein.getNumero() + "," + commandeFrein.getMin() + "," + commandeFrein.getMax());
		
		buff.close();
		fw.close();
	}
	
	public boolean isUtiliserJoystick(){ return utiliserJoystick; }
	
	public void setUtiliserJoystick(boolean utiliserJoystick){ this.utiliserJoystick = utiliserJoystick; }

	public int getNumeroJoystick() { return numeroJoystick;	}
	
	public float getPlageMorte() { return plageMorte;	}

	public ConfigurationAction getVolant() { return commandeVolant;	}

	public ConfigurationAction getFrein() {	return commandeFrein; }
}
