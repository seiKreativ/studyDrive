package gui.registration;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import gui.mainFrame.MainFrame;
import store.StoreException;

public class RegistrationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3198864217046720627L;
	private JPanel contentPane;
	private JTextField emailTextfield;
	private JTextField passwordTextfield;
	private JTextField repeatPasswordTextfield;
	private JTextField nameTextfield;

	
	
	
	public RegistrationDialog() {
		setBackground(Color.WHITE);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 364);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignUp = new JButton("Register");
		btnSignUp.setBackground(Color.DARK_GRAY);
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setFont(new Font("Arial Nova Light", Font.PLAIN, 20));
		btnSignUp.setBounds(88, 297, 147, 32);
		btnSignUp.setBorderPainted(false);
		contentPane.add(btnSignUp);
		btnSignUp.addActionListener(e -> onSignUp());
		
		nameTextfield = new JTextField();
		nameTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		nameTextfield.setColumns(10);
		nameTextfield.setBackground(Color.WHITE);
		nameTextfield.setBounds(32, 98, 261, 20);
		contentPane.add(nameTextfield);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(32, 81, 261, 14);
		contentPane.add(lblName);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(73, 116, 1, 2);
		contentPane.add(separator_1);
		
		emailTextfield = new JTextField();
		emailTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		emailTextfield.setBackground(Color.WHITE);
		emailTextfield.setBounds(32, 146, 261, 20);
		contentPane.add(emailTextfield);
		emailTextfield.setColumns(10);
		
		JLabel lblUsername = new JLabel("Email");
		lblUsername.setBounds(32, 129, 261, 14);
		contentPane.add(lblUsername);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(73, 177, 1, 2);
		contentPane.add(separator);
		
		passwordTextfield = new JPasswordField();
		passwordTextfield.setColumns(10);
		passwordTextfield.setBackground(Color.WHITE);
		passwordTextfield.setBounds(32, 194, 261, 20);
		contentPane.add(passwordTextfield);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(32, 177, 261, 14);
		contentPane.add(lblPassword);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(73, 269, 1, 2);
		contentPane.add(separator_2);
		
		repeatPasswordTextfield = new JPasswordField();
		//repeatPasswordTextfield.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
		repeatPasswordTextfield.setColumns(10);
		repeatPasswordTextfield.setBackground(Color.WHITE);
		repeatPasswordTextfield.setBounds(32, 242, 261, 20);
		contentPane.add(repeatPasswordTextfield);
		
		JLabel lblRepeatPassword = new JLabel("Repeat Password:");
		lblRepeatPassword.setBounds(32, 225, 261, 14);
		contentPane.add(lblRepeatPassword);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(75, 311, 1, 2);
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

		ArrayList<Component> keyListenerComponents = new ArrayList<>();
		keyListenerComponents.add(emailTextfield);
		keyListenerComponents.add(nameTextfield);
		keyListenerComponents.add(passwordTextfield);
		keyListenerComponents.add(repeatPasswordTextfield);
		keyListenerComponents.add(btnSignUp);
		for (Component c : keyListenerComponents) {
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						onSignUp();
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						RegistrationDialog.this.dispose();
						new SignUpDialog();
					}
				}
			});
		}

		setUndecorated(true); 
		setVisible(true);
	}

	private void onSignUp() {
		if (passwordTextfield.getText().equals(repeatPasswordTextfield.getText())) {
			try {
				new Student(nameTextfield.getText(), emailTextfield.getText(), passwordTextfield.getText(), true);
				dispose();
				new MainFrame();
			} catch (StoreException | IllegalInputException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(this, "Error: passwords not the same", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
