package library.example.libraryEdu.repository;
import library.example.libraryEdu.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstnameAndLastname(String firstname, String lastname);
}