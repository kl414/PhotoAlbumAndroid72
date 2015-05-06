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
 * @author Edward Mamedov
 *
 */

public class RecaptionFrame {
	protected JTextField captionField;
	protected JTextField renameCaptionField;
	protected JFrame frame;
	protected ButtonListener bl;
	Control control;
	String albumName, photoName;

	public RecaptionFrame (Control control){
		this.control = control;
	}
		
	protected void recaptionPhoto(String photoName, String albumName){
		this.photoName = photoName;
		this.albumName = albumName;
		frame = new JFrame("Recaption Photo");
		frame.setVisible(true);
		frame.setSize(400,150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		JPanel namePanel = new JPanel(new FlowLayout());
		JLabel nameLabel = new JLabel("New Caption: ");
		renameCaptionField = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(renameCaptionField);

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
			String command = e.getActionCommand();
			if(command.equalsIgnoreCase("Cancel")){
				frame.dispose();
			}
			else if(command.equalsIgnoreCase("Save")){
				String newCaption = renameCaptionField.getText();
				control.recaption(photoName, albumName, newCaption);
				frame.dispose();
				OpenAlbum.update();
			}
		}
	}
}
