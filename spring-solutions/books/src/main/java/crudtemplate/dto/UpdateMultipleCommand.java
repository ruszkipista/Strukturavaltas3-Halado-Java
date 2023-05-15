package crudtemplate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMultipleCommand {
    
    @NotBlank(message = "name can not be blank")
    private String name;
}