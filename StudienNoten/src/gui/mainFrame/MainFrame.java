package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gui.addFrame.AddFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724418947028211664L;
	private JPanel contentPane;
	private JTextField durchschnittsnote;
	private Student stud; 
	
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
	public MainFrame(Student stud) {
		setTitle("Studium Noten Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder();
		setBounds(100, 100, 800, 431);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAllgemein = new JMenu("Allgemein");
		menuBar.add(mnAllgemein);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
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
		
		JButton btnAdd = new JButton("Pr\u00FCfung hinzuf\u00FCgen ");
		btnAdd.setBackground(Color.GRAY);
		btnAdd.setBounds(10, 5, 319, 41);
		panel.add(btnAdd);
		
		btnAdd.addActionListener(e -> {
			JDialog addDia = new AddFrame(this, "Neue Prüfung hinzufügen"); 	
			addDia.setVisible(true);
		});
		
		JButton btnDel = new JButton("Pr\u00FCfung l\u00F6schen ");
		btnDel.setBackground(Color.GRAY);
		btnDel.setBounds(10, 57, 319, 41);
		panel.add(btnDel);
		
		JButton btnMod = new JButton("Pr\u00FCfung ver\u00E4ndern");
		btnMod.setBackground(Color.GRAY);
		btnMod.setBounds(10, 109, 319, 41);
		panel.add(btnMod);
		
	}
}
