package library.example.libraryEdu.controller;

import jakarta.validation.Valid;
import library.example.libraryEdu.dto.BookDTO;
import library.example.libraryEdu.exception.APIError;
import library.example.libraryEdu.exception.NotFoundException;
import library.example.libraryEdu.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO, @RequestParam(required = false) String genre) {
        BookDTO createdBook = bookService.createBook(bookDTO, genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new HashMap<>());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public APIError handleNotFoundException(NotFoundException exception) {
        return new APIError(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}