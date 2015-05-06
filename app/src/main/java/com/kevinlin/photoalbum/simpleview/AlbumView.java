package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class AlbumView {

	protected JFrame frame;
	protected ButtonListener bl;
	Control control;
	protected JTextField nameField;
	String albumName;
	
	public AlbumView(Control control){
		this.control = control;
	}
	
	protected void deleteAlbum(){
		
	}
	
	protected void createAlbum(){
		frame = new JFrame("Create Album");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		JPanel namePanel = new JPanel(new FlowLayout());
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton add = new JButton("Add");
		add.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(add);
		buttonPanel.add(cancel);
		
		frame.add(namePanel,  BorderLayout.NORTH);
		frame.add(buttonPanel,  BorderLayout.CENTER);
		
	}
	
	protected void renameAlbum(String albumName){
		this.albumName = albumName;
		frame = new JFrame("Rename Album");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		JPanel namePanel = new JPanel(new FlowLayout());
		JLabel nameLabel = new JLabel("New Name: ");
		nameField = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton save = new JButton("Save");
		save.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(save);
		buttonPanel.add(cancel);
		
		frame.add(namePanel,  BorderLayout.NORTH);
		frame.add(buttonPanel,  BorderLayout.CENTER);
		
		
	}
	
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			if(command.equalsIgnoreCase("Cancel")){
				frame.dispose();
			}else if(command.equalsIgnoreCase("Add")){
				String albumName = nameField.getText();
				if(albumName == null || albumName.trim().length() == 0){
					new Error("Album names shouldn't be empty!");
				}else{
					if(control.createAlbum(albumName)){
						//TODO display the albums on Photo Album - Login
						UserView.modifyTable(control.listAlbums());
						frame.dispose();
					}else{
						new Error("The album is already exist!");
					}
				}
			}else{
				String newName = nameField.getText();
				if(newName == null || newName.trim().length() == 0){
					new Error("Album names shouldn't be empty!");
				}else{
					if(control.rename(albumName, newName)){
						UserView.modifyTable(control.listAlbums());
						frame.dispose();
					}else{
						new Error("The new name is duplicate with existing album name");
					}
				}
			}
		}
		
	}

}
