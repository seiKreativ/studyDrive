package gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.registration.SignUpDialog;

public class AppStarter {
    public static void main (String args[]) { 
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        new SignUpDialog();
    }
}
