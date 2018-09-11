package resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TextFileWriter {
	
	private String outPutFile= System.getProperty("user.dir") + "/src/resource/";
	
	
	public void writeFile(String content) { 
		 BufferedWriter writer = null;
	        try {
	            File logFile = new File(this.outPutFile);
	            System.out.println("New File Created : " +  logFile.getCanonicalPath());
	            writer = new BufferedWriter(new FileWriter(logFile));
	            writer.write(content);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        } 
	}
	
	
	public void setOutPutFile(String outputFileName) { 
		this.outPutFile = this.outPutFile + outputFileName;
	}
	

}
