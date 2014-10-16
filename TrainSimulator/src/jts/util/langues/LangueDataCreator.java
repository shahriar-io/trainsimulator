package jts.util.langues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.Controleur;
import jts.ihm.langues.Langue;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;

public class LangueDataCreator {

	public static void parseCsv(String dossierLangue, File fichierLangue, boolean createJavaFiles){
		try {
			FileReader fr = new FileReader(fichierLangue);
			BufferedReader reader = new BufferedReader(fr);
			
			parseLines(reader, dossierLangue, createJavaFiles);
			
			reader.close();
			fr.close();
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + fichierLangue.toString() + " : " + e.getMessage());
		}
	}

	private static void parseLines(BufferedReader reader, String dossierLangue, boolean createJavaFiles) throws IOException {
		List<String> codesLangue = new ArrayList<String>();
		List<LangueAdapter> adapters = new ArrayList<LangueAdapter>();
		List<ElementXml> langues = new ArrayList<ElementXml>();
		List<ElementXml> panels = new ArrayList<ElementXml>();
		
		String ligne = reader.readLine();
		String[] splits = ligne.split(";");
		for(int i=1; i<splits.length; i++){
			String code = splits[i];
			codesLangue.add(code);
			if(code.equals(Langue.ESPERANTO.getCode())){
				adapters.add(new EsperantoAdapter());
			} else {
				adapters.add(null);
			}
			ElementXml element = new ElementXml("Traduction");
			element.addAttribut(new AttributXml("language", splits[i]));
			langues.add(element);
		}
		ligne = reader.readLine();
		
		while(ligne!=null){
			if(!ligne.trim().equals("")){
				splits = ligne.split(";");
				if(splits.length<=1||splits[1].trim().equals("")){
					panels.clear();
					for(int i=0; i<codesLangue.size(); i++){
						ElementXml element = new ElementXml(splits[0]);
						panels.add(element);
						langues.get(i).addElement(element);
					}
				} else {
					for(int i=1; i<splits.length; i++){
						if(!splits[i].trim().equals("")){
							String traduction = splits[i];
							if(adapters.get(i-1)!=null){
								traduction = adapters.get(i-1).adapt(traduction);
							}
							panels.get(i-1).addAttribut(new AttributXml(splits[0], traduction));
						}
					}
				}
				ligne = reader.readLine();
			}
		}
		
		for(int i=0; i<langues.size(); i++){
			FileWriter fw = new FileWriter(new File(dossierLangue + "/" + codesLangue.get(i) + ".xml"));
			BufferedWriter writer = new BufferedWriter(fw);
			
			writer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			writer.newLine();
			langues.get(i).write("", writer);
			
			writer.close();
			fw.close();
		}
		
		if(createJavaFiles){
			for(ElementXml traduction : langues.get(0).getElements()){
				FileWriter fw = new FileWriter(new File("Traduction" + traduction.getNom() + ".java"));
				BufferedWriter writer = new BufferedWriter(fw);
				
				writer.write("package jts.ihm.langues.traduction;");
				writer.newLine();
				writer.newLine();
				writer.write("import org.w3c.dom.Element;");
				writer.newLine();
				writer.newLine();
				writer.write("public class Traduction" + traduction.getNom() + " {");
				writer.newLine();
				writer.write("\t");
				writer.newLine();
				
				for(AttributXml attribut : traduction.getAttributs()){
					writer.write("\tprivate String " + attribut.getName() + ";");
					writer.newLine();
				}
				writer.write("\t");
				writer.newLine();
				
				writer.write("\tpublic Traduction" + traduction.getNom() + "(){");
				writer.newLine();
				writer.write("\t\t");
				writer.newLine();
				writer.write("\t}");
				writer.newLine();
				writer.write("\t");
				writer.newLine();
				
				writer.write("\tpublic void parse(Element element){");
				writer.newLine();
				for(AttributXml attribut : traduction.getAttributs()){
					writer.write("\t\t" + attribut.getName() + " = Traduction.tryToLoad(" + attribut.getName() + ", element, \"" + attribut.getName() + "\");");
					writer.newLine();
				}
				writer.write("\t}");
				writer.newLine();
				
				for(AttributXml attribut : traduction.getAttributs()){
					String attributName = attribut.getName().substring(0, 1).toUpperCase() + attribut.getName().substring(1);
					writer.write("\t");
					writer.newLine();
					writer.write("\tpublic String get" + attributName + "(){ return this." + attribut.getName() + "; }");
					writer.newLine();
				}
				
				writer.write("}");
				writer.newLine();
				
				writer.close();
				fw.close();
			}
		}
	}
	
	public static void main(String[] args){
		LangueDataCreator.parseCsv("data/langues_temp", new File("data/langues.csv"), true);
	}
}
