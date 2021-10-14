package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.Utility;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.UtilityRepository;

import java.util.List;

@Service
class UtilityExtServiceImpl {
    private final UtilityRepository utilityRepository;

    public UtilityExtServiceImpl(UtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(Utility utility) {
        this.utilityRepository.save(utility);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.utilityRepository.deleteById(id);
    }
}

@Service//("utilityService")
public class UtilityServiceImpl implements UtilityService {

    private final UtilityRepository utilityRepository;
    private final UtilityExtServiceImpl utilityExtService;

    public UtilityServiceImpl(UtilityRepository utilityRepository, UtilityExtServiceImpl utilityExtService) {
        this.utilityRepository = utilityRepository;
        this.utilityExtService = utilityExtService;
    }

    public DatabaseResult addUtility(Utility utility) {
        utility.setId(0);
        try {
            this.utilityExtService.save(utility);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult updateUtility(Utility utility) {
        var dbUtility = this.utilityRepository.findById(utility.getId());
        if(dbUtility.isEmpty()) return DatabaseResult.Error;

        return addUtility(utility);
    }

    public DatabaseResult deleteUtility(long id) {
        Utility utility = getUtility(id);
        if(utility == null) return DatabaseResult.Error;

        try {
            this.utilityExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public Utility getUtility(long id) {
        var utility = this.utilityRepository.findById(id);
        return utility.orElse(null);
    }

    @Transactional
    public List<Utility> getUtilities() {
        return this.utilityRepository.findAll();
    }

}
