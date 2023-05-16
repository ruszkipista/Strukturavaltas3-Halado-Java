package crudtemplate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crudtemplate.dto.CreateMultipleCommand;
import crudtemplate.dto.MultipleDto;
import crudtemplate.dto.MultipleMapper;
import crudtemplate.dto.UpdateMultipleCommand;
import crudtemplate.exception.MultipleNotFoundException;
import crudtemplate.exception.SingleNotFoundException;
import crudtemplate.model.Multiple;
import crudtemplate.model.Single;
import crudtemplate.repository.MultipleRepository;
import crudtemplate.repository.SingleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MultipleService {
    private MultipleRepository repoMultiple;
    private SingleRepository repoSingle;
    private MultipleMapper mapper;

    public List<MultipleDto> getMultiples(Optional<String> namePrefix) {
        return this.mapper.toDto(repoMultiple.findAllByNamePart(namePrefix));
    }

    public MultipleDto getMultipleById(long id) {
        return this.mapper.toDto(repoMultiple.findById(id)
                                     .orElseThrow(()->new MultipleNotFoundException(id)));
    }
    
    @Transactional
    public MultipleDto createMultiple(long singleId, CreateMultipleCommand command) {
        Single single = repoSingle.findById(singleId).orElseThrow(()->new SingleNotFoundException(singleId));
        Multiple entityToSave = this.mapper.fromCreateCommand(command);
        entityToSave.setSingle(single);
        Multiple entitySaved = repoMultiple.save(entityToSave);
        return this.mapper.toDto(entitySaved);
    }

    @Transactional
    public MultipleDto updateMultipleById(long id, UpdateMultipleCommand command) {
        Multiple entity = repoMultiple.findById(id)
                         .orElseThrow(()->new MultipleNotFoundException(id));
        entity.setName(command.getName());
        return this.mapper.toDto(entity);
    }

    @Transactional
    public MultipleDto connectMultipleToSingle(long multipleId, long singleId) {
        Multiple multiple = repoMultiple.findById(multipleId).orElseThrow(()->new MultipleNotFoundException(multipleId));
        Single single = repoSingle.findById(singleId).orElseThrow(()->new SingleNotFoundException(singleId));
        multiple.setSingle(single);
        return mapper.toDto(multiple);
    }

    @Transactional
    public void removeMultipleById(long id) {
        repoMultiple.findById(id).orElseThrow(()->new MultipleNotFoundException(id));
        repoMultiple.deleteById(id);
    }
}
