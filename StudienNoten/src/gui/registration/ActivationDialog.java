package gui.registration;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import data.exam.Email;
import data.exam.lecture.LectureContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActivationDialog extends JFrame {

	private JPanel contentPane;
	private LectureContainer lecCon;
	private String username;
	private JTextField txtCode;
	private JTextField txtNeuMail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActivationDialog frame = new ActivationDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ActivationDialog() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 245);
		try {
			lecCon = LectureContainer.instance();
			username = lecCon.getUserName();
		} catch (StoreException e) {
			e.printStackTrace();
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCode = new JLabel("Hier den Aktivierungcode eingeben: ");
		lblCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblCode.setVerticalAlignment(SwingConstants.CENTER);
		lblCode.setBounds(93, 11, 259, 14);
		contentPane.add(lblCode);

		txtCode = new JTextField();
		txtCode.setBounds(154, 36, 136, 20);
		contentPane.add(txtCode);
		txtCode.setColumns(10);

		JButton btnActivationCode = new JButton("Aktivieren");
		btnActivationCode.addActionListener(e -> onAktivieren());
		btnActivationCode.setBounds(10, 67, 201, 23);
		contentPane.add(btnActivationCode);

		JButton btnCodeErneutZusenden = new JButton("Code erneut zusenden");
		btnCodeErneutZusenden.addActionListener(e -> onErneuSenden());
		btnCodeErneutZusenden.setBounds(225, 67, 201, 23);
		contentPane.add(btnCodeErneutZusenden);

		JLabel lblNeueEmail = new JLabel(
				"Für User " + username + " neue Email-Adresse eingeben und neuen Code zusenden:");
		lblNeueEmail.setVerticalAlignment(SwingConstants.CENTER);
		lblNeueEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeueEmail.setBounds(20, 107, 406, 20);
		contentPane.add(lblNeueEmail);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 107, 416, -11);
		contentPane.add(separator);

		txtNeuMail = new JTextField(15);
		txtNeuMail.setBounds(93, 138, 259, 20);
		contentPane.add(txtNeuMail);
		txtNeuMail.setColumns(10);

		JButton btnNeueMailOk = new JButton("Neuen Code zusenden");
		btnNeueMailOk.addActionListener(e -> onNeueMail());
		btnNeueMailOk.setBounds(10, 170, 201, 23);
		contentPane.add(btnNeueMailOk);

		JButton btnBack = new JButton("Zurück");
		btnBack.addActionListener(e -> onBack());
		btnBack.setBounds(225, 170, 201, 23);
		contentPane.add(btnBack);
		
		this.setVisible(true);
	}

	private void onBack() {
		dispose();
		new SignUpDialog();
	}

	private void onNeueMail() {
		try {
			if (!lecCon.checkMailAreadyExists(txtNeuMail.getText())) {
				lecCon.setNewMail(txtNeuMail.getText());
				Email.postNewActivationMail(txtNeuMail.getText(), lecCon.getUserName(), false);
			} else {
				JOptionPane.showMessageDialog(this, "Error: Email schon registriert", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(this, "Email wurde gesendet", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);
			new SignUpDialog();
		} catch (StoreException | MessagingException | UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onErneuSenden() {
		try {
			Email.postNewActivationMail(lecCon.getUserEmail(), lecCon.getUserName(), true);
			JOptionPane.showMessageDialog(this, "Email wurde gesendet", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);
		} catch (MessagingException | StoreException | UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onAktivieren() {
		try {
			if (lecCon.checkActivationCode(txtCode.getText())) {
				lecCon.setActivated();
				dispose();
				new MainFrame();
			} else {
				JOptionPane.showMessageDialog(this, "Error: Falscher Code", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
