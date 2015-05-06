package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * @author Hongjie Lin
 *
 */
public class Error extends JDialog{
	
	public Error(String msg){
		super(new JFrame(), "Error");
		this.setSize(300,100);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		JLabel err = new JLabel(msg, SwingConstants.CENTER);
		err.setForeground(Color.RED);
		add(err, BorderLayout.CENTER);
		
		JButton close = new JButton("close");
		JPanel pButton = new JPanel(new FlowLayout());
		pButton.add(close);
		close.addActionListener(new ButtonListener());
		add(pButton, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	protected class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
		}
		
	}
}
