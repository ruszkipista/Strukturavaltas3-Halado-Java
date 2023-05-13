package appointments.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaseTypeMapper {

    CaseTypeDto toDto(CaseType CaseType);
    List<CaseTypeDto> toDto(List<CaseType> CaseTypes);

}
