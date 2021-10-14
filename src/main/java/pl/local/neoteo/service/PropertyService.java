package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.Subscription;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service//("subscriptionService")
public interface PropertyService {
    Property getProperty(long id);
    List<Property> getProperties();
    DatabaseResult activateProperty(long id);
}
