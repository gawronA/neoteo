package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Service
public interface PdfService {
    void generateReport(User user, OutputStream out);
}
