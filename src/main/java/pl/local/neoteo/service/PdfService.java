package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;

import javax.servlet.http.HttpServletResponse;

@Service
public interface PdfService {
    void generatePaymentsPdf(User user, HttpServletResponse response);
}
