package cs213.photoAlbum.simpleview;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;

/**
 * 
 * @author Hongjie Lin
 *
 */

public class PhotoView {
	
	protected JFrame frame;
	protected ButtonListener bl;
	Control control;
	protected JTextField fileNameField;
	protected JTextField captionField;
	String albumName, photoName;
	protected JTextField tagTypeField, tagValueField;
	
	public PhotoView (Control control){
		this.control = control;
	}
	
	
	protected void removePhoto(String albumName, String photoName){
		control.removePhoto(photoName, albumName);
		OpenAlbum.update();
	}
	
	protected void addPhoto(String albumName){
		this.albumName = albumName;
		frame = new JFrame("Add Photo");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JPanel fileNamePanel = new JPanel(new FlowLayout());
		JLabel fileNameLabel = new JLabel("File Name:");
		fileNameField = new JTextField(15);
		fileNamePanel.add(fileNameLabel);
		fileNamePanel.add(fileNameField);	
		
		JPanel captionPanel= new JPanel(new FlowLayout());
		JLabel captionLabel = new JLabel("Caption:   ");
		captionField = new JTextField(15);
		captionPanel.add(captionLabel);
		captionPanel.add(captionField);
		
		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton add = new JButton("Add");
		add.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(add);
		buttonPanel.add(cancel);
		
		c.gridx = 0;
		c.weightx = 1;
		c.anchor=GridBagConstraints.WEST;
		c.gridy = 0;
		frame.add(fileNamePanel, c);
		c.gridy = 1;
		c.weightx = 0.5;
		frame.add(captionPanel, c);
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
			else if(command.equalsIgnoreCase("Add")){
				String fileName = fileNameField.getText();
				String caption = captionField.getText();
				try {
					control.addPhoto(fileName, caption, albumName);
				} catch (FileNotFoundException e1) {
					new Error("The file does not exist!");
				}
				frame.dispose();
				OpenAlbum.update();
			}
		}
	}
}
