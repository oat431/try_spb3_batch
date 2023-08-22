package panomete.learn.spb3batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import panomete.learn.spb3batch.entity.Books;

@Slf4j
public class BookItemProcessor implements ItemProcessor<Books, Books> {
    @Override
    public Books process(Books item) throws Exception {
        String title = item.getTitle().toUpperCase();
        String author = item.getAuthor().toUpperCase();
        String publisher = item.getPublisher().toUpperCase();
        String edition = item.getEdition().toUpperCase();
        String publishedDate = item.getPublishedDate();

        Books transformedBook = Books.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .edition(edition)
                .publishedDate(publishedDate)
                .build();
        log.info("a book has been transformed: {}", transformedBook.toString());
        return transformedBook;
    }
}
