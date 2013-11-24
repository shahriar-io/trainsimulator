package jts.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.CircuitSections;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.points.PointExtremite;

public class ScriptLgnReader {

	public static CircuitSections load(File file){
		CircuitSections circuit = new CircuitSections();
		
		try {
			FileReader fReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fReader);
			
			String line = reader.readLine();
			
			while(line != null){
				String ligneNonCommentee = line.split("#")[0];
				
				if(ligneNonCommentee.startsWith("addSection")){
					int longueurLigne = ligneNonCommentee.length();
					String argLine = ligneNonCommentee.substring(11, longueurLigne-1);
					String[] args = argLine.split(",");
					if(args.length == 6){
						String type = args[0].trim();
						double x = Double.parseDouble(args[1].trim());
						double y = Double.parseDouble(args[2].trim());
						double z = Double.parseDouble(args[3].trim());
						double theta = Double.parseDouble(args[4].trim());
						double psi = Double.parseDouble(args[5].trim());
						addSection(circuit, type, x, y, z, theta, psi);
					} else if(args.length == 5){
						String type = args[0].trim();
						int numSection = Integer.parseInt(args[1].trim());
						int numPtAutreSection = Integer.parseInt(args[2].trim());
						int numPtNewSection = Integer.parseInt(args[3].trim());
						double theta = Double.parseDouble(args[4].trim());
						
						Section sectionRef = circuit.getSections().get(numSection-1);
						addSection(circuit, type, sectionRef, numPtAutreSection, numPtNewSection, theta);
					} else {
						System.out.println("Nombre d'arguments incorrect pour la fonction addSection");
					}
				} else {
					System.out.println("Ligne non interprétée : " + line);
				}
				
				line = reader.readLine();
			}
			
			reader.close();
			fReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return circuit;
	}
	
	public static Section addSection(CircuitSections circuit, String type, Section sectionRef, int numPtSectionRef, int numPtSectionNew) {
		return addSection(circuit, type, sectionRef, numPtSectionRef, numPtSectionNew, 0);
	}

	public static Section addSection(CircuitSections circuit, String type, Section sectionRef, int numPtSectionRef, int numPtSectionNew, double theta) {
		PointExtremite ptRef = sectionRef.getPointsExtremites().get(numPtSectionRef-1);
		boolean sensDirectRef = ptRef.getElementBase().getP1().equals(ptRef);
		
		Point pointRef = new Point();
		AngleEuler angleRef = new AngleEuler();
		ptRef.getElementBase().recupererPosition(pointRef, angleRef, 0, sensDirectRef);
		angleRef.opposer();
		
		Section newSection = SectionLoader.load(new File("data/sections/" + type + ".xml"), new Point(), new AngleEuler());
		PointExtremite ptNew = newSection.getPointsExtremites().get(numPtSectionNew-1);
		boolean sensDirectNew = ptNew.getElementBase().getP1().equals(ptNew);
		Point pointNew = new Point();
		AngleEuler angleNew = new AngleEuler();
		ptNew.getElementBase().recupererPosition(pointNew, angleNew, 0, sensDirectNew);
		angleNew.opposer();
		
		//System.out.println("Sens direct ref : " + sensDirectRef);
		//System.out.println("Angle ref : " + BasicConvert.radToDeg(BasicGeo.li2Pi(angleRef.getPsi())) + "°");
		//System.out.println("Sens direct new : " + sensDirectNew);
		//System.out.println("Angle new : " + BasicConvert.radToDeg(BasicGeo.li2Pi(angleNew.getPsi())) + "°");
		double deltaAngle = angleRef.getPsi() - (angleNew.getPsi() - Math.PI);
		newSection.getAngle().setPsi(deltaAngle);
		newSection.getAngle().setTheta(theta);
		newSection.rendreAbsolu();
		Point deltaPoint = new Point(
				ptRef.getX() - ptNew.getX(),
				ptRef.getY() - ptNew.getY(),
				ptRef.getZ() - ptNew.getZ());
		System.out.println("Ajout " + type + " " + deltaPoint + " cap " + deltaAngle);
		
		newSection = SectionLoader.load(new File("data/sections/" + type + ".xml"), deltaPoint, new AngleEuler());
		newSection.getAngle().setPsi(deltaAngle);
		newSection.getAngle().setTheta(theta);
		circuit.addSection(newSection);
		newSection.rendreAbsolu();
		circuit.essayerLienBidirectionnel(sectionRef, newSection);
		return newSection;
	}

	public static Section addSection(CircuitSections circuit, String type, double x, double y, double z, double theta, double psi) {
		Point point = new Point(x, y, z);
		AngleEuler angle = new AngleEuler(theta*Math.PI/180, 0, psi*Math.PI/180);
		Section section = SectionLoader.load(new File("data/sections/" + type + ".xml"), point, angle);
		section.rendreAbsolu();
		circuit.addSection(section);
		return section;
	}
}
