package pl.local.neoteo.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    boolean send(String from, String to, String subject, String content);
    boolean send(String from, String to, String subject, String content, String pdfAttachmentName, byte[] pdfAttachment);
}
