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

@Service//("propertyService")
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyExtServiceImpl propertyExtService;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyExtServiceImpl propertyExtService) {
        this.propertyRepository = propertyRepository;
        this.propertyExtService = propertyExtService;
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

    public DatabaseResult activateProperty(long id) {
        var prop = this.propertyRepository.findById(id);
        try {
            prop.orElseThrow().setActive(true);
            this.propertyExtService.save(prop.get());
        }
        catch (Exception ex) {
            return DatabaseResult.Error;
        }
        return DatabaseResult.Success;
    }
}
