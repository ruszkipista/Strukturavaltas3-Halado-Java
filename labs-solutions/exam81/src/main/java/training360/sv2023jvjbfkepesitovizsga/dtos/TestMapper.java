package training360.sv2023jvjbfkepesitovizsga.dtos;

import java.util.List;

import org.mapstruct.Mapper;

import training360.sv2023jvjbfkepesitovizsga.model.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestDto toDto(Test entity);
    List<TestDto> toDto(List<Test> entities);

    Test fromDto(TestDto entityDto);
    List<Test> fromDto(List<TestDto> entityDtos);
    
    Test fromCreateCommand(CreateTestCommand entityCreateCommand);

}
