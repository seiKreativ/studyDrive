package gui.registration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SignUpDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3348027184301675536L;
	private JTextField txtUsername;
	private JTextField txtPassword;

	/**
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signUpDialog dialog = new signUpDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public SignUpDialog() {
		setBounds(100, 100, 397, 255);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblSignIn = new JLabel("Sign In");
		lblSignIn.setFont(new Font("Arial Nova Light", Font.PLAIN, 26));
		lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn.setVerticalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblSignIn, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(35, 11, 307, 14);
		panel.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(35, 39, 269, 20);
		txtUsername.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		txtUsername.setBackground(Color.white);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		//txtPassword.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		txtPassword.setColumns(10);
		txtPassword.setBackground(Color.WHITE);
		txtPassword.setBounds(35, 98, 269, 20);
		panel.add(txtPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(35, 70, 307, 14);
		panel.add(lblPassword);
		
		JPanel Options = new JPanel();
		getContentPane().add(Options, BorderLayout.SOUTH);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setBackground(Color.DARK_GRAY);
		btnSignIn.setForeground(Color.WHITE);
		btnSignIn.setFont(new Font("Arial Nova Light", Font.PLAIN, 13));
		btnSignIn.setBorderPainted(false);
		Options.add(btnSignIn);
		
		JButton btnRegisterNow = new JButton("Register Now");
		btnRegisterNow.setBackground(Color.DARK_GRAY);
		btnRegisterNow.setForeground(Color.WHITE);
		btnRegisterNow.setFont(new Font("Arial Nova Light", Font.PLAIN, 13));
		btnRegisterNow.setBorderPainted(false);
		btnRegisterNow.addActionListener(e -> {
			/*
			 * Falls user noch nicht mitglied ist muss er sich erst neu anmelden
			 */
			this.dispose(); 
			RegistrationDialog dia = new RegistrationDialog(); 
			dia.setVisible(true);
		});
		Options.add(btnRegisterNow);

	}
}
