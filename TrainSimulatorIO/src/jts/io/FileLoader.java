package jts.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

	private final static File DOSSIER_LIGNES = new File("data/lignes");
	public final static File DOSSIER_SCENARIOS = new File("data/scenarios");
	
	private List<String> nomsLignes;
	private List<File> fichiersLignes;
	private List<String> nomsScenarios;
	private List<File> fichiersScenarios;
	
	public FileLoader(){
		nomsLignes = new ArrayList<String>();
		fichiersLignes = new ArrayList<File>();
		nomsScenarios = new ArrayList<String>();
		fichiersScenarios = new ArrayList<File>();
	}
	
	public void chercherNomLignes(){
		File[] fichiers = DOSSIER_LIGNES.listFiles();
		for(File fichier : fichiers){
			if(fichier.getName().endsWith(".xml")){
				fichiersLignes.add(fichier);
				nomsLignes.add(LigneLoader.getNomLigne(fichier));
			}
		}
	}
	
	public void chercherNomScenarios(String nomFichierLigne){
		fichiersScenarios.clear();
		nomsScenarios.clear();
		File[] fichiers = DOSSIER_SCENARIOS.listFiles();
		for(File fichier : fichiers){
			if(fichier.getName().endsWith(".xml")){
				String noms[] = ScenarioLoader.getNomScenario(fichier);
				if(noms[1].equals(nomFichierLigne)){
					fichiersScenarios.add(fichier);
					nomsScenarios.add(noms[0]);
				}
			}
		}
	}

	public List<String> getNomsLignes() { return nomsLignes; }
	
	public List<File> getFichiersLignes() { return fichiersLignes; }

	public List<String> getNomsScenarios() { return nomsScenarios; }
	
	public List<File> getFichiersScenarios() { return fichiersScenarios; }
}
