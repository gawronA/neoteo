package pl.local.neoteo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> postRecord(@RequestBody Map<String, Object> payload) {
        long propertyId = ((Number)payload.get("propertyId")).longValue();
        long utilityTypeId = ((Number)payload.get("utilityTypeId")).longValue();
        double amount = ((Number)payload.get("amount")).doubleValue();

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
            record.setPrice(amount * utilityType.getPrice());
            record.setType(utilityType);
            record.setProperty(property);
            record.setDate(Calendar.getInstance().getTime());

            var result = recordService.addRecord(record);
            if(result != DatabaseResult.Success) throw new Exception();
            else return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }
}
