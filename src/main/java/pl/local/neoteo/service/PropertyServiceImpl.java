package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.Property;
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

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyExtServiceImpl propertyExtService) {
        this.propertyRepository = propertyRepository;
        this.propertyExtService = propertyExtService;
    }

    private DatabaseResult addProperty(Property property) {
        property.setId(0);
        try {
            this.propertyExtService.save(property);
        }
        catch (Exception ex) {
            return DatabaseResult.Error;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult updateProperty(Property property) {
        var dbProperty = this.propertyRepository.findById(property.getId());
        if(dbProperty.isEmpty()) return DatabaseResult.Error;

        return addProperty(property);
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
