package panomete.learn.spb3batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Books {
    @Id
    @Builder.Default
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id = UUID.randomUUID();

    String title;
    String author;
    String publisher;
    String edition;
    String publishedDate;
}
