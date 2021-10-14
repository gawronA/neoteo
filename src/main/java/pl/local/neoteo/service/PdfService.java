package pl.local.neoteo.service;

import pl.local.neoteo.entity.AppUser;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    public void generatePaymentsPdf(AppUser user, HttpServletResponse response);
}
