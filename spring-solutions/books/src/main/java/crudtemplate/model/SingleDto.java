package crudtemplate.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleDto {
    private Long id;
    private String name;
    private EnumeratedType enumType;
    private Set<MultipleWithoutSingleDto> multiples;
}
