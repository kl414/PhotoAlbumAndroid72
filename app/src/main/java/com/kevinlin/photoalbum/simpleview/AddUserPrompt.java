package cs213.photoAlbum.simpleview;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class AddUserPrompt extends JFrame {

	JLabel userName, fullName, error;
	JTextField tfUserName, tfFullName;
	JButton addBtn, cancelBtn;
	Control control;
	AdminView parent;
	JPanel userFrame;

	public AddUserPrompt(Control ctrl, AdminView av)
	{
		super("Add User");
		this.setSize(320, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		control = ctrl;
		parent = av;

		userName = new JLabel("Username: ");
		fullName = new JLabel("Full Name: ");
		error = new JLabel("");
		error.setForeground(Color.RED);

		tfUserName = new JTextField(20);
		tfFullName = new JTextField(20);

		addBtn = new JButton("Add");
		cancelBtn = new JButton("Cancel");

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		this.add(userName,c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		this.add(tfUserName,c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 0;
		this.add(fullName,c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		this.add(tfFullName,c);
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 1;
		this.add(addBtn,c);
		c.gridx = 1;
		c.gridwidth = 0;
		this.add(cancelBtn,c);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.weighty = 1;
		this.add(error,c);

		addBtn.addActionListener(new ButtonListener(this));
		cancelBtn.addActionListener(new ButtonListener(this));
		
	}	

	class ButtonListener implements ActionListener
	{
		AddUserPrompt frame;

		public ButtonListener(AddUserPrompt aup)
		{
			frame = aup;
		}

		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == frame.addBtn){
				if (tfUserName.getText().trim().equals("") || tfFullName.getText().trim().equals("")){
					error.setText("Error: Need proper username and fullname");
				}
				else{
					boolean temp;
					temp = control.addUser(tfUserName.getText(), tfFullName.getText());
					if (temp == false){
						error.setText("Error: User exists with that username");
					}
					else{
						parent.setEnabled(true);
						parent.requestFocus();
						dispose();
						parent.listModel.addElement(tfFullName.getText());
					}
				}
			}
			else if (e.getSource() == frame.cancelBtn){
				frame.dispose();
				frame.parent.setEnabled(true);
			}
		}
	}
}
