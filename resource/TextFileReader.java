package resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class TextFileReader {

	private String  inputFileName;
	private String rawData;
	URL url;
	
	/**
	 * adds data in FILENAME line by line into rawData String.
	 */
	public void readText() {
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(getFileName());
			br = new BufferedReader(fr);

			String sCurrentLine;
			this.rawData = "";
			while ((sCurrentLine = br.readLine()) != null) {
				this.rawData = this.rawData + sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String getRawData() {
		readText();
		return this.rawData;
	}
	
	private String getFileName() { 
		return System.getProperty("user.dir") + "/src/resource/" + this.inputFileName;
	}
	
	public void setInputFileName(String fileName) {
		this.inputFileName = fileName;
	}

}
