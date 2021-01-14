package program;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
public class SendEmailWithPDFGeneration {
    public static void main(String[] args) {
	    	SendEmailWithPDFGeneration sendmail = new SendEmailWithPDFGeneration();
	        sendmail.mail();
	    }
	
	 public void mail() {
		final String username = "yourmail@gmail.com";
	        final String password = "*********";
	        Properties prop = new Properties();
	        prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true");
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });
	        ByteArrayOutputStream outputStream = null;
	        try {           
	            outputStream = new ByteArrayOutputStream();
	            generatepdf(outputStream);
	            byte[] bytes = outputStream.toByteArray();
	            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	            MimeBodyPart pdfBodyPart = new MimeBodyPart();
	            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	            pdfBodyPart.setFileName("sample.pdf");
	            MimeMultipart mimeMultipart = new MimeMultipart();
	            mimeMultipart.addBodyPart(pdfBodyPart);
	            MimeMessage mimeMessage = new MimeMessage(session);
	            mimeMessage.setFrom(new InternetAddress(username));
	            mimeMessage.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("sendermail@gmail.com")
	            );
	            mimeMessage.setSubject("Dynamic PDF Geration ");
	            mimeMessage.setText("Dear user,"
	                    + "\n\n This is testing email");
	            mimeMessage.setContent(mimeMultipart);
	            Transport.send(mimeMessage);
	            System.out.println("Email sent");         
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            if(null != outputStream) {
	                try { outputStream.close(); outputStream = null; }
	                catch(Exception ex) { }
	            }
	        }
	    }
	    public void generatepdf(OutputStream outputStream) throws Exception {
	    	LocalDateTime dateandtime = LocalDateTime.now();
	        Document document = new Document();
	        PdfWriter.getInstance(document, outputStream);
	        document.open();
	        document.addTitle("Test PDF");
	        document.addSubject("Testing email PDF");
	        document.addKeywords("Username");
	        document.addAuthor("suresh");
	        document.addCreator("suresh");
	        Paragraph paragraph = new Paragraph();
	        paragraph.add(new Chunk("This is testing pdf Generation "+dateandtime));
	        document.add(paragraph);
	        document.close();
	    }
	     
	
}
