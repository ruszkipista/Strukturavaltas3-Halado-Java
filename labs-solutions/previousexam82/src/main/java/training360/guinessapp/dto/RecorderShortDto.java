package training360.guinessapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecorderShortDto {
    private String name;
    private LocalDate dateOfBirth;
}
