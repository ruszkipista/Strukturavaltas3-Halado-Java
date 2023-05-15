package crudtemplate.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleDto {
    private Long id;
    private String name;
    private LocalDate whenHappened;
    private SingleWithoutMultiplesDto single;
}
