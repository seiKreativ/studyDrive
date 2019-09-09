package gui.registration;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import data.exam.IllegalInputException;
import data.exam.Student;
import gui.ExceptionMessage;
import gui.mainFrame.MainFrame;
import store.StoreException;

public class RegistrationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3198864217046720627L;
	private JPanel contentPane;
	private JTextField usernameTextfield;
	private JTextField passwordTextfield;
	private JTextField repeatPasswordTextfield;

	
	
	
	public RegistrationDialog() {
		setBackground(Color.WHITE);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 304);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignUp = new JButton("Register");
		btnSignUp.setBackground(Color.DARK_GRAY);
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setFont(new Font("Arial Nova Light", Font.PLAIN, 20));
		btnSignUp.setBounds(88, 250, 147, 32);
		btnSignUp.setBorderPainted(false);
		contentPane.add(btnSignUp);
		btnSignUp.addActionListener(e -> onSignUp());
		
		usernameTextfield = new JTextField();
		usernameTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		usernameTextfield.setBackground(Color.WHITE);
		usernameTextfield.setBounds(32, 99, 261, 20);
		contentPane.add(usernameTextfield);
		usernameTextfield.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(32, 82, 261, 14);
		contentPane.add(lblUsername);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(73, 130, 1, 2);
		contentPane.add(separator);
		
		passwordTextfield = new JPasswordField();
		//passwordTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		passwordTextfield.setColumns(10);
		passwordTextfield.setBackground(Color.WHITE);
		passwordTextfield.setBounds(32, 147, 261, 20);
		contentPane.add(passwordTextfield);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(32, 130, 261, 14);
		contentPane.add(lblPassword);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(73, 222, 1, 2);
		contentPane.add(separator_2);
		
		repeatPasswordTextfield = new JPasswordField();
		//repeatPasswordTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		repeatPasswordTextfield.setColumns(10);
		repeatPasswordTextfield.setBackground(Color.WHITE);
		repeatPasswordTextfield.setBounds(32, 195, 261, 20);
		contentPane.add(repeatPasswordTextfield);
		
		JLabel lblRepeatPassword = new JLabel("Repeat Password:");
		lblRepeatPassword.setBounds(32, 178, 261, 14);
		contentPane.add(lblRepeatPassword);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(75, 264, 1, 2);
		contentPane.add(separator_3);
		
		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Arial Nova Light", Font.PLAIN, 26));
		lblRegistration.setBounds(96, 11, 139, 40);
		contentPane.add(lblRegistration);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new SignUpDialog();
			}
		});
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 22));
		lblX.setBounds(298, 16, 21, 32);
		contentPane.add(lblX);

		setUndecorated(true); 
		setVisible(true);
	}

	private void onSignUp() {
		if (passwordTextfield.getText().equals(repeatPasswordTextfield.getText())) {
			try {
				new Student(usernameTextfield.getText(), passwordTextfield.getText(), true);
				dispose();
				new MainFrame();
			} catch (StoreException | IllegalInputException e) {
				//new ExceptionMessage(null, "Error", "Error: " + e.getMessage());
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			//new ExceptionMessage(null, "Error", "Error: passwords not the same");
			JOptionPane.showMessageDialog(this, "Error: passwords not the same", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
