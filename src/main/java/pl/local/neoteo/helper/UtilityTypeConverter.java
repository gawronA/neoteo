package pl.local.neoteo.helper;

import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.service.UtilityTypeService;

public class UtilityTypeConverter implements Converter<String, UtilityType> {

    private final UtilityTypeService utilityTypeService;

    public UtilityTypeConverter(UtilityTypeService utilityTypeService) {
        this.utilityTypeService = utilityTypeService;
    }

    @Override
    public UtilityType convert(String s) {
        return utilityTypeService.getUtilityType(Long.parseLong(s));
    }
}
