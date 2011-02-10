package jts.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

public class CoordConverter {

	private int pixXMin;
	private int pixXMax;
	private int pixYMin;
	private int pixYMax;
	
	private float xMin;
	private float xMax;
	private float yMin;
	private float yMax;
	
	private DecimalFormat DF = new DecimalFormat("0.000");
	
	public CoordConverter(int pixXMin, int pixXMax, int pixYMin, int pixYMax,
			float minX, float maxX, float minY, float maxY) {
		super();
		this.pixXMin = pixXMin;
		this.pixXMax = pixXMax;
		this.pixYMin = pixYMin;
		this.pixYMax = pixYMax;
		xMin = minX;
		xMax = maxX;
		yMin = minY;
		yMax = maxY;
		
		float derivX = (pixXMax - pixXMin)/(maxX - minX);
		float derivY = (pixYMax - pixYMin)/(maxY - minY);
		System.out.println(derivX + "/" + derivY);
	}

	public void convertFile(File origine, File destination) throws IOException{
		FileReader reader = new FileReader(origine);
		BufferedReader buffR = new BufferedReader(reader);
		
		FileWriter writer = new FileWriter(destination);
		BufferedWriter buffW = new BufferedWriter(writer);
		
		String str = buffR.readLine();
		while (str!=null){
			int[] coord1 = getInts(str);
			float[] coord2 = convert(coord1[0], coord1[1]);
			buffW.write(DF.format(coord2[0]) + ";" + DF.format(coord2[1]));
			buffW.newLine();
			str = buffR.readLine();
		}
		
		buffR.close();
		reader.close();
		buffW.close();
		writer.close();
	}
	
	private int[] getInts(String str){
		int[] coord = new int[2];
		
		Scanner scanner = new Scanner(str);
        scanner.useDelimiter(";");
        scanner.useLocale(new Locale("en"));
       
        coord[0] = scanner.nextInt();
        coord[1] = scanner.nextInt();
		
		return coord;
	}
	
	private float[] convert(int x, int y){
		float[] coord = new float[2];
		
		float ratioX = ((float)(x - pixXMin))/(pixXMax - pixXMin);
		float ratioY = ((float)(y - pixYMin))/(pixYMax - pixYMin);
		
		coord[0] = ratioX*(xMax - xMin) + xMin;
		coord[1] = ratioY*(yMin - yMax) + yMax;
		
		return coord;
	}

	public static void main(String[] args) {
		CoordConverter cc = new CoordConverter(
				48,
				2054,
				50,
				677,
				-1.84f,
				1.84f,
				0.2f,
				1.38f);
		try {
			cc.convertFile(new File("Coords.txt"), new File("Coords2.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//float[] coord = cc.convert(15, 15);
		//System.out.println(coord[0] + "/" + coord[1]);
	}

}
