package library.example.libraryEdu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
}
