package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service
public interface PropertyService {
    DatabaseResult updateProperty(Property property);
    Property getProperty(long id);
    List<Property> getProperties();
}
