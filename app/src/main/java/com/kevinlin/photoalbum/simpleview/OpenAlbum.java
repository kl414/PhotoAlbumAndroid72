package cs213.photoAlbum.simpleview;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.iBackend;
/**
 * 
 * @author Hongjie Lin
 *
 */
public class OpenAlbum extends JFrame{
	protected ButtonListener bl;
	static Control control;
	static String albumName;
	String userName;
	static JPanel display;
	static JList list, photoInfo;
	static int index;

	public OpenAlbum(Control control, String albumName, String userName){
		super("Photo Album - " + albumName);
		this.albumName = albumName;
		this.control = control;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		bl = new ButtonListener();

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		JButton exitAlbum = new JButton("Exit Album");
		exitAlbum.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 5);
		gc.weightx = 0;
		gc.gridx = 3;
		gc.insets = new Insets(0,400, 3, 0);
		gc.gridy = 0;
		gc.gridwidth = 1;
		add(exitAlbum, gc);
		display = new JPanel();

		//TODO need captions along with the thumbnails
		Album album = control.getAlbum(albumName);
		ImageIcon icon = null;
		DefaultListModel<ImageIcon> listmodel = new DefaultListModel<ImageIcon>();
		if(album.getPhotos().size() != 0){
			for(Photo photo: album.getPhotos()){
				try {
					icon = new ImageIcon(ImageIO.read(new File(photo.exactPath)).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listmodel.addElement(icon);
			}
		}
		list = new JList(listmodel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(1);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new listSelectionLis());
		
		gc.insets = new Insets(0, 5, 0, 5);
		gc.weightx = 0.5;
		gc.gridy = 1;
		gc.gridx = 0;
		gc.gridwidth = 3;
		JScrollPane sp = new JScrollPane(list);
		sp.setPreferredSize(new Dimension(500, 500));
		add(sp, gc);
		display.setPreferredSize(new Dimension(500, 500));
		index = list.getSelectedIndex();
		if(index != -1){
			try {
				String path = control.getAlbum(albumName).getPhotos().get(index).exactPath;
				icon = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance((int)display.getPreferredSize().getWidth(), (int)display.getPreferredSize().getHeight(), BufferedImage.SCALE_SMOOTH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JLabel img = new JLabel(icon);
		display.add(img);
		gc.insets = new Insets(0, 5, 0, 5);
		gc.weightx = 0.5;
		gc.gridy = 1;
		gc.gridx = 3;
		gc.gridwidth = 3;
		add(display, gc);

		JPanel operation = new JPanel(new GridBagLayout());
		operation.setPreferredSize(new Dimension(500, 100));
		JButton add = new JButton("Add");
		add.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 0;
		gc.gridx = 0;
		gc.gridwidth = 1;
		operation.add(add, gc);

		JButton remove = new JButton("Remove");
		remove.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 0;
		gc.gridx = 1;
		operation.add(remove, gc);

		JButton recaption = new JButton("Recaption");
		recaption.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 0;
		gc.gridx = 2;
		operation.add(recaption, gc);

		JButton addTag = new JButton("Add Tag");
		addTag.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 1;
		gc.gridx = 0;
		operation.add(addTag, gc);

		JButton deleteTag = new JButton("Delete Tag");
		deleteTag.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 1;
		gc.gridx = 1;
		operation.add(deleteTag, gc);

		JButton move = new JButton("Move");
		move.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 1;
		gc.gridx = 2;
		operation.add(move, gc);

		JButton slideshow = new JButton("Slideshow");
		slideshow.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 2;
		gc.gridx = 1;
		operation.add(slideshow, gc);


		gc.insets =  new Insets(10, 5, 0, 5);
		gc.weightx = 0.5;
		gc.gridy = 2;
		gc.gridx = 0;
		gc.gridwidth = 3;
		add(operation, gc);

		DefaultListModel<String> labels = new DefaultListModel<String>();
		index = list.getSelectedIndex();
		if(index != -1){
			ArrayList<String> contents = control.listPhotoInfo(control.getAlbum(albumName).getPhotos().get(index).fileName);
			for(String content : contents){
				labels.addElement(content);
			}
		}
		photoInfo = new JList(labels);
		photoInfo.setEnabled(false);
		JScrollPane info= new JScrollPane(photoInfo);
		info.setPreferredSize(new Dimension(500, 100));
		gc.insets = new Insets(10, 5, 0, 5);
		gc.weightx = 0;
		gc.gridy = 2;
		gc.gridx = 3;
		gc.gridwidth = 3;
		add(info, gc);

		pack();
		this.setLocationRelativeTo(null);	
		setVisible(true);
		this.addWindowListener(new WindowListener());
	}

	private class listSelectionLis implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(!e.getValueIsAdjusting()){
				index = list.getSelectedIndex();
				DefaultListModel<String> infoModel = (DefaultListModel<String>) photoInfo.getModel();
				infoModel.removeAllElements();
				if(index != -1){
					ArrayList<String> contents = control.listPhotoInfo(control.getAlbum(albumName).getPhotos().get(index).fileName);
					for(String content : contents){
						infoModel.addElement(content);
					}
				}
				photoInfo.validate();
				photoInfo.repaint();
				

				ImageIcon icon = null;
				display.removeAll();
				if(index != -1){
					try {
						String path = control.getAlbum(albumName).getPhotos().get(index).exactPath;
						icon = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance((int)display.getPreferredSize().getWidth(), (int)display.getPreferredSize().getHeight(), BufferedImage.SCALE_SMOOTH));
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
				JLabel img = new JLabel(icon);
				display.add(img);
				display.validate();
				display.repaint();
			}
		}
    }
	
	protected class WindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			try {
				iBackend.writeBackend((iBackend)control.backend);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			System.exit(0);
		}
	}


	protected static void update(){
		Album album = control.getAlbum(albumName);
		ImageIcon icon = null;
		DefaultListModel<ImageIcon> listmodel = (DefaultListModel<ImageIcon>) list.getModel();
		listmodel.removeAllElements();
		if(album.getPhotos().size() != 0){
			for(Photo photo: album.getPhotos()){
				try {
					icon = new ImageIcon(ImageIO.read(new File(photo.exactPath)).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				listmodel.addElement(icon);
			}
		}
		list.validate();
		list.setSelectedIndex(index);
		list.ensureIndexIsVisible(index);
		/*
		DefaultListModel<String> infoModel = (DefaultListModel<String>) photoInfo.getModel();
		infoModel.removeAllElements();
		if(index != -1){
			ArrayList<String> contents = control.listPhotoInfo(control.getAlbum(albumName).getPhotos().get(index).fileName);
			for(String content : contents){
				infoModel.addElement(content);
			}
		}
		photoInfo.validate();
		
		display.removeAll();
		if(index != -1){
			try {
				String path = control.getAlbum(albumName).getPhotos().get(index).exactPath;
				icon = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance((int)display.getPreferredSize().getWidth(), (int)display.getPreferredSize().getHeight(), BufferedImage.SCALE_SMOOTH));
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		JLabel img = new JLabel(icon);
		display.add(img);
		display.validate();
		*/
	}
	
	protected void refresh(){
		
	}
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			index = list.getSelectedIndex();
			switch(command){
			case "Add":
				PhotoView addp = new PhotoView(control);
				addp.addPhoto(albumName);
				break;
			case "Remove":
				PhotoView removep = new PhotoView(control);
				if(index == -1){
					new Error("Please select the photo you want to remove!");
				}else{
					Photo photo = control.getAlbum(albumName).getPhotos().get(index);
					removep.removePhoto(albumName, photo.fileName);
				}
				break;
			case "Recaption":
				//TODO get photo name
				RecaptionFrame recap = new RecaptionFrame(control);
				if(index == -1){
					new Error("Please select the photo you want to do recaption!");
				}else{
					Photo photo1 = control.getAlbum(albumName).getPhotos().get(index);
					recap.recaptionPhoto(photo1.fileName, albumName);
				}
				break;	
			case "Add Tag":
				//TODO get photo name
				Tag addt = new Tag(control);
				if(index == -1){
					new Error("Please select the photo you want to add tag!");
				}else{
					Photo photo2 = control.getAlbum(albumName).getPhotos().get(index);
					addt.addTag(photo2.fileName);
				}
				break;	
			case "Delete Tag":
				Tag deleteT = new Tag(control);
				if(index == -1){
					new Error("Please select the photo you want to delete tag!");
				}else{
					Photo photo3 = control.getAlbum(albumName).getPhotos().get(index);
					deleteT.deleteTag(photo3.fileName);
				}
				break;	
			case "Move":
				MovePhoto move = new MovePhoto(control);
				if(index == -1){
					new Error("Please select the photo you want to move!");
				}else{
					Photo photo4 = control.getAlbum(albumName).getPhotos().get(index);
					move.move(photo4.fileName, albumName);
				}
				break;	
			case "Slideshow":
				if(index == -1){
					new Error("Please select the photo you want to move!");
				}else{
				SlideshowView ssv = new SlideshowView(control);
				ssv.slideshow(control.getAlbum(albumName));
				}
				break;	
			case "Exit Album":
				Frame frame = new UserView(control, userName);
				frame.setVisible(true);
				dispose();
				break;		
			}
		}

	}
}
