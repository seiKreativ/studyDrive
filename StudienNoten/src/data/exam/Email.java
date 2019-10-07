package data.exam;

import store.ExamStore;
import store.StoreException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Email {

    private ExamStore store;

    public Email() throws StoreException {
        store = ExamStore.instance();
    }

    public void postNewPasswortMail(String recipient) throws MessagingException, UnsupportedEncodingException, StoreException {

        Message msg = new MimeMessage(Email.getGMailSession());
        msg.setFrom(new InternetAddress("study0acc@gmail.com", "StudyAcc"));
        InternetAddress addressTo = new InternetAddress( recipient );
        msg.setRecipient( Message.RecipientType.TO, addressTo );

        String name = store.getUserName();
        String newPassword = Email.generatePasswort();
        store.changePasswort(newPassword);

        String message = "Hallo " + name +",\n" +
                "\n" +
                "Das neue Passwort f\u00fcr deinen StudyAcc-Account lautet: \n" +
                "\n" +
                newPassword + "\n" +
                "\n" +
                "Du kannst es unter \"Account\" und \"Passwort \u00e4ndern\" wieder \u00e4ndern. \n" +
                "\n" +
                "Viele Gr\u00fc\u00dfe, \n" +
                "\n" +
                "dein Team von StudyAcc";

        msg.setSubject( "SudyAcc: Neues Passwort" );
        msg.setContent( message, "text/plain" );

        Transport.send( msg );
    }

    private static Session getGMailSession() {
        final Properties props = new Properties();

        props.setProperty( "mail.smtp.host", "smtp.gmail.com" );
        props.setProperty( "mail.smtp.auth", "true" );
        props.setProperty( "mail.pop3.user", "study0acc@gmail.com" );
        props.setProperty( "mail.pop3.password", "pas138pas" );
        props.setProperty( "mail.smtp.port", "465" );
        props.setProperty( "mail.smtp.socketFactory.port", "465" );
        props.setProperty( "mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory" );
        props.setProperty( "mail.smtp.socketFactory.fallback", "false" );

        return Session.getInstance( props, new javax.mail.Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( props.getProperty( "mail.pop3.user" ),
                        props.getProperty( "mail.pop3.password" ) );
            }
        } );
    }

    public boolean checkEmail(String email) throws StoreException {
        return store.checkEmail(email);
    }

    private static String generatePasswort() {
        ArrayList<Integer> asciiCodes = new ArrayList<>();
        for (int i = 48; i < 58; i++)
            asciiCodes.add(i);
        for (int i = 65; i < 91; i++)
            asciiCodes.add(i);
        for (int i = 97; i < 123; i++)
            asciiCodes.add(i);
        Random r = new Random();
        String password = "";
        for (int i = 0; i < 8; i++) {
            int random = r.nextInt(asciiCodes.size());
            char tmp = (char) (int) asciiCodes.get(random);
            password = password.concat(String.valueOf(tmp));
        }
        return password;
    }
}
