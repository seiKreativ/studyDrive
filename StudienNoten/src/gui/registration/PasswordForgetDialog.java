package gui.registration;

import data.exam.Email;
import store.StoreException;

import java.awt.*;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PasswordForgetDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8052600036105942804L;
	private final JPanel contentPanel = new JPanel();
	private JTextField emailField;


	public PasswordForgetDialog(SignUpDialog signUpDialog, String email) {
		super(signUpDialog);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 363, 179);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("E-mail:");
			lblNewLabel.setBounds(25, 63, 155, 14);
			contentPanel.add(lblNewLabel);
		}
		
		emailField = new JTextField();
		emailField.setBounds(25, 78, 210, 20);
		emailField.setText(email);
		contentPanel.add(emailField);

		ArrayList<Component> keyListenerComponents = new ArrayList<>();
		keyListenerComponents.add(emailField);
		
		JTextArea txtrBitteGebenSie = new JTextArea();
		txtrBitteGebenSie.setEditable(false);
		txtrBitteGebenSie.setFont(new Font("Arial Nova Light", Font.PLAIN, 13));
		txtrBitteGebenSie.setOpaque(true);
		txtrBitteGebenSie.setBackground(null);
		txtrBitteGebenSie.setText("Bitte gib hier deine E-Mail ein und wir senden \r\ndir in Kürze ein neues Passwort");
		txtrBitteGebenSie.setBounds(25, 11, 314, 34);
		contentPanel.add(txtrBitteGebenSie);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Neues Passwort zusenden");
				okButton.setActionCommand("OK");
				keyListenerComponents.add(okButton);
				okButton.addActionListener(e -> onOk());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener((e) -> {
					this.dispose(); 
				});
			}
		}

		for (Component c : keyListenerComponents) {
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						onOk();
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						dispose();
					}
				}
			});
		}
		setVisible(true);
	}

	private void onOk() {
		try {
			Email email = new Email();
			if (email.checkEmail(emailField.getText())) {
				email.postNewPasswortMail(emailField.getText());
				JOptionPane.showMessageDialog(this, "Email wurde zugesandt", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(this, "Diese Email wurde nicht registriert", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (StoreException | MessagingException | UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}