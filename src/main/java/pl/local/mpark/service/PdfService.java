package pl.local.mpark.service;

import pl.local.mpark.entity.AppUser;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    public void generatePaymentsPdf(AppUser user, HttpServletResponse response);
}
