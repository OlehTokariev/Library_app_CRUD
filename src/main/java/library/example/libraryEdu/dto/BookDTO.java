package library.example.libraryEdu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotNull(message = "Year must not be null")
    @Positive(message = "Year must be positive")
    private Integer year;

    @NotBlank(message = "Genre must not be blank")
    private String genre;

    @NotNull(message = "Author must not be null")
    @Valid
    private AuthorDTO author;
}
