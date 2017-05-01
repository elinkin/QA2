package cvlv;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class helps to connect and work with gmail account.
 */
public class MailHelper {
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "465";
    Store store;

    public MailHelper(String login, String password) throws javax.mail.MessagingException {
        this.store = connectToMail(login, password);
    }

    /**
     * Specify mail service settings for connect
     *
     * @return properties
     */
    public Properties createMailProperties() {
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.host", HOST);
        mailServerProperties.put("mail.smtp.socketFactory.port", PORT);
        mailServerProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.port", PORT);
        return mailServerProperties;
    }

    /**
     * Method marks all cv.lv password reminder messages in folder as Deleted
     *
     * @param folder folder to work with
     * @throws javax.mail.MessagingException
     */
    public void cleanupMailbox(String folder) throws javax.mail.MessagingException {
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_WRITE);

        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            if (message.getSubject().equals("CV-Online: paroles atgadinajums")) {
                message.setFlag(Flags.Flag.DELETED, true);
            }
        }
    }

    /**
     * Method creates mail session
     *
     * @return session
     */
    public javax.mail.Session createMailSession() {
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(createMailProperties(), null);
        return session;
    }

    /**
     * Method creates a store to work in
     *
     * @param login    mail login
     * @param password mail password
     * @return - store
     * @throws MessagingException
     */
    public Store connectToMail(String login, String password) throws javax.mail.MessagingException {
        Store store = createMailSession().getStore("imaps");
        store.connect(HOST, login, password);
        return store;
    }

    private String getMailBody(Part part) throws MessagingException, IOException, javax.mail.MessagingException {
        if (part.isMimeType("text/*")) {
            return (String) part.getContent();
        }

        if (part.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) part.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = getMailBody(bp);
                    }
                } else if (bp.isMimeType("text/html")) {
                    String s = getMailBody(bp);
                    if (s != null)
                        return s;
                } else {
                    return getMailBody(bp);
                }
            }
            return text;
        } else if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getMailBody(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

    /**
     * Method returns last received password recovery message from cv.lv
     *
     * @param folder folder to look
     * @return message
     * @throws MessagingException
     * @throws IOException
     */
    public String getMail(String folder) throws IOException, javax.mail.MessagingException, MessagingException {
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_ONLY);
        for (int i = 0; i < 120; i++) {
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                if (message.getSubject().equals("CV-Online: paroles atgadinajums")) {
                    String body = getMailBody(message);
                    Pattern pattern = Pattern.compile("(http://www.cv.lv/password/confirm/[a-z0-9]+)");
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                }
            }
            System.out.println("Waiting for email…");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        return null;
    }
}
