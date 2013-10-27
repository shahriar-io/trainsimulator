package jts.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**Cette classe permet de réaliser un log des évènements du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Log {
	
	private static Log INSTANCE;
	
	private BufferedWriter writer;
	private LogMode logMode;

	private Log(BufferedWriter writer, LogMode logMode){
		this.writer = writer;
		this.logMode = logMode;
	}
	
	public static void init(File fichierLog, LogMode logMode) throws IOException {
		FileWriter fWriter = new FileWriter(fichierLog);
		BufferedWriter writer = new BufferedWriter(fWriter);
		INSTANCE = new Log(writer, logMode);
	}
	
	public static void close() throws IOException {
		INSTANCE.writer.close();
	}
	
	public static Log getInstance(){ return INSTANCE; }
	
	public void logInfo(String str){
		log(str, true);
	}
	
	public void logWarning(String str, boolean sortieConsole){
		if(logMode != LogMode.INFO){
			log(str, sortieConsole);
		}
	}
	
	public void logDebug(String str, boolean sortieConsole){
		if(logMode == LogMode.DEBUG){
			log(str, sortieConsole);
		}
	}
	
	private void log(String str, boolean sortieConsole){
		if(sortieConsole){
			System.out.println(str);
		}
		fileLog(str);
	}
	
	private void fileLog(String str){
		try {
			writer.write(str);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.out.println("Unable to write log");
			e.printStackTrace();
		}
	}
}
