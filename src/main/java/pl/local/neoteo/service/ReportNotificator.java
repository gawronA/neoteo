package pl.local.neoteo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;

import javax.ejb.Schedule;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;

@Service
public class ReportNotificator {

    @Autowired
    MailService mailService;
    @Autowired
    PdfService pdfService;
    @Autowired
    UserService userService;

    @Scheduled(fixedRate = 90000)
    public void notification() {
        var users = userService.getUsers();
        for(User user : users) {
            if(user.getProperty() == null || user.getProperty().getRecords().isEmpty()) continue;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfService.generateReport(user, out);

            var res = mailService.send(
                    "244009@edu.p.lodz.pl",
                    user.getEmail(),
                    "NeoTeo raport",
                    "<h3>Hello " +
                            user.getFirstName() +
                            " " +
                            user.getLastName() +
                            "</h3>" +
                            "<p>Raport bieżącego zużycia</p>",
                    "raport_" + user.getFirstName() + "_" + user.getLastName() + ".pdf",
                    out.toByteArray());
        }
    }
}
