package gui.registration;

import data.exam.lecture.LectureContainer;
import store.StoreException;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PasswordDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8052600036105942804L;
	private final JPanel contentPanel = new JPanel();
	private JTextField altesPasswortField;
	private JTextField neuesPasswortField;
	private JTextField neuesPasswortReField;


	public PasswordDialog(JFrame owner) {
		super(owner);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 363, 206);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Altes Passwort:");
			lblNewLabel.setBounds(28, 10, 155, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNeuesPasswort = new JLabel("Neues Passwort:");
			lblNeuesPasswort.setBounds(28, 50, 155, 14);
			contentPanel.add(lblNeuesPasswort);
		}
		{
			JLabel lblNeuesPasswortWiederholen = new JLabel("Neues Passwort wiederholen:");
			lblNeuesPasswortWiederholen.setBounds(28, 90, 155, 14);
			contentPanel.add(lblNeuesPasswortWiederholen);
		}
		
		altesPasswortField = new JPasswordField();
		altesPasswortField.setBounds(28, 25, 210, 20);
		contentPanel.add(altesPasswortField);
		
		neuesPasswortField = new JPasswordField();
		neuesPasswortField.setBounds(28, 65, 210, 20);
		contentPanel.add(neuesPasswortField);
		
		neuesPasswortReField = new JPasswordField();
		neuesPasswortReField.setBounds(28, 105, 210, 20);
		contentPanel.add(neuesPasswortReField);

		ArrayList<Component> keyListenerComponents = new ArrayList<>();
		keyListenerComponents.add(altesPasswortField);
		keyListenerComponents.add(neuesPasswortField);
		keyListenerComponents.add(neuesPasswortReField);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Passwort ändern");
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
			LectureContainer con = LectureContainer.instance();
			if (!altesPasswortField.getText().equals(con.getPassword())) {
				JOptionPane.showMessageDialog(this, "Error: Falsches altes Passwort", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!neuesPasswortField.getText().equals(neuesPasswortReField.getText())) {
				JOptionPane.showMessageDialog(this, "Error: Passwörter stimmen nicht überein", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (neuesPasswortField.getText().length() <= 2) {
				JOptionPane.showMessageDialog(this, "Error: Passwort muss mindestens drei Zeichen haben", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			con.changePassword(neuesPasswortField.getText());
			JOptionPane.showMessageDialog(this, "Passwort erfolgreich geändert", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
