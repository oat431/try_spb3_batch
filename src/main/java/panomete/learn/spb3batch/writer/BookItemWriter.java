package panomete.learn.spb3batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import panomete.learn.spb3batch.entity.Books;
import panomete.learn.spb3batch.repository.BookRepository;

@Slf4j
@RequiredArgsConstructor
public class BookItemWriter implements ItemWriter<Books> {
    final BookRepository bookRepository;
    @Override
    public void write(Chunk<? extends Books> chunk) throws Exception {
        for (Books item : chunk.getItems()) {
            bookRepository.save(item);
            log.info("a book title {} has been saved", item.getTitle());
        }
    }
}
