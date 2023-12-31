package panomete.learn.spb3batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import panomete.learn.spb3batch.entity.Books;

public class BookItemReader extends FlatFileItemReader<Books>{
    private final String fileInput;

    public BookItemReader(String fileInput) {
        this.fileInput = fileInput;
        readFile();
    }

    public void readFile() {
        Resource resource = new ClassPathResource(fileInput);
        setResource(resource);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setNames(new String[]{"title", "author", "publisher", "edition", "publishedDate"});

        BeanWrapperFieldSetMapper<Books> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Books.class);

        DefaultLineMapper<Books> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        setLineMapper(lineMapper);
    }
}
