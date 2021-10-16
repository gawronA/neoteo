package pl.local.neoteo.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public boolean send(String from, String to, String subject, String content) {
        Email emailFrom = new Email(from);
        Email emailTo = new Email(to);
        Content contentt = new Content("text/html", content);
        Mail mail = new Mail(emailFrom, subject, emailTo, contentt);
        SendGrid sg = new SendGrid("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");
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
