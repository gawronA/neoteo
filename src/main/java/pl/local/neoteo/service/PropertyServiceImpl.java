package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.PropertyRepository;

import java.util.List;

@Service
class PropertyExtServiceImpl {
    private final PropertyRepository propertyRepository;

    public PropertyExtServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(Property property) {
        this.propertyRepository.save(property);
    }
}

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyExtServiceImpl propertyExtService;
    private final UserService userService;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyExtServiceImpl propertyExtService, UserService userService) {
        this.propertyRepository = propertyRepository;
        this.propertyExtService = propertyExtService;
        this.userService = userService;
    }

    public DatabaseResult createProperty(Property property, String username) {
        property.setId(0);
        User user = userService.getUserByEmail(username);
        property.setUser(user);
        user.setProperty(property);
        return userService.updateUser(user);
    }

    public DatabaseResult updateProperty(Property property) {
        var dbProperty = this.propertyRepository.findById(property.getId());
        if(dbProperty.isEmpty()) return DatabaseResult.Error;

        try {
            this.propertyExtService.save(property);
        }
        catch (Exception ex) {
            return DatabaseResult.Error;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public Property getProperty(long id) {
        var prop = this.propertyRepository.findById(id);
        return prop.orElse(null);
    }
    @Transactional
    public List<Property> getProperties() {
        return this.propertyRepository.findAll();
    }

}
