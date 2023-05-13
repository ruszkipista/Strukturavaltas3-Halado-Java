package flights.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import flights.dtos.AirplaneDto;
import flights.dtos.AirplaneMapper;
import flights.dtos.CreateAirplaneCommand;
import flights.dtos.UpdateAirplaneCommand;
import flights.exception.AirplaneNotFoundException;
import flights.model.Airplane;
import flights.repository.AirplaneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AirplaneService {
    private AirplaneRepository repo;
    private AirplaneMapper mapper;

    public List<AirplaneDto> getAirplanes(Optional<String> ownerAirline) {
        return this.mapper.toDto(repo.findAllByOwnerAirline(ownerAirline));
    }

    public AirplaneDto getAirplaneById(long id) {
        return this.mapper.toDto(repo.findById(id)
                                     .orElseThrow(()->new AirplaneNotFoundException(id)));
    }
    
    public AirplaneDto createAirplane(CreateAirplaneCommand command) {
        Airplane entity = repo.save(this.mapper.fromCreateCommand(command));
        return this.mapper.toDto(entity);
    }

    @Transactional
    public AirplaneDto updateAirplaneById(long id, UpdateAirplaneCommand updateCommand) {
        Airplane entity;
        try {
            entity = repo.findById(id)
                         .orElseThrow(()->new IllegalArgumentException("Airplane not found: "+id));
        } catch (Exception e) {
            throw new AirplaneNotFoundException(id, e);
        }
        Airplane entityUpdated = this.mapper.fromUpdateCommand(updateCommand, entity);
        entityUpdated = repo.save(entityUpdated);
        return this.mapper.toDto(entityUpdated);
    }

    @Transactional
    public void removeAirplaneById(long id) {
        repo.findById(id).orElseThrow(()->new AirplaneNotFoundException(id));
        repo.deleteById(id);
    }

}
