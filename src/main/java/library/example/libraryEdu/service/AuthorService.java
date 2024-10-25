package library.example.libraryEdu.service;

import library.example.libraryEdu.model.Author;
import library.example.libraryEdu.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Получение всех авторов
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Получение автора по ID
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // Создание нового автора
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Обновление существующего автора
    public Author updateAuthor(Long id, Author authorDetails) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setFirstname(authorDetails.getFirstname());
            existingAuthor.setLastname(authorDetails.getLastname());
            existingAuthor.setBirthdate(authorDetails.getBirthdate());
            return authorRepository.save(existingAuthor);
        } else {
            throw new RuntimeException("Author not found with id " + id);
        }
    }

    // Удаление автора
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}

