package pl.local.neoteo.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public boolean send(String from, String to, String subject, String content) {
        return send(from, to, subject, content, null, null);
    }

    @Override
    public boolean send(String from, String to, String subject, String content, String pdfAttachmentName, byte[] pdfAttachment) {
        Email emailFrom = new Email(from);
        Email emailTo = new Email(to);
        Content contentt = new Content("text/html", content);

        Mail mail = new Mail(emailFrom, subject, emailTo, contentt);

        if(pdfAttachment != null) {
            Attachments attachments = new Attachments();
            Base64 x = new Base64();
            String encodedString = x.encodeAsString(pdfAttachment);
            attachments.setContent(encodedString);
            attachments.setDisposition("attachment");
            attachments.setFilename(pdfAttachmentName);
            attachments.setType("application/pdf");
            mail.addAttachments(attachments);
        }

        SendGrid sg = new SendGrid("***");
        Request req = new Request();
        try {
            req.setMethod(Method.POST);
            req.setEndpoint("mail/send");
            req.setBody(mail.build());
            Response response = sg.api(req);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
}
