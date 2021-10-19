package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.RecordRepository;

import java.util.List;

@Service
class RecordExtServiceImpl {
    private final RecordRepository recordRepository;

    public RecordExtServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(Record record) {
        this.recordRepository.save(record);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.recordRepository.deleteById(id);
    }
}

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordExtServiceImpl recordExtService;

    public RecordServiceImpl(RecordRepository recordRepository, RecordExtServiceImpl recordExtService) {
        this.recordRepository = recordRepository;
        this.recordExtService = recordExtService;
    }

    public DatabaseResult addRecord(Record record) {
        record.setId(0);
        try {
            this.recordExtService.save(record);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult updateRecord(Record record) {
        var dbUtility = this.recordRepository.findById(record.getId());
        if(dbUtility.isEmpty()) return DatabaseResult.Error;

        try {
            this.recordExtService.save(record);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult deleteRecord(long id) {
        Record record = getRecord(id);
        if(record == null) return DatabaseResult.Error;

        try {
            this.recordExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public Record getRecord(long id) {
        var record = this.recordRepository.findById(id);
        return record.orElse(null);
    }

    @Transactional
    public List<Record> getRecords() {
        return this.recordRepository.findAll();
    }

}
