package code.hci;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import code.analysis.datatypes.DataPrices;
import code.analysis.datatypes.DataPublishers;
import code.analysis.datatypes.DataRatings;
import code.main.Composer;

public class Panel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton q1, q2, q3, q4;
	private JScrollPane scrollPane;
	private JTextArea output;
	private GridBagLayout mgr;
	private GridBagConstraints gbc;
	private Composer c;

	public Panel() {
		mgr = new GridBagLayout();
		gbc = new GridBagConstraints();
		this.setLayout(mgr);

		q1 = new JButton("Question 1");
		q1.setActionCommand(ActionCommands.question1);
		q1.addActionListener(this);

		q2 = new JButton("Question 2");
		q2.setActionCommand(ActionCommands.question2);
		q2.addActionListener(this);

		q3 = new JButton("Question 3");
		q3.setActionCommand(ActionCommands.question3);
		q3.addActionListener(this);

		q4 = new JButton("Question 4");
		q4.setActionCommand(ActionCommands.question4);
		q4.addActionListener(this);

		Dimension d = new Dimension(800, 600);

		output = new JTextArea();
		output.setSize(d);
		output.setEditable(false);
		Font f = new Font("TimesRoman", Font.PLAIN, 20);
		output.setFont(f);

		scrollPane = new JScrollPane(output);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(d);

		gbc.anchor = GridBagConstraints.CENTER;
		addComponentToPanel(q1, 0, 1);
		gbc.anchor = GridBagConstraints.NORTH;
		addComponentToPanel(q2, 0, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		addComponentToPanel(q3, 0, 3);
		gbc.anchor = GridBagConstraints.NORTH;
		addComponentToPanel(q4, 0, 4);

		addComponentToPanel(scrollPane, 0, 0);

		try {
			c = new Composer();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * adds new component to panel dependent on x and y grid (GridBagConstraint Layout manager)
	 * 
	 * @param component
	 * @param x
	 * @param y
	 */
	private void addComponentToPanel(Component component, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		mgr.setConstraints(component, gbc);
		this.add(component);
	}

	public JPanel getPanel() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clearOutput();
		switch (e.getActionCommand()) {
		case ActionCommands.question1:
			
			try {
				q1();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			break;

		case ActionCommands.question2:
		
			try {
				q2();
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		
			break;

		case ActionCommands.question3:
		
			try {
				q3();
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		
			break;

		case ActionCommands.question4:
			
			try {
				q4();
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		
			break;

		default:
			break;
		}
	}

	private void clearOutput() {
		output.setText("");
	}

	public void updateOutput(String str) {
		output.setText(str);
	}

	private void q4() throws ParseException, IOException {
		JSONObject json = DataPublishers.booksByPublisher();
		String str = json.toString();
		updateOutput(formatOutputForDisplay(str.toCharArray()));
	}

	private void q3() throws ParseException, IOException {
		int rankedListLen = 5;
		DataPrices d = new DataPrices();
		updateOutput(formatOutputForDisplay(d.getRankedArray(rankedListLen).toJSONString().toCharArray()));	
	}

	private void q2() throws ParseException, IOException {
		JSONArray json = DataRatings.getHighestRated(10);
		String str = json.toString();
		updateOutput(formatOutputForDisplay(str.toCharArray()));
	}

	private void q1() throws IOException {
		updateOutput(formatOutputForDisplay(c.getSrvConn().getCompleteDataSet().toCharArray()));
	}

	private String formatOutputForDisplay(char[] chars) {
		String output = "";
		for (int i = 0; i < chars.length; i++) {
			output = output + chars[i];
			if ((chars[i] == '{') || (chars[i] == '}')) {
				output = output + "\n";
			}
		}
		return output;
	}

}
