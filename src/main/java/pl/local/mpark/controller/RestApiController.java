package pl.local.mpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.repository.CardRepository;
import pl.local.mpark.service.AccountService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api")
public class RestApiController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "user/{cardNumber}", method = RequestMethod.GET, produces = "application/json")
    public AppUser getAppUserInJson(@PathVariable("cardNumber") String cardNumber) {
        AppUser user = cardRepository.findByNumber(cardNumber).getAppUser();
        return user;
    }

    @RequestMapping(value="user/{cardNumber}", method=RequestMethod.POST, produces = "application/json")
    public String postAppUserInJson(@PathVariable("cardNumber") String cardNumber, @RequestBody Map<String, Object> payload) {
        AppUser user = cardRepository.findByNumber(cardNumber).getAppUser();
        if(user == null) return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date fromDate = format.parse((String)payload.get("fromDate"));
            Date toDate = format.parse((String)payload.get("toDate"));
            if(accountService.makeParkingPayment(user, fromDate, toDate) != DatabaseResult.Success) return null;
            else return "OK";
        } catch(Exception ex) {
            return null;
        }

    }
}
