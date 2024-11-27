package library.example.libraryEdu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    @Min(value = 1, message = "ID must be greater than or equal to 1")
    private Long id;

    @NotBlank(message = "Firstname must not be blank")
    private String firstname;

    @NotBlank(message = "Lastname must not be blank")
    private String lastname;

    @NotNull(message = "Birthdate must not be null")
    @Past(message = "Birthdate must be a past date")
    private LocalDate birthdate;
}