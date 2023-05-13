package training360.guinessapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RecorderDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
}
