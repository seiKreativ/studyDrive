package gui.registration;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RegistrationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2685194346838147846L;
	private JPanel contentPane;
	private JTextField usernameTextfield;
	private JTextField emailTextField;
	private JTextField passwordTextfield;
	private JTextField repeatPasswordTextfield;

	/**
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registration frame = new registration();
					frame.setUndecorated(true); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	* Launch the application.
	*/
	public RegistrationDialog() {
		setBackground(Color.WHITE);
		setBounds(100, 100, 343, 386);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignUp = new JButton("Register");
		btnSignUp.setBackground(Color.DARK_GRAY);
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setFont(new Font("Arial Nova Light", Font.PLAIN, 20));
		btnSignUp.setBounds(88, 294, 147, 32);
		btnSignUp.setBorderPainted(false);
		contentPane.add(btnSignUp);
		
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
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		emailTextField.setColumns(10);
		emailTextField.setBackground(Color.WHITE);
		emailTextField.setBounds(32, 143, 261, 20);
		contentPane.add(emailTextField);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(32, 126, 261, 14);
		contentPane.add(lblEmail);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(73, 174, 1, 2);
		contentPane.add(separator_1);
		
		passwordTextfield = new JPasswordField();
		//passwordTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		passwordTextfield.setColumns(10);
		passwordTextfield.setBackground(Color.WHITE);
		passwordTextfield.setBounds(32, 191, 261, 20);
		contentPane.add(passwordTextfield);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(32, 174, 261, 14);
		contentPane.add(lblPassword);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(73, 222, 1, 2);
		contentPane.add(separator_2);
		
		repeatPasswordTextfield = new JPasswordField();
		//repeatPasswordTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		repeatPasswordTextfield.setColumns(10);
		repeatPasswordTextfield.setBackground(Color.WHITE);
		repeatPasswordTextfield.setBounds(32, 239, 261, 20);
		contentPane.add(repeatPasswordTextfield);
		
		JLabel lblRepeatPassword = new JLabel("Repeat Password:");
		lblRepeatPassword.setBounds(32, 222, 261, 14);
		contentPane.add(lblRepeatPassword);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(75, 264, 1, 2);
		contentPane.add(separator_3);
		
		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Arial Nova Light", Font.PLAIN, 26));
		lblRegistration.setBounds(32, 11, 171, 40);
		contentPane.add(lblRegistration);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 22));
		lblX.setBounds(298, 16, 21, 32);
		contentPane.add(lblX);
	}
}
