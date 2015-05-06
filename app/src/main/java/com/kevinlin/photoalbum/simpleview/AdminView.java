package cs213.photoAlbum.simpleview;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.model.iBackend;

/**
 * 
 * @author Edward Mamedov
 *
 */

public class AdminView extends JFrame {

	Control control;
	JList<String> users;
	JButton addUser, deleteUser, logout;
	JLabel error;
	DefaultListModel listModel;

	public AdminView(Control tmpControl){
		super("Photo Album (Admin)");
		control = tmpControl;

		this.setSize(400,310);
		//this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		addUser = new JButton("Add User");
		deleteUser = new JButton("Delete User");
		logout = new JButton("Logout");

		error = new JLabel("");
		error.setForeground(Color.RED);

		addUser.addActionListener(new ButtonListener(this));
		deleteUser.addActionListener(new ButtonListener(this));
		logout.addActionListener(new ButtonListener(this));

		listModel = new DefaultListModel();
		control.deleteUser("admin");
		List<User> tmpUser = control.backend.getUsers();
		for(User userList : tmpUser) {
			listModel.addElement(userList.name);
		}
		users = new JList(listModel);
		users.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		users.addListSelectionListener(new ListListener());

		JScrollPane sP = new JScrollPane();
		sP.setViewportView(users);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 1;
		c.insets = new Insets(6,0,0,0);
		this.add(addUser, c);

		c.gridx = 3;
		c.weightx = 1;
		c.insets = new Insets(6,0,0,0);
		this.add(deleteUser, c);

		c.gridx = 4;
		c.insets = new Insets(6,0,0,0);
		this.add(logout, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		this.add(sP, c);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(1,10,1,10);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(error,c);
		
		this.addWindowListener(new WindowListener());

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
	
	class ListListener implements ListSelectionListener
	{	
		public void valueChanged(ListSelectionEvent arg0)
		{
			error.setText(null);
		}
	}

	class ButtonListener implements ActionListener
	{
		AdminView frame;

		public ButtonListener(AdminView av)
		{
			frame = av;
		}

		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == frame.addUser){
				frame.error.setText("");
				JFrame addUserPrompt = new AddUserPrompt(control, frame);
				addUserPrompt.setVisible(true);
			}
			else if (e.getSource() == frame.deleteUser){
				frame.error.setText("");
				if (control.backend.getUsers().size() < 1){
					frame.error.setText("Error: No available users to delete");
				}
				int index = users.getSelectedIndex();
				if(index == -1){
					new Error("Please select a user to delete!");
					return;
				}
				String temp = frame.users.getSelectedValue();
				List<User> tmpUser = control.backend.getUsers();
					for(User userList : tmpUser) {
						if (temp.equalsIgnoreCase(userList.name)){
							control.deleteUser(userList.id);
							listModel.removeElement(userList.name);
							frame.users.setSelectedIndex(0);
							return;
						}
					}
				}
			else if (e.getSource() == frame.logout){
				try {
					iBackend.writeBackend((iBackend)control.backend); //double check if users are getting saved.
					JFrame loggedOut = new Login(control);
					frame.setVisible(false);
					loggedOut.setVisible(true);
					frame.dispose();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
