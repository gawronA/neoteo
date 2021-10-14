package pl.local.neoteo.service;

import pl.local.neoteo.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    void generatePaymentsPdf(User user, HttpServletResponse response);
}
