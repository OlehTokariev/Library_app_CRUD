package library.example.libraryEdu.exception;
import java.time.LocalDateTime;

public class APIError {
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public APIError(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}

