package gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.registration.SignUpDialog;

public class AppStarter {
	public static void main(String args[]) {
		
/*		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		new SignUpDialog();
	}
}
