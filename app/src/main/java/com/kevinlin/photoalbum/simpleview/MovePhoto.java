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

public class MovePhoto {

	protected JTextField albumNameField;
	protected JFrame frame;
	protected ButtonListener bl;
	Control control;
	String albumName, photoName;

	public MovePhoto (Control control){
		this.control = control;
	}

	protected void move(String photoName, String albumName){
		this.photoName = photoName;
		this.albumName = albumName;
		frame = new JFrame("Move Photo");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		JPanel albumPanel = new JPanel(new FlowLayout());
		JLabel albumLabel = new JLabel("Destination Album Name: ");
		albumNameField = new JTextField(15);
		albumPanel.add(albumLabel);
		albumPanel.add(albumNameField);
		
		bl = new ButtonListener();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton save = new JButton("Save");
		save.addActionListener(bl);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(bl);
		buttonPanel.add(save);
		buttonPanel.add(cancel);
		
		frame.add(albumPanel, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.CENTER);


	}

	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(command.equalsIgnoreCase("Cancel")){
				frame.dispose();
			}
			else if(command.equalsIgnoreCase("Save")){
				boolean temp;
				temp = control.movePhoto(photoName, albumName, albumNameField.getText());
				if (temp == false){
					new Error("Destination album doesn't exist");
				}else{
					frame.dispose();
					OpenAlbum.update();
				}
			}
		}
	}
}
