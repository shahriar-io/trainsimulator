package jts.ihm.langues;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**Langues disponibles dans le logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public enum Langue {

	FRANCAIS("fr", "français"),
	ESPERANTO("eo", "esperanto"),
	ANGLAIS("en", "english"),
	ALLEMAND("de", "deutsch");
	
	private String code;
	private String nom;
	
	private Langue(String code, String nom){
		this.code = code;
		this.nom = nom;
	}

	public String getCode() { return code; }

	public String getNom() { return nom; }
	
	public static String[] getNoms(){
		int nbLangue = Langue.values().length;
		String noms[] = new String[nbLangue];
		for(int i=0; i<nbLangue; i++){
			noms[i] = Langue.values()[i].getNom();
		}
		return noms;
	}

	private static final Map<String,Langue> codeReverse = new HashMap<String,Langue>();

	static {
		for(Langue langue : EnumSet.allOf(Langue.class))
			codeReverse.put(langue.getCode(), langue);
	}
	
	private static final Map<Langue,Integer> ordinalReverse = new HashMap<Langue,Integer>();

	static {
		for(Langue langue : EnumSet.allOf(Langue.class))
			ordinalReverse.put(langue, langue.ordinal());
	}
	
	public static Langue getLangueFromCode(String code){
		return codeReverse.get(code);
	}
	
	public static int getOrdinalFromLangue(Langue langue){
		return ordinalReverse.get(langue);
	}
}
