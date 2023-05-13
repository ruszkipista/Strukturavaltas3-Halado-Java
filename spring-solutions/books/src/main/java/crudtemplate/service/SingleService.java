package crudtemplate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crudtemplate.exception.SingleNotFoundException;
import crudtemplate.model.CreateSingleCommand;
import crudtemplate.model.Single;
import crudtemplate.model.SingleDto;
import crudtemplate.model.SingleMapper;
import crudtemplate.model.UpdateSingleCommand;
import crudtemplate.repository.SingleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SingleService {
    private SingleRepository repo;
    private SingleMapper mapper;

    public List<SingleDto> getSingles(Optional<String> prefix) {
        if (prefix.isEmpty()) {
            return this.mapper.toDto(repo.findAll());
        }
        return this.mapper.toDto(repo.findAllByNamePart(prefix.get()));
    }

    public SingleDto getSingleById(long id) {
        return this.mapper.toDto(repo.findById(id)
                                     .orElseThrow(()->new SingleNotFoundException(id)));
    }
    
    public SingleDto createSingle(CreateSingleCommand command) {
        Single entity = repo.save(this.mapper.fromCreateCommand(command));
        return this.mapper.toDto(entity);
    }

    @Transactional
    public SingleDto updateSingleById(long id, UpdateSingleCommand command) {
        Single entity = repo.findById(id)
                         .orElseThrow(()->new SingleNotFoundException(id));
        entity.setName(command.getName());
        entity.setEnumType(command.getEnumType());
        return this.mapper.toDto(entity);
    }

    @Transactional
    public void removeSingleById(long id) {
        repo.findById(id).orElseThrow(()->new SingleNotFoundException(id));
        repo.deleteById(id);
    }
}
