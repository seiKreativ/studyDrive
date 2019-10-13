package data.exam;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import store.StoreException;
import store.UserInformationStore;

public class Email {

    private static UserInformationStore store;

    public Email() throws StoreException {
        store = UserInformationStore.instance();
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
                "das neue Passwort f\u00fcr deinen StudyAcc-Account lautet: \n" +
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

    public static void postPdfMail(String filename) throws MessagingException, UnsupportedEncodingException, StoreException {

        store = UserInformationStore.instance();
        String name = store.getUserName();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String uhrzeit = sdf.format(new Date());

        String message = "Hallo " + name +",\n" +
                "\n" +
                "im Anhang findest du die um " + uhrzeit + " Uhr generierte \u00dcbersicht \u00fcber deine " +
                "Noten (bzw. Vorlesungen/\u00dcbungsbl\u00e4tter). \n" +
                "\n" +
                "Viele Gr\u00fc\u00dfe, \n" +
                "\n" +
                "dein Team von StudyAcc \n";

        MimeMultipart content = new MimeMultipart( "mixed" );

        MimeBodyPart text = new MimeBodyPart();
        text.setText( message );
        content.addBodyPart( text );

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(
                new DataHandler( new FileDataSource( filename ) ) );
        messageBodyPart.setFileName( new File(filename).getName() );
        content.addBodyPart( messageBodyPart );

        Message msg = new MimeMessage(Email.getGMailSession());
        msg.setFrom(new InternetAddress("study0acc@gmail.com", "StudyAcc"));
        InternetAddress addressTo = new InternetAddress( store.getUserEmail() );
        msg.setRecipient( Message.RecipientType.TO, addressTo );

        msg.setSubject( "SudyAcc: Generierte \u00dcbersicht" );
        msg.setText(message);
        msg.setContent(content);

        Transport.send( msg );
    }

    public static void postNewActivationMail(String recipient, String name, boolean isNewCode) throws MessagingException, UnsupportedEncodingException, StoreException {

        Message msg = new MimeMessage(Email.getGMailSession());
        msg.setFrom(new InternetAddress("study0acc@gmail.com", "StudyAcc"));
        InternetAddress addressTo = new InternetAddress( recipient );
        msg.setRecipient( Message.RecipientType.TO, addressTo );

        String code;
        String betreff;
        store = UserInformationStore.instance();

        if (isNewCode) {
            betreff = "StudyAcc: Dein Aktivierungs-Code";
            code = store.getUsercode();
        }
        else {
            betreff = "StudyAcc: Dein Aktivierungs-Code";
            code = Email.generateActivationCode();
            store.setActivationCode(code);
        }

        String message = "Hallo " + name +",\n" +
                "\n" +
                "der Aktivierungs-Code f\u00fcr deinen StudyAcc-Account lautet: \n" +
                "\n" +
                code + "\n" +
                "\n" +
                "Du wirst aufgefordert, ihn beim ersten Log-In einzugeben. \n" +
                "\n" +
                "Viele Gr\u00fc\u00dfe, \n" +
                "\n" +
                "dein Team von StudyAcc";

        msg.setSubject( betreff );
        msg.setContent( message, "text/plain" );

        Transport.send( msg );
    }

    private static String generateActivationCode() {
        ArrayList<Integer> asciiCodes = new ArrayList<>();
        for (int i = 48; i < 58; i++)
            asciiCodes.add(i);
        Random r = new Random();
        String code = "";
        for (int i = 0; i < 5; i++) {
            int random = r.nextInt(asciiCodes.size());
            char tmp = (char) (int) asciiCodes.get(random);
            code = code.concat(String.valueOf(tmp));
        }
        return code;
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
