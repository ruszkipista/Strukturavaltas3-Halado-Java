package crudtemplate.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MultipleMapper {

    MultipleDto toDto(Multiple entity);
    List<MultipleDto> toDto(List<Multiple> entities);

    Multiple fromDto(MultipleDto entityDto);
    List<Multiple> fromDto(List<MultipleDto> entityDtos);
    
    Multiple fromCreateCommand(CreateMultipleCommand entityCreateCommand);

}
