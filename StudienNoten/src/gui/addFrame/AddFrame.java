package gui.addFrame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class AddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	public AddFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 128);
		this.setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(20, 11, 78, 14);
		contentPane.add(lblSemester);

		JLabel lblLeistungspunkte = new JLabel("LPs:");
		lblLeistungspunkte.setBounds(110, 11, 72, 14);
		contentPane.add(lblLeistungspunkte);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(190, 11, 403, 14);
		contentPane.add(lblName);

		JLabel lblNote = new JLabel("Note:");
		lblNote.setBounds(600, 11, 90, 14);
		contentPane.add(lblNote);

		JComboBox<String> comboBoxSem = new JComboBox<String>();
		comboBoxSem.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 12; i++) {
			comboBoxSem.addItem(Integer.toString(i));
		}
		comboBoxSem.addItem("-?-");
		comboBoxSem.setSelectedItem("-?-");
		comboBoxSem.setBounds(20, 36, 50, 20);
		contentPane.add(comboBoxSem);

		textField = new JTextField();
		textField.setBounds(108, 36, 50, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(190, 36, 380, 20);
		contentPane.add(textField_1);

		JComboBox<String> comboBoxNoten = new JComboBox<String>();
		comboBoxNoten.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 4; i++) {
			comboBoxNoten.addItem(Integer.toString(i) + ".0");
			comboBoxNoten.addItem(Integer.toString(i) + ".3");
			comboBoxNoten.addItem(Integer.toString(i) + ".7");

		}
		comboBoxNoten.addItem("5.0");
		comboBoxNoten.setSelectedItem("1.0");
		comboBoxNoten.setBounds(600, 36, 50, 20);
		contentPane.add(comboBoxNoten);

		JButton btnApply = new JButton("Apply");
		btnApply.setBackground(Color.GRAY);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 	hier noch eventhandling einfügen
				 */
			}
		});
		
		btnApply.setBounds(265, 94, 90, 23);
		contentPane.add(btnApply);

		JButton btnClose = new JButton("Close");
		btnClose.setBackground(Color.GRAY);
		btnClose.addActionListener(e -> {
				if (JOptionPane.showConfirmDialog(null, "Soll ohne die Werte zu speichern wirklich geschlossen werden?",
						"Warnung!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					this.dispose(); // yes option
				} else {
					
				}
			}
		);
		btnClose.setBounds(365, 94, 90, 23);
		contentPane.add(btnClose);
	}
}
