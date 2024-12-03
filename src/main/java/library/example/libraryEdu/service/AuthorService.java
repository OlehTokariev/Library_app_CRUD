package library.example.libraryEdu.service;

import library.example.libraryEdu.dto.AuthorDTO;
import library.example.libraryEdu.exception.AuthorInUseException;
import library.example.libraryEdu.exception.NotFoundException;
import library.example.libraryEdu.model.Author;
import library.example.libraryEdu.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private AuthorDTO convertToDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .birthdate(author.getBirthdate())
                .build();
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found"));
        return convertToDTO(author);
    }

    public AuthorDTO findOrCreateAuthor(AuthorDTO authorDTO) {
        return convertToDTO(authorRepository.findByFirstnameAndLastname(authorDTO.getFirstname(), authorDTO.getLastname())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setFirstname(authorDTO.getFirstname());
                    newAuthor.setLastname(authorDTO.getLastname());
                    newAuthor.setBirthdate(authorDTO.getBirthdate());
                    return authorRepository.save(newAuthor);
                }));
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Optional<Author> existingAuthor = authorRepository
                .findByFirstnameAndLastname(authorDTO.getFirstname(), authorDTO.getLastname());

        if (existingAuthor.isPresent()) {
            return convertToDTO(existingAuthor.get());
        }

        Author author = new Author();
        author.setFirstname(authorDTO.getFirstname());
        author.setLastname(authorDTO.getLastname());
        author.setBirthdate(authorDTO.getBirthdate());

        Author savedAuthor = authorRepository.save(author);

        return convertToDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found"));

        if (authorDTO.getFirstname() != null) {
            existingAuthor.setFirstname(authorDTO.getFirstname());
        }
        if (authorDTO.getLastname() != null) {
            existingAuthor.setLastname(authorDTO.getLastname());
        }
        if (authorDTO.getBirthdate() != null) {
            existingAuthor.setBirthdate(authorDTO.getBirthdate());
        }

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return convertToDTO(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found"));

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new AuthorInUseException("Cannot delete author with id " + id + " because they are associated with existing books.");
        }

        authorRepository.delete(author);
    }
}
