package cs213.photoAlbum.simpleview;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

/**
 * 
 * @author Hongjie Lin
 *
 */

public class Tag {
	
	
	public Tag (Control control){
		this.control = control;
	}
	
	protected JFrame frame;
	protected ButtonListener bl;
	Control control;
	protected JTextField tagTypeField;
	protected JTextField tagValueField;
	String albumName, photoName;
	List<Photo> photos;
	
	protected void searchByTag(){
		photos = new ArrayList<Photo>();
		frame = new JFrame("Search By Tag");
		frame.setSize(500, 150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		frame.setVisible(true);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		JPanel fromPanel = new JPanel(new FlowLayout());
		JLabel fromLabel = new JLabel("Tag Type : ");
		tagTypeField = new JTextField(15);
		fromPanel.add(fromLabel);
		fromPanel.add(tagTypeField);	

		JPanel toPanel= new JPanel(new FlowLayout());
		JLabel toLabel = new JLabel("Tag Value : ");
		tagValueField = new JTextField(15);
		toPanel.add(toLabel);
		toPanel.add(tagValueField);

		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton search = new JButton("Search");
		search.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		JButton moreTags = new JButton("More Tags");
		moreTags.addActionListener(bl);
		buttonPanel.add(search);
		buttonPanel.add(cancel);
		buttonPanel.add(moreTags);
		
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
	protected void deleteTag(String photoName){
		this.photoName = photoName;
		frame = new JFrame("Delete Tag");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JPanel tagTypePanel = new JPanel(new FlowLayout());
		JLabel tagTypeLabel = new JLabel("Tag Type: ");
		tagTypeField = new JTextField(15);
		tagTypePanel.add(tagTypeLabel);
		tagTypePanel.add(tagTypeField);	
		
		JPanel tagValuePanel= new JPanel(new FlowLayout());
		JLabel tagValueLabel = new JLabel("Tag Value:");
		tagValueField = new JTextField(15);
		tagValuePanel.add(tagValueLabel);
		tagValuePanel.add(tagValueField);
		
		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton add = new JButton("Delete");
		add.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(add);
		buttonPanel.add(cancel);
		
		c.gridx = 0;
		c.weightx = 1;
		c.anchor=GridBagConstraints.WEST;
		c.gridy = 0;
		frame.add(tagTypePanel, c);
		c.gridy = 1;
		c.weightx = 0.5;
		frame.add(tagValuePanel, c);
		c.gridy = 2;
		frame.add(buttonPanel, c);
	}
	protected void addTag(String photoName){
		this.photoName = photoName;
		frame = new JFrame("Add Tag");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JPanel tagTypePanel = new JPanel(new FlowLayout());
		JLabel tagTypeLabel = new JLabel("Tag Type: ");
		tagTypeField = new JTextField(15);
		tagTypePanel.add(tagTypeLabel);
		tagTypePanel.add(tagTypeField);	
		
		JPanel tagValuePanel= new JPanel(new FlowLayout());
		JLabel tagValueLabel = new JLabel("Tag Value:");
		tagValueField = new JTextField(15);
		tagValuePanel.add(tagValueLabel);
		tagValuePanel.add(tagValueField);
		
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
		frame.add(tagTypePanel, c);
		c.gridy = 1;
		c.weightx = 0.5;
		frame.add(tagValuePanel, c);
		c.gridy = 2;
		frame.add(buttonPanel, c);
	}
	
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			String tagType = tagTypeField.getText();
			String tagValue = tagValueField.getText();
			if(command.equalsIgnoreCase("Cancel")){
				frame.dispose();
			}
			else if(command.equalsIgnoreCase("Add")){
				if(!control.addTag(photoName, tagType, tagValue))
					new Error("Tag already exists for " + photoName + " " + tagType + ":" + tagValue);
				frame.dispose();
				OpenAlbum.update();
			}else if(command.equalsIgnoreCase("Delete")){
				if(!control.deleteTag(photoName, tagType, tagValue))
					new Error("Tag does no exist for " + photoName + " " + tagType + ":" + tagValue);
				frame.dispose();
				OpenAlbum.update();
			}else if(command.equalsIgnoreCase("Search")){
				photos = control.getPhotosByTag(photos, tagType, tagValue);
				Album temp = new Album("temp");
				for (Photo p : photos){
					temp.add(p);
				}
				if (photos.size() == 0){
					new Error("No Photos found for the input tags!");
				}else{
					JFrame search = new SearchResults(control, photos);
					frame.setVisible(false);
					search.setVisible(true);
					frame.dispose();
				}
			}else if(command.equalsIgnoreCase("More Tags")){
				photos = control.getPhotosByTag(photos, tagType, tagValue);
				tagTypeField.setText("");
				tagValueField.setText("");
			}
		}
	}
}
