package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.User;

/**
 * 
 * @author Edward Mamedov
 *
 */

public class Login extends JFrame{
	JLabel username, errorMessage;
	JTextField tfUsername;
	JButton login;
	JPanel pName, pButton, pError;
	Control control;

	public Login(Control c){
		super("Photo Album - Login");
		control = c;

		this.setSize(400,150);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		pError = new JPanel(new FlowLayout());
		errorMessage = new JLabel(" ");
		errorMessage.setForeground(Color.RED);
		pError.add(errorMessage);

		pButton = new JPanel(new FlowLayout());
		login = new JButton("Login");
		pButton.add(login);
		login.addActionListener(new ButtonListener(this));

		tfUsername = new JTextField(15);
		username = new JLabel("Username: ");
		pName = new JPanel(new FlowLayout());
		pName.add(username);
		pName.add(tfUsername);

		add(pName, BorderLayout.NORTH);
		add(pButton, BorderLayout.CENTER);
		add(pError, BorderLayout.SOUTH);
	}

	class ButtonListener implements ActionListener
	{

		Login frame;

		public ButtonListener(Login f)
		{
			frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent a) {
			String user = frame.tfUsername.getText();
			frame.errorMessage.setText("");
			try
			{
				// TODO error checking for non-existing users
				if(!frame.control.login(user) && !user.equalsIgnoreCase("admin")){
					new Error("The user " + user + " does not exist!");
				}else{
					if(user.equalsIgnoreCase("admin"))
					{
						Frame adminView = new AdminView(control);
						frame.setVisible(false);
						adminView.setVisible(true);
						frame.dispose();
					}
					else
					{
						Frame userView = new UserView(control, user);
						frame.setVisible(false);
						userView.setVisible(true);
						frame.dispose();
	
					}
				}
			}
			catch(IllegalArgumentException e)
			{
				frame.errorMessage.setText("Invalid User");
			}
			catch(NullPointerException e){
				frame.errorMessage.setText("Enter User");
			}
		}

	}


	public static void main(String[] args)
	{
		Control ctrl = new Control();
		if (!ctrl.backend.getUsers().contains(new User(null, "admin"))){
			ctrl.addUser("admin", "admin");
		}
		JFrame frame = new Login(ctrl);
		frame.setVisible(true);
	}
}
