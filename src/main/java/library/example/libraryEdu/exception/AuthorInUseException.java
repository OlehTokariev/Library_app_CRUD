package library.example.libraryEdu.exception;

public class AuthorInUseException extends RuntimeException {
    public AuthorInUseException(String message) {
        super(message);
    }
}