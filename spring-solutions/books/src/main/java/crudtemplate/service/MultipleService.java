package crudtemplate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crudtemplate.exception.MultipleNotFoundException;
import crudtemplate.model.CreateMultipleCommand;
import crudtemplate.model.Multiple;
import crudtemplate.model.MultipleDto;
import crudtemplate.model.MultipleMapper;
import crudtemplate.model.UpdateMultipleCommand;
import crudtemplate.repository.MultipleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MultipleService {
    private MultipleRepository repo;
    private MultipleMapper mapper;

    public List<MultipleDto> getMultiples(Optional<String> namePrefix) {
        return this.mapper.toDto(repo.findAllByNamePart(namePrefix));
    }

    public MultipleDto getMultipleById(long id) {
        return this.mapper.toDto(repo.findById(id)
                                     .orElseThrow(()->new MultipleNotFoundException(id)));
    }
    
    public MultipleDto createMultiple(CreateMultipleCommand command) {
        Multiple entity = repo.save(this.mapper.fromCreateCommand(command));
        return this.mapper.toDto(entity);
    }

    @Transactional
    public MultipleDto updateMultipleById(long id, UpdateMultipleCommand command) {
        Multiple entity = repo.findById(id)
                         .orElseThrow(()->new MultipleNotFoundException(id));
        entity.setName(command.getName());
        return this.mapper.toDto(entity);
    }

    @Transactional
    public void removeMultipleById(long id) {
        repo.findById(id).orElseThrow(()->new MultipleNotFoundException(id));
        repo.deleteById(id);
    }
}
