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

public class PasswordDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField altesPasswortField;
	private JPasswordField neuesPasswortField;
	private JPasswordField neuesPasswortReField;


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
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Passwort ändern");
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
