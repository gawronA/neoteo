package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.service.UtilityTypeService;

import java.util.HashSet;
import java.util.Set;

public class UtilityTypeListConverter implements Converter<String[], Set<UtilityType>> {

    private final UtilityTypeService utilityTypeService;

    @Autowired
    public UtilityTypeListConverter(UtilityTypeService utilityTypeService) {
        this.utilityTypeService = utilityTypeService;
    }

    @Override
    public Set<UtilityType> convert(String[] s) {
        Set<UtilityType> utilityTypes = new HashSet<UtilityType>();
        for(String id : s) {
            UtilityType utilityType = this.utilityTypeService.getUtilityType(Long.parseLong(id));
            if(utilityType == null) return null;
            utilityTypes.add(utilityType);
        }
        return utilityTypes;
    }
}
