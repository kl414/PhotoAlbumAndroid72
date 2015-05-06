package cs213.photoAlbum.simpleview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

/**
 * 
 * @author Edward Mamedov
 *
 */

public class SlideshowView{

	protected JFrame frame;
	JPanel buttons;
	protected ButtonListener bl;
	Control control;
	String albumName;
	JLabel picLabel;
	Image myPicture;
	JButton exit, back, forward;
	List<Photo> photos;
	int index;
	JPanel pic;

	public SlideshowView (Control control){
		this.control = control;
	}

	protected void slideshow(Album album){
		this.albumName = albumName;
		frame = new JFrame("Slideshow");
		frame.setResizable(false);
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		pic = new JPanel();
		pic.setSize(700,600);
		
		photos = album.getPhotos();
		index = 0;
		
		try {
			myPicture = ImageIO.read(new File(photos.get(index).exactPath)).getScaledInstance(700, 600, BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picLabel = new JLabel(new ImageIcon(myPicture));
		pic.add(picLabel);
		bl = new ButtonListener();
		buttons = new JPanel();

		exit = new JButton("Close");
		exit.addActionListener(bl);

		back = new JButton("<");
		back.addActionListener(bl);

		forward = new JButton(">");
		forward.addActionListener(bl);

		buttons.add(back);
		buttons.add(exit);
		buttons.add(forward);

		gc.gridy = 0;
		gc.weighty = 1;
		gc.weightx = 1;
		frame.add(pic, gc);
		gc.gridy = 1;
		gc.weighty = 0;
		frame.add(buttons, gc);
		//update();
		back.setEnabled(false);
	}
	
	private void update() {
		
		if (index == 0){
			back.setEnabled(false);
			forward.setEnabled(true);
		}
		if (index == photos.size()-1){
			forward.setEnabled(false);
			back.setEnabled(true);
		}
		if (index != 0 && index != photos.size()-1){
			back.setEnabled(true);
			forward.setEnabled(true);
		}
		try {
			myPicture = ImageIO.read(new File(photos.get(index).exactPath)).getScaledInstance(700, 600, BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pic.removeAll();
		picLabel = new JLabel(new ImageIcon(myPicture));
		pic.add(picLabel);
		pic.validate();
	}

	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command){
			case "Close":
				frame.dispose();
				break;
			case "<":
				index = index-1;
				update();
				break;
			case ">":
				index = index+1;
				update();
				break;
			}
		}
	}
}


