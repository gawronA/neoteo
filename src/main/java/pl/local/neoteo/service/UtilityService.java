package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Utility;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service//("bookingService")
public interface UtilityService {
    public DatabaseResult addUtility(Utility utility);
    public DatabaseResult updateUtility(Utility utility);
    public DatabaseResult deleteUtility(long id);
    public Utility getUtility(long id);
    public List<Utility> getUtilities();
}
