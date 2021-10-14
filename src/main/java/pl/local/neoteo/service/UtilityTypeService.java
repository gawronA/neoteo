package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service
public interface UtilityTypeService {
    public DatabaseResult addUtilityType(UtilityType payment);
    public DatabaseResult updateUtilityType(UtilityType payment);
    public DatabaseResult deleteUtilityType(long id);
    public UtilityType getUtilityType(long id);
    public List<UtilityType> getUtilityTypes();
}
