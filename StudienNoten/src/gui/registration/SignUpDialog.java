package gui.registration;

import data.exam.IllegalInputException;
import data.exam.Student;
import gui.mainFrame.MainFrame;
import store.StoreException;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUpDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3348027184301675536L;
	private JTextField txtUsername;
	private JTextField txtPassword;

	public SignUpDialog() {
		setBounds(100, 100, 397, 255);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel lblSignIn = new JLabel("Sign In");
		lblSignIn.setFont(new Font("Arial Nova Light", Font.PLAIN, 26));
		lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn.setVerticalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblSignIn, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("Email");
		lblUsername.setBounds(35, 11, 307, 14);
		panel.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(35, 39, 269, 20);
		txtUsername.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		txtUsername.setBackground(Color.white);
		panel.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		// txtPassword.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
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
		btnSignIn.addActionListener(e -> onSignIn());
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

		ArrayList<Component> keyListenerComponents = new ArrayList<>();
		keyListenerComponents.add(btnSignIn);
		keyListenerComponents.add(txtUsername);
		keyListenerComponents.add(txtPassword);
		
		JLabel lblPasswortVergessen = new JLabel("Passwort vergessen");
		lblPasswortVergessen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new PasswordForgetDialog(SignUpDialog.this); 
			}
		});
		lblPasswortVergessen.setForeground(Color.BLUE);
		lblPasswortVergessen.setBounds(35, 126, 137, 14);
		panel.add(lblPasswortVergessen);
		for (Component c : keyListenerComponents) {
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						onSignIn();
					if (e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()) {
						SignUpDialog.this.dispose();
						RegistrationDialog dia = new RegistrationDialog();
						dia.setVisible(true);
					}
				}
			});
		}

		this.setVisible(true);

	}

	private void onSignIn() {
		try {
			new Student("Unnötig", txtUsername.getText(), txtPassword.getText(), false);
			dispose();
			new MainFrame();
		} catch (StoreException | IllegalInputException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}