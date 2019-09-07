package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import data.exam.ExamContainer;
import data.exam.Student;
import gui.addFrame.AddFrame;
import gui.registration.SignUpDialog;
import store.ExamStore;
import store.StoreException;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5724418947028211664L;
	private JPanel contentPane;
	private JTextField durchschnittsnote;
	private ExamContainer container;
	private JButton btnAdd, btnDel, btnMod;
	/**
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	 */
	public MainFrame() {
		setTitle("Studium Noten Manager");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder();
		setBounds(100, 100, 800, 431);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAllgemein = new JMenu("Allgemein");
		menuBar.add(mnAllgemein);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(e -> onLogOut());
		mnAllgemein.add(mntmLogOut);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnAllgemein.add(mntmSave);
		
		JMenuItem mntmInformation = new JMenuItem("Information");
		mnAllgemein.add(mntmInformation);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel allExams = new TableExams();
		allExams.setBackground(Color.WHITE);
		allExams.setForeground(Color.WHITE);
		allExams.setBounds(359, 34, 427, 329);
		contentPane.add(allExams);

		JPanel grade = new JPanel();
		grade.setLayout(new BorderLayout(0, 0));
		grade.setBorder(new EmptyBorder(5, 5, 5, 5));
		grade.setBounds(0, 35, 349, 175);
		grade.setBackground(Color.WHITE);
		contentPane.add(grade);

		durchschnittsnote = new JTextField();
		durchschnittsnote.setBackground(Color.WHITE);
		durchschnittsnote.setFont(new Font("Tahoma", Font.PLAIN, 48));
		durchschnittsnote.setHorizontalAlignment(SwingConstants.CENTER);
		durchschnittsnote.setText("1,0");
		durchschnittsnote.setBorder(emptyBorder);
		durchschnittsnote.setEditable(false);
		durchschnittsnote.setOpaque(true);
		grade.add(durchschnittsnote, BorderLayout.CENTER);
		durchschnittsnote.setColumns(10);

		JLabel lblDurchschnittsnote = new JLabel("Durchschnittsnote");
		lblDurchschnittsnote.setBackground(Color.WHITE);
		lblDurchschnittsnote.setOpaque(true);
		grade.add(lblDurchschnittsnote, BorderLayout.NORTH);
		lblDurchschnittsnote.setHorizontalAlignment(SwingConstants.CENTER);
		lblDurchschnittsnote.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 210, 339, 165);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnAdd = new JButton("Pr\u00FCfung hinzuf\u00FCgen ");
		btnAdd.setBackground(Color.GRAY);
		btnAdd.setBounds(10, 5, 319, 41);
		panel.add(btnAdd);
		btnAdd.addActionListener(e -> onAdd());
		
		btnDel = new JButton("Pr\u00FCfung l\u00F6schen ");
		btnDel.setBackground(Color.GRAY);
		btnDel.setBounds(10, 57, 319, 41);
		panel.add(btnDel);
		btnDel.addActionListener(e -> onDel());
		
		btnMod = new JButton("Pr\u00FCfung ver\u00E4ndern");
		btnMod.setBackground(Color.GRAY);
		btnMod.setBounds(10, 109, 319, 41);
		panel.add(btnMod);
		btnMod.addActionListener(e -> onModify());

		try {
			container = ExamContainer.instance();
		} catch (StoreException e) {
			//Fehler tritt hier nicht auf
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					container.close();
				} catch (StoreException ex) {
					System.out.println("Database Error: closing failed " + ex.getMessage());
				}
				System.exit(0);
			}
		});

		setVisible(true);
	}

	private void onModify() {
		//Prüfung verändern
	}

	private void onAdd() {
		//Prüfung hinzufügen
		JDialog addDia = new AddFrame(this, "Neue Pr�fung hinzuf�gen");
		addDia.setVisible(true);
	}

	private void onDel() {
		// Prüfung löschen
	}

	private void onLogOut() {
		try {
			container.close();
			dispose();
			new SignUpDialog();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
