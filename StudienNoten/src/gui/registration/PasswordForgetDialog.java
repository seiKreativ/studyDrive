package gui.registration;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.Font;

public class PasswordForgetDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8052600036105942804L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField altesPasswortField;


	public PasswordForgetDialog(SignUpDialog signUpDialog) {
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
		
		altesPasswortField = new JPasswordField();
		altesPasswortField.setBounds(25, 78, 210, 20);
		contentPanel.add(altesPasswortField);
		
		JTextArea txtrBitteGebenSie = new JTextArea();
		txtrBitteGebenSie.setEditable(false);
		txtrBitteGebenSie.setFont(new Font("Arial Nova Light", Font.PLAIN, 13));
		txtrBitteGebenSie.setOpaque(true);
		txtrBitteGebenSie.setBackground(null);
		txtrBitteGebenSie.setText("Bitte geben Sie hier Ihre E-Mail ein und wir senden Ihnen \r\nin Kürze ein neues Passwort");
		txtrBitteGebenSie.setBounds(25, 11, 314, 34);
		contentPanel.add(txtrBitteGebenSie);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Neues Passwort zusenden");
				okButton.setActionCommand("OK");
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
		setVisible(true);
	}
}