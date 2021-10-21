package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.UtilityTypeRepository;

import java.util.List;

@Service
class UtilityTypeExtServiceImpl {

    private final UtilityTypeRepository utilityTypeRepository;

    public UtilityTypeExtServiceImpl(UtilityTypeRepository utilityTypeRepository) {
        this.utilityTypeRepository = utilityTypeRepository;
    }

    @Transactional
    void save(UtilityType utilityType) {
        this.utilityTypeRepository.save(utilityType);
    }

    @Transactional
    void delete(long id) {
        this.utilityTypeRepository.deleteById(id);
    }
}

@Service
public class UtilityTypeServiceImpl implements UtilityTypeService {

    private final UtilityTypeRepository utilityTypeRepository;
    private final UtilityTypeExtServiceImpl utilityTypeExtService;
    private final RecordService recordService;

    public UtilityTypeServiceImpl(UtilityTypeRepository utilityTypeRepository, UtilityTypeExtServiceImpl utilityTypeExtService, RecordService recordService) {
        this.utilityTypeRepository = utilityTypeRepository;
        this.utilityTypeExtService = utilityTypeExtService;
        this.recordService = recordService;
    }

    @Override
    public DatabaseResult addUtilityType(UtilityType utilityType) {
        try {
            this.utilityTypeExtService.save(utilityType);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    @Transactional
    public DatabaseResult updateUtilityType(UtilityType utilityType) {
        var dbUtilityType = this.utilityTypeRepository.findById(utilityType.getId());
        if(dbUtilityType.isEmpty()) return DatabaseResult.Error;

        return addUtilityType(utilityType);
    }

    @Override
    public DatabaseResult deleteUtilityType(long id) {
        UtilityType utilityType = getUtilityType(id);
        if(utilityType == null) return DatabaseResult.Error;
        if(utilityType.getRecords() != null && !utilityType.getRecords().isEmpty()) {
            for(Record record:utilityType.getRecords()) {
                recordService.deleteRecord(record.getId());
            }
        }

        try {
            this.utilityTypeExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Override
    public UtilityType getUtilityType(long id) {
        var pay = this.utilityTypeRepository.findById(id);
        return pay.orElse(null);
    }

    @Override
    public List<UtilityType> getUtilityTypes() {
        return this.utilityTypeRepository.findAll();
    }

}
