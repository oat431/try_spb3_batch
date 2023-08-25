package panomete.learn.spb3batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import panomete.learn.spb3batch.entity.Books;


//@Component
public class BookItemReader extends FlatFileItemReader<Books>{
    private String fileInput;

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
//        new FlatFileItemReaderBuilder<Books>().name("bookItemReader")
//                .resource(new ClassPathResource(fileInput))
//                .delimited()
//                .names(new String[]{"title", "author", "publisher", "edition", "publishedDate"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<Books>() {{
//                    setTargetType(Books.class);
//                }})
//                .build();
    }
}
