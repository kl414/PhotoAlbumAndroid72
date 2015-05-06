package cs213.photoAlbum.simpleview;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.iBackend;

/**
 * @author Hongjie Lin
 *
 */
public class UserView extends JFrame{
	protected ButtonListener bl;
	Control control;
	static JTable table;
	String userName;
	
	public UserView(Control control, String userName){
		super("Photo Album - (" + userName + ")");
		this.control = control;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.userName = userName;
		bl = new ButtonListener();
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton logout = new JButton("Logout");

		gc.insets = new Insets(0, 0, 3, 5);
		gc.weightx = 0.5;
		gc.gridx = 6;
		gc.gridy = 0;
		logout.addActionListener(bl);
		add(logout, gc);
		
		String[][] contents = control.listAlbums();
		String[] columnNames = {"Album Name", "# Photos", "Begin Date", "End Date"};
		table = new JTable(new tableModelE(contents, columnNames));
		table.setPreferredScrollableViewportSize(new Dimension(600, 400));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(25);
		gc.insets = new Insets(0, 5, 0, 5);
		gc.weightx = 0;
		gc.gridy = 1;
		gc.gridx = 0;
		gc.gridwidth = 7;
		JScrollPane sp = new JScrollPane(table);
		add(sp, gc);
		
		
		JButton create = new JButton("Create");
		create.addActionListener(bl);
		gc.insets = new Insets(10, 5, 10, 0);
		gc.weightx = 0.5;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		add(create, gc);
		
		JButton remove = new JButton("Remove");
		remove.addActionListener(bl);
		gc.weightx = 0.5;
		gc.gridx = 1;
		gc.gridy = 2;
		add(remove, gc);
		
		JButton rename = new JButton("Rename");
		rename.addActionListener(bl);
		gc.weightx = 0.5;
		gc.gridx = 2;
		gc.gridy = 2;
		add(rename, gc);
		
		JButton open = new JButton("Open");
		open.addActionListener(bl);
		gc.weightx = 0.5;
		gc.gridx = 3;
		gc.gridy = 2;
		add(open, gc);
		
		JLabel search  = new JLabel("<html><body style='text-align: right'>Search<br/> By:</html>");
		gc.insets = new Insets(10, 25, 10, 0);
		gc.weightx = 0.5;
		gc.gridx = 4;
		gc.gridy = 2;
		add(search, gc);
		
		JButton tag = new JButton("Tag");
		tag.addActionListener(bl);
		gc.insets = new Insets(10, 3, 10, 0);
		gc.weightx = 0.5;
		gc.gridx = 5;
		gc.gridy = 2;
		add(tag, gc);
		
		JButton date = new JButton("Date");
		date.addActionListener(bl);
		gc.insets = new Insets(10, 3, 10, 5);
		gc.weightx = 0.5;
		gc.gridx = 6;
		gc.gridy = 2;
		add(date, gc);
		
		
		pack();

		this.setLocationRelativeTo(null);	
		this.addWindowListener(new WindowListener());
	}
	
	public static void modifyTable(String[][] contents){
		String[] columnNames = {"Album Name", "# Photos", "Begin Date", "End Date"};
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setDataVector(contents, columnNames);
		table.validate();
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
	
	private class tableModelE extends DefaultTableModel{
		
		public tableModelE(Object[][] info, Object[] col){
			super(info, col);
		}
		@Override
		public boolean isCellEditable(int row, int column){
			return false;
		}
	}
	/*
	private class JTableE extends JTable{
		
		public JTableE(DefaultTableModel listmodel){
			super(listmodel);
		}
		@Override
		public boolean isCellEditable(int row, int column){
			return false;
		}
	}
	*/
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			if(command.equalsIgnoreCase("Create")){
				AlbumView av = new AlbumView(control);
				av.createAlbum();
			}else if(command.equalsIgnoreCase("Remove")){
	            int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
	            int selectedColumn = table.convertColumnIndexToModel(0);
	            if (selectedRow < 0 || selectedColumn < 0){
	            	new Error("Please select the album you want to remove!");
	            }else{
		    		DefaultTableModel model = (DefaultTableModel) table.getModel();
		            String temp = (String)model.getValueAt(selectedRow, selectedColumn);
					control.deleteAlbum(temp);
					modifyTable(control.listAlbums());
	            }
			}else if(command.equalsIgnoreCase("Rename")){
				// TODO get the selected albumName
				AlbumView av = new AlbumView(control);
				int index = table.getSelectedRow();
				if(index == -1){
					new Error("Please select the album you want to rename!");
				}else 
					av.renameAlbum((String)table.getValueAt(index, 0));
			}else if(command.equalsIgnoreCase("Open")){
				int index = table.getSelectedRow();
				if(index == -1){
					new Error("Please select the album you want to open!");
				}else{
					OpenAlbum oa = new OpenAlbum(control, (String)table.getValueAt(index, 0), userName);
					dispose();
				}
			}else if(command.equalsIgnoreCase("Tag")){
				Tag tag = new Tag(control);
				tag.searchByTag();
			}else if(command.equalsIgnoreCase("Date")){
				SearchByDate sbd = new SearchByDate(control);
				sbd.searchByDate();
			}else if(command.equalsIgnoreCase("Logout")){
				try {
					iBackend.writeBackend((iBackend)control.backend); //double check if users are getting saved.
					JFrame loggedOut = new Login(control);
					setVisible(false);
					loggedOut.setVisible(true);
					dispose();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
}
