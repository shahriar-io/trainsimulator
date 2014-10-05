package jts.conf;

import jts.ihm.langues.Langue;

import com.jme3.system.AppSettings;

public enum JmeRenderer {
	
	OPENGL1(AppSettings.LWJGL_OPENGL1),
	OPENGL2(AppSettings.LWJGL_OPENGL2),
	OPENGL3(AppSettings.LWJGL_OPENGL3);
	
	private String name;
	
	private JmeRenderer(String name){
		this.name = name;
	}
	
	public String getName(){ return name; }
	
	public static String[] getNoms(){
		int nbLangue = JmeRenderer.values().length;
		String noms[] = new String[nbLangue];
		for(int i=0; i<nbLangue; i++){
			noms[i] = JmeRenderer.values()[i].getName();
		}
		return noms;
	}
}
