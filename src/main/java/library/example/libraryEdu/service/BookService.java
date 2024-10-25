package library.example.libraryEdu.service;

import library.example.libraryEdu.model.Author;
import library.example.libraryEdu.model.Book;
import library.example.libraryEdu.repository.AuthorRepository;
import library.example.libraryEdu.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book createBook(Book book) {
        Author author = book.getAuthor();

        // Проверяем, что переданные значения автора не null
        if (author != null && author.getFirstname() != null && author.getLastname() != null) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        } else {
            throw new IllegalArgumentException("Author details are incomplete.");
        }

        // Сохраняем книгу
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setGenre(bookDetails.getGenre());
            existingBook.setYear(bookDetails.getYear());
            existingBook.setAuthor(bookDetails.getAuthor());
            return bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book not found with id " + id);
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
