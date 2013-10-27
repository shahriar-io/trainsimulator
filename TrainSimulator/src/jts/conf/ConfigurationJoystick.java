package jts.conf;

import java.io.BufferedWriter;
import java.io.IOException;

import org.w3c.dom.Element;

/**Cette classe représente la configuration du joystick.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ConfigurationJoystick {

	private boolean useJoystick;
	private int numeroJoystick;
	
	public ConfigurationJoystick(){
		
	}

	public boolean isUseJoystick() { return useJoystick; }

	public void setUseJoystick(boolean useJoystick) { this.useJoystick = useJoystick; }

	public int getNumeroJoystick() { return numeroJoystick; }

	public void setNumeroJoystick(int numeroJoystick) { this.numeroJoystick = numeroJoystick; }
	
	public void load(Element element) {
		useJoystick = Boolean.parseBoolean(element.getAttribute("useJoystick"));
		numeroJoystick = Integer.parseInt(element.getAttribute("numeroJoystick"));
	}
	
	public void save(BufferedWriter buffer, String indent) throws IOException {
		buffer.write(indent + "<Joystick useJoystick=\"" + useJoystick + "\" numeroJoystick=\"" + numeroJoystick + "\"/>");
		buffer.newLine();
	}
}
