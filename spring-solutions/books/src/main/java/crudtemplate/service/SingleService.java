package crudtemplate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crudtemplate.dto.CreateSingleCommand;
import crudtemplate.dto.SingleDto;
import crudtemplate.dto.SingleMapper;
import crudtemplate.dto.UpdateSingleCommand;
import crudtemplate.exception.SingleNotFoundException;
import crudtemplate.model.Single;
import crudtemplate.repository.MultipleRepository;
import crudtemplate.repository.SingleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SingleService {
    private SingleRepository repoSingle;
    private MultipleRepository repoMultiple;
    private SingleMapper mapper;

    public List<SingleDto> getSingles(Optional<String> namePrefix) {
        return this.mapper.toDto(repoSingle.findAllByNamePart(namePrefix));
    }

    public SingleDto getSingleById(long id) {
        return this.mapper.toDto(repoSingle.findById(id)
                                     .orElseThrow(()->new SingleNotFoundException(id)));
    }
    
    public SingleDto createSingle(CreateSingleCommand command) {
        Single entity = repoSingle.save(this.mapper.fromCreateCommand(command));
        return this.mapper.toDto(entity);
    }

    @Transactional
    public SingleDto updateSingleById(long id, UpdateSingleCommand command) {
        Single entity = repoSingle.findById(id)
                         .orElseThrow(()->new SingleNotFoundException(id));
        entity.setName(command.getName());
        entity.setEnumType(command.getEnumType());
        return this.mapper.toDto(entity);
    }

    @Transactional
    public void removeSingleById(long id) {
        repoSingle.findById(id).orElseThrow(()->new SingleNotFoundException(id));
        repoSingle.deleteById(id);
    }

}
