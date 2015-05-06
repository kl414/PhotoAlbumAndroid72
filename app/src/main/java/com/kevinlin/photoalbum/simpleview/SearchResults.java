package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.iBackend;

/**
 * 
 * @author Edward Mamedov
 *
 */

public class SearchResults extends JFrame {

	static Control control;
	static List<Photo> photos;
	static String albumName;
	protected ButtonListener bl;
	protected JFrame frame;
	static JPanel display;
	static JList list, photoInfo;
	static int index;
	static Album album;
	protected JFrame frame2;
	protected JTextField nameField;
	
	public SearchResults(Control control, List<Photo> photos) {
		super("Search - Query Results");
		this.control = control;
		this.photos = photos;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		display = new JPanel();
		bl = new ButtonListener();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		JButton exitAlbum = new JButton("Exit Search");
		exitAlbum.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 5);
		gc.weightx = 0;
		gc.gridx = 3;
		gc.insets = new Insets(0,400, 3, 0);
		gc.gridy = 0;
		gc.gridwidth = 1;
		add(exitAlbum, gc);

		//TODO need captions along with the thumbnails

		ImageIcon icon = null;
		DefaultListModel<ImageIcon> listmodel = new DefaultListModel<ImageIcon>();
		if(photos.size() != 0){
			for(Photo photo: photos){
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
		JPanel display = new JPanel();
		display = new JPanel();
		display.setPreferredSize(new Dimension(500, 500));
		index = list.getSelectedIndex();
		if(index != -1){
			try {
				String path = photos.get(index).exactPath;
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
		JButton add = new JButton("Save As Album");
		add.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridy = 0;
		gc.gridx = 0;
		gc.gridwidth = 1;
		operation.add(add, gc);

		JButton slideshow = new JButton("Slideshow");
		slideshow.addActionListener(bl);
		gc.insets = new Insets(0, 0, 3, 3);
		gc.weightx = 0.5;
		gc.gridx = 0;
		gc.gridy = 1;
		operation.add(slideshow, gc);


		gc.insets =  new Insets(10, 5, 0, 5);
		gc.weightx = 0.5;
		gc.gridy = 2;
		gc.gridx = 0;
		gc.gridwidth = 3;
		add(operation, gc);

		//bottom right is a JScrollPane with JList of labels, JTable is only for holding space now
		DefaultListModel<String> labels = new DefaultListModel<String>();
		index = list.getSelectedIndex();
		if(index != -1){
			ArrayList<String> contents = control.listPhotoInfo(photos.get(index).fileName);
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
					ArrayList<String> contents = control.listPhotoInfo(photos.get(index).fileName);
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
						String path = photos.get(index).exactPath;
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

	protected void createAlbum(){
		frame2 = new JFrame("Name of New Album:");
		frame2.setVisible(true);
		frame2.setSize(400,150);
		frame2.setResizable(false);
		frame2.setLocationRelativeTo(null);
		frame2.setLayout(new BorderLayout());

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

		frame2.add(namePanel,  BorderLayout.NORTH);
		frame2.add(buttonPanel,  BorderLayout.CENTER);
		
		album = new Album("temp");
		for(Photo p: photos){
			album.add(p);
		}
	}

	protected static void update(){
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
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			index = list.getSelectedIndex();
			switch(command){
			case "Save As Album":
				album = null;
				createAlbum();
				break;
			case "Slideshow":
				if(index == -1){
					new Error("Please select the photo you want to move!");
				}else{
					SlideshowView ssv = new SlideshowView(control);
					ssv.slideshow(album);
				}
				break;	
			case "Exit Search":
				dispose();
				break;
			case "Add":
				String newName = nameField.getText();
				if(newName == null || newName.trim().length() == 0){
					new Error("Album names shouldn't be empty!");
				}else{
					album.setName(newName);
					control.addAlbum(album);
					frame2.dispose();
					UserView.modifyTable(control.listAlbums());
				}
				break;
			case "Cancel":
				frame2.dispose();
				break;
			}

		}

	}
}

