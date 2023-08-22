package panomete.learn.spb3batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.learn.spb3batch.entity.Books;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Books, UUID> {
}
