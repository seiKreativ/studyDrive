package gui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExceptionMessage extends JDialog {

    public ExceptionMessage(Frame owner, String title, String message) {
        super(owner, title);
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocation(200, 200);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(0);
        this.add(messageLabel, BorderLayout.CENTER);

        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> dispose());
        this.add(ok, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    public static int jaNeinDialog(Frame owner, String title, String message) {
        JDialog dialog = new JDialog(owner, title);

        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setLocation(200, 200);

        AtomicInteger returner = new AtomicInteger();

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(0);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> {
             returner.set(0);
            dialog.dispose();
        });
        south.add(ok);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            returner.set(1);
            dialog.dispose();
        });
        south.add(cancel);
        dialog.add(south, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);

        return returner.get();
    }
}
