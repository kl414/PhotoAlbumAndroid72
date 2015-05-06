package cs213.photoAlbum.simpleview;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Photo;

/**
 * 
 * @author Edward Mamedov
 *
 */

public class SearchByDate{
	
	protected JFrame frame;
	protected ButtonListener bl;
	private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
	JLabel startDate, endDate;
	JTextField fromField;
	JTextField toField;
	JButton search, cancel;
	Control control;
	DateFormat format;
	List<Photo> photosByDate;


	public SearchByDate(Control control){
		this.control = control;
	}

	protected void searchByDate(){
		frame = new JFrame("Search by Date");
		frame.setSize(500, 150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		frame.setVisible(true);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		JPanel fromPanel = new JPanel(new FlowLayout());
		JLabel fromLabel = new JLabel("Start Date (MM/dd/yyyy-HH:mm:ss):");
		fromField = new JTextField(15);
		fromPanel.add(fromLabel);
		fromPanel.add(fromField);	

		JPanel toPanel= new JPanel(new FlowLayout());
		JLabel toLabel = new JLabel("End Date (MM/dd/yyyy-HH:mm:ss) : ");
		toField = new JTextField(15);
		toPanel.add(toLabel);
		toPanel.add(toField);

		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton search = new JButton("Search");
		search.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(search);
		buttonPanel.add(cancel);

		c.gridx = 0;
		c.weightx = 1;
		c.anchor=GridBagConstraints.WEST;
		c.gridy = 0;
		frame.add(fromPanel, c);
		c.gridy = 1;
		c.weightx = 0.5;
		frame.add(toPanel, c);
		c.gridy = 2;
		frame.add(buttonPanel, c);
	}

	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(command.equalsIgnoreCase("Cancel")){
				frame.dispose();
			}
			else if(command.equalsIgnoreCase("Search")){
				if ((fromField.getText() == null) || (toField.getText() == null)){
					new Error("Please specify start and end date");
					return;
				}

				try {
					photosByDate = new ArrayList<Photo>();
					photosByDate = control.getPhotosByDate(fromField.getText(), toField.getText());
					if (photosByDate.size() == 0){
						new Error("No Photos found in Date Range Specified");
					}
					else{
						
						JFrame search = new SearchResults(control, photosByDate);
						frame.setVisible(false);
						search.setVisible(true);
						frame.dispose();
						return;
					}
				} catch (ParseException e1) {
					if (e1.getErrorOffset() == 2){
						new Error("End date can't be before start date");
					}
					else new Error("Invalid Date format");
				}
			}

		}
	}
}