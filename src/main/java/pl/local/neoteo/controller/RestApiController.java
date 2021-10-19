package pl.local.neoteo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.UserRepository;
import pl.local.neoteo.service.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api")
public class RestApiController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UtilityTypeService utilityTypeService;

    @RequestMapping(value = "utilityTypes", method = RequestMethod.GET, produces = "application/json")
    public List<UtilityType> getUtilityTypes() {
        return utilityTypeService.getUtilityTypes();
    }

    @RequestMapping(value="record", method=RequestMethod.POST, produces = "application/json")
    public String postRecord(@RequestBody Map<String, Object> payload) {
        long propertyId = ((Number)payload.get("propertyId")).longValue();
        long utilityTypeId = ((Number)payload.get("utilityTypeId")).longValue();
        double amount = (double)payload.get("amount");

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            //Date date = format.parse((String)payload.get("date"));

            Property property = propertyService.getProperty(propertyId);
            if(property == null) throw new Exception();
            if(property.getRecords() == null) property.setRecords(new HashSet<>());
            UtilityType utilityType = utilityTypeService.getUtilityType(utilityTypeId);
            if(utilityType == null) throw new Exception();

            Record record = new Record();
            record.setAmount(amount);
            record.setPrice(utilityType.getPrice());
            record.setType(utilityType);
            record.setDate(Calendar.getInstance().getTime());

            property.getRecords().add(record);
            if(propertyService.updateProperty(property) != DatabaseResult.Success) return null;
            else return "OK";
        } catch(Exception ex) {
            return null;
        }

    }
}
