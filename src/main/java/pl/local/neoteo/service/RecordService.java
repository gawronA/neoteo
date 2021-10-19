package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service
public interface RecordService {
    public DatabaseResult addRecord(Record utility);
    public DatabaseResult updateRecord(Record utility);
    public DatabaseResult deleteRecord(long id);
    public Record getRecord(long id);
    public List<Record> getRecords();
}
