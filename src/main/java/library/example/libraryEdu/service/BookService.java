package library.example.libraryEdu.service;

import jakarta.persistence.EntityNotFoundException;
import library.example.libraryEdu.dto.AuthorDTO;
import library.example.libraryEdu.dto.BookDTO;
import library.example.libraryEdu.exception.NotFoundException;
import library.example.libraryEdu.model.Author;
import library.example.libraryEdu.model.Book;
import library.example.libraryEdu.repository.AuthorRepository;
import library.example.libraryEdu.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private BookDTO convertToDTO(Book book) {
        Author author = book.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO(author.getFirstname(), author.getLastname(), author.getBirthdate());
        return new BookDTO(book.getTitle(), book.getYear(), book.getGenre(), authorDTO);  // Используем genre
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO);
    }

    private Author findOrCreateAuthor(AuthorDTO authorDTO) {
        return authorRepository.findByFirstnameAndLastname(authorDTO.getFirstname(), authorDTO.getLastname())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setFirstname(authorDTO.getFirstname());
                    newAuthor.setLastname(authorDTO.getLastname());
                    newAuthor.setBirthdate(authorDTO.getBirthdate());
                    return authorRepository.save(newAuthor);
                });
    }

    public BookDTO createBook(BookDTO bookDTO, String genre) {
        Author author = findOrCreateAuthor(bookDTO.getAuthor());

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setGenre(bookDTO.getGenre());
        book.setAuthor(author);

        if (genre != null && !genre.isEmpty()) {
            book.setGenre(genre);
        }

        if ("Test Rollback".equals(bookDTO.getTitle())) {
            throw new RuntimeException("Testing transaction rollback");
        }

        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));

        if (bookDTO.getTitle() != null) {
            existingBook.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getGenre() != null) {
            existingBook.setGenre(bookDTO.getGenre());
        }
        if (bookDTO.getYear() != null) {
            existingBook.setYear(bookDTO.getYear());
        }
        if (bookDTO.getAuthor() != null) {
            Author author = findOrCreateAuthor(bookDTO.getAuthor());
            existingBook.setAuthor(author);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }


    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + bookId));

        bookRepository.delete(book);
    }

}


