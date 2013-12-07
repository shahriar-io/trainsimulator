package jts.ihm.langues;

import java.io.File;

import jts.ihm.langues.traduction.Traduction;

public class DefaultLangueManager implements LangueManager {
	
	private static File FOLDER = new File("data/langues");
	private static File DEFAULT_FILE = new File("data/langues/fr.xml");
	
	private Langue langue;
	private Traduction traduction;
	private File[] fichiersTraductions;
	
	public DefaultLangueManager(){
		
	}
	
	public void init() {
		//Par défaut, on pointe sur le fichier fr par défaut
		int nbLangues = Langue.values().length;
		fichiersTraductions = new File[nbLangues];
		for(int i=0; i<nbLangues; i++){
			fichiersTraductions[i] = DEFAULT_FILE;
		}
		
		Traduction traduction = new Traduction();
		for(File file : FOLDER.listFiles()){
			if(file.getName().endsWith(".xml")){
				traduction.parse(file, true);
				for(int i=0; i<nbLangues; i++){
					if (traduction.getLanguage().equals(Langue.values()[i].getCode())){
						fichiersTraductions[i] = file;
					}
				}
			}
			
		}
	}
	
	public void setLangue(Langue langue) {
		this.langue = langue;
	}

	/**On charge d'abord le fichier par défaut pour avoir tous les textes,
	 * eventuellement dans la version par défaut si la langue voulue est incomplète.
	 * 
	 */
	public void chargerTraduction() {
		this.traduction = new Traduction();
		this.traduction.parse(DEFAULT_FILE, false);
		this.traduction.parse(fichiersTraductions[langue.ordinal()], false);
	}

	public Traduction getTraduction() {	return traduction; }
}
