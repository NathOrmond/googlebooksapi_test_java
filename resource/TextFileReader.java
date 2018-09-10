package resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class TextFileReader {

	private static String FILENAME;
	private static String rawData;
	URL url;
	
	/**
	 * adds data in FILENAME line by line into rawData String.
	 */
	public static void readText() {
		BufferedReader br = null;
		FileReader fr = null;
		setFILENAME();
		
		try {
			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			TextFileReader.rawData = "";
			while ((sCurrentLine = br.readLine()) != null) {
				TextFileReader.rawData = TextFileReader.rawData + sCurrentLine;
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
	
	public static String getRawData() {
		return TextFileReader.rawData;
	}
	
	public static void setFILENAME() {
		TextFileReader.FILENAME = System.getProperty("user.dir") + "/src/resource/testdata.txt";
	}

}
