package crudtemplate.dto;

import crudtemplate.model.EnumeratedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleWithoutMultiplesDto {
    private Long id;
    private String name;
    private EnumeratedType enumType;
}
