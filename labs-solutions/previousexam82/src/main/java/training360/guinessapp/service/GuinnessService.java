package training360.guinessapp.service;

import training360.guinessapp.domain.Recorder;
import training360.guinessapp.domain.WorldRecord;
import training360.guinessapp.dto.*;
import training360.guinessapp.exceptionhandling.CanNotBeatRecordException;
import training360.guinessapp.exceptionhandling.RecorderNotFoundException;
import training360.guinessapp.exceptionhandling.WorldRecordNotFoundException;
import training360.guinessapp.repository.RecorderRepository;
import training360.guinessapp.repository.WorldRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GuinnessService {

    private WorldRecordRepository worldRecordRepository;
    private RecorderRepository recorderRepository;
    private ModelMapper modelMapper;

    public GuinnessService(WorldRecordRepository worldRecordRepository, RecorderRepository recorderRepository, ModelMapper modelMapper) {
        this.worldRecordRepository = worldRecordRepository;
        this.recorderRepository = recorderRepository;
        this.modelMapper = modelMapper;
    }


    public RecorderDto saveRecorder(RecorderCreateCommand command) {
        Recorder toSave = modelMapper.map(command, Recorder.class);
        Recorder saved = recorderRepository.save(toSave);
        return modelMapper.map(saved, RecorderDto.class);
    }

    public WorldRecordDto saveWorldRecord(WorldRecordCreateCommand command) {
        Recorder recorder = findRecorderById(command.getRecorderId());

        WorldRecord toSave = new WorldRecord();
        toSave.setDescription(command.getDescription());
        toSave.setValue(command.getValue());
        toSave.setUnitOfMeasure(command.getUnitOfMeasure());
        toSave.setDateOfRecord(command.getDateOfRecord());
        toSave.setRecorder(recorder);

        WorldRecord saved = worldRecordRepository.save(toSave);
        WorldRecordDto worldRecordDto = modelMapper.map(saved, WorldRecordDto.class);
        worldRecordDto.setRecorderName(recorder.getName());
        return worldRecordDto;
    }

    public BeatWorldRecordDto beatRecord(Long worldRecordId, BeatWorldRecordCommand command) {
        Recorder newRecorder = findRecorderById(command.getRecorderId());
        WorldRecord worldRecord = findWorldRecordById(worldRecordId);
        Double oldValue = worldRecord.getValue();
        Double newValue = command.getNewRecord();
        if (oldValue > newValue) {
            throw new CanNotBeatRecordException();
        }

        Recorder oldRecorder = worldRecord.getRecorder();
        worldRecord.setRecorder(newRecorder);
        worldRecord.setDateOfRecord(LocalDate.now());
        worldRecord.setValue(newValue);

        BeatWorldRecordDto beatWorldRecordDto = new BeatWorldRecordDto();
        beatWorldRecordDto.setDescription(worldRecord.getDescription());
        beatWorldRecordDto.setUnitOfMeasure(worldRecord.getUnitOfMeasure());
        beatWorldRecordDto.setOldRecorderName(oldRecorder.getName());
        beatWorldRecordDto.setOldRecordValue(oldValue);
        beatWorldRecordDto.setNewRecorderName(newRecorder.getName());
        beatWorldRecordDto.setNewRecordValue(worldRecord.getValue());
        beatWorldRecordDto.setRecordDifference(Math.round((newValue - oldValue) * 100.0) / 100.0);

        return beatWorldRecordDto;
    }

    public List<RecorderShortDto> listCertainRecorders() {
        return recorderRepository.listCertainRecorders();
    }

    private Recorder findRecorderById(Long recorderId) {
        Optional<Recorder> recorder = recorderRepository.findById(recorderId);
        if (recorder.isEmpty()) {
            throw new RecorderNotFoundException();
        }
        return recorder.get();
    }

    private WorldRecord findWorldRecordById(Long worldRecordId) {
        Optional<WorldRecord> record = worldRecordRepository.findById(worldRecordId);
        if (record.isEmpty()) {
            throw new WorldRecordNotFoundException();
        }
        return record.get();
    }
}
