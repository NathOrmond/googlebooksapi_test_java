package code.main;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.parser.ParseException;

import code.hci.Frame;
import code.hci.Panel;

public class Main {
	static Frame frame;
	static Panel panel;

	public static void main(String[] args) throws ParseException, IOException {
		
		try {
		      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (InstantiationException e) {
		      e.printStackTrace();
		    } catch (IllegalAccessException e) {
		      e.printStackTrace();
		    } catch (UnsupportedLookAndFeelException e) {
		      e.printStackTrace();
		    }

		panel = new Panel();
		frame = new Frame(panel);
	}

}
