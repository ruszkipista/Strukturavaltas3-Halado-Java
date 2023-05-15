package crudtemplate.dto;

import crudtemplate.model.EnumeratedType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateSingleCommand {
    
    @NotBlank(message = "name can not be blank")
    private String name;
    private EnumeratedType enumType;
}

