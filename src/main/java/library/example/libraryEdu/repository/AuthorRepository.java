package library.example.libraryEdu.repository;
import library.example.libraryEdu.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Мои методы в будущем
}
