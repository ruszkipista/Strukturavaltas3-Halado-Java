package sportresults.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sportresults.dto.*;
import sportresults.exceptions.AthleteNotFoundException;
import sportresults.exceptions.ResultNotFoundException;
import sportresults.model.Athlete;
import sportresults.model.Result;
import sportresults.model.SportType;
import sportresults.repository.AthleteRepository;
import sportresults.repository.ResultRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SportResultService {

    private ResultRepository resultRepository;
    private AthleteRepository athleteRepository;
    private ModelMapper modelMapper;

    public AthleteDto createAthlete(CreateAthleteCommand command) {
        Athlete athlete = new Athlete(command.getName(), command.getSex());
        athleteRepository.save(athlete);
        return modelMapper.map(athlete, AthleteDto.class);
    }

    public AthleteDto addResultToAthlete(Long athleteId, CreateResultCommand command) {
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow(() -> new AthleteNotFoundException(athleteId));
        Result result = new Result(command.getPlace(), command.getResultDate(), command.getSportType(), command.getMeasure());
        resultRepository.save(result);
        athlete.addResult(result);
        return modelMapper.map(athlete, AthleteDto.class);
    }

    public ResultDto updateResult(Long id, UpdateMeasureCommand command) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new ResultNotFoundException(id));
        result.setMeasure(command.getMeasure());
        return modelMapper.map(result, ResultDto.class);
    }

    public List<ResultDto> getResults(Optional<String> sportType) {
        List<Result> results = resultRepository.findBySportType(sportType);
//        if (sportType.isPresent() && (sportType.get().equals(SportType.HAMMER_THROWING) || sportType.get().equals(SportType.POLE_VAULT)) {
//            List<Result> r = results.stream().sorted(Result::getMeasure).collect(Collectors.toList());
//        }
        return results.stream().map(result -> modelMapper.map(result, ResultDto.class)).collect(Collectors.toList());
    }
}
