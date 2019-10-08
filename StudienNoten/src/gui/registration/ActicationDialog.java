package gui.registration;

import data.exam.Email;
import data.exam.lecture.LectureContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class ActicationDialog extends JFrame {

    private JTextField txtCode;
    private JTextField txtNeuMail;
    private LectureContainer lecCon;
    private String username;

    public ActicationDialog() {
        super("Aktivierungs-Code");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        try {
            lecCon = LectureContainer.instance();
            username = lecCon.getUserName();
        } catch (StoreException e) {
            e.printStackTrace();
        }

        JLabel lblCode = new JLabel("Hier den Code eingeben:");
        add(lblCode);

        txtCode = new JTextField(7);
        add(txtCode);

        JButton activationCode = new JButton("Aktivieren");
        activationCode.addActionListener(e -> onAktivieren());
        add(activationCode);

        JButton erneutSenden = new JButton("Code erneut senden");
        erneutSenden.addActionListener(e -> onErneuSenden());
        add(erneutSenden);

        JLabel lblneuemail = new JLabel("Für User " + username + " neue Email-Adresse \n" +
                "eingeben und Code senden");
        add(lblneuemail);

        txtNeuMail = new JTextField(15);
        add(txtNeuMail);

        JButton neueMailOk = new JButton("Code an neue Mail senden");
        neueMailOk.addActionListener(e -> onNeueMail());
        add(neueMailOk);

        JButton back = new JButton("Zurück");
        back.addActionListener(e -> onBack());
        add(back);

        setVisible(true);
        pack();
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
                JOptionPane.showMessageDialog(this, "Error: Email schon registriert", "Error", JOptionPane.ERROR_MESSAGE);
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
            }
            else {
                JOptionPane.showMessageDialog(this, "Error: Falscher Code", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (StoreException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
