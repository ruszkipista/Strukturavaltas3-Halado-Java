package crudtemplate.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMultipleCommand {
    
    @NotBlank(message = "name can not be blank")
    private String name;

    @Past(message = "must be in the past")
    @NotNull(message = "must not be null")
    private LocalDate whenHappened;
}