package training360.model;

import java.util.List;

import org.mapstruct.Mapper;

import training360.DTOs.CreateSchoolCommand;
import training360.DTOs.SchoolDto;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    SchoolDto toDto(School entity);
    List<SchoolDto> toDto(List<School> entities);

    School fromDto(SchoolDto entityDto);
    List<School> fromDto(List<SchoolDto> entityDtos);
    
    School fromCreateCommand(CreateSchoolCommand entityCreateCommand);

}
