package crudtemplate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateMultipleCommand {
    
    @NotBlank(message = "name can not be blank")
    private String name;
}

