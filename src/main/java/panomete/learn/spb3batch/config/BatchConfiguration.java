package panomete.learn.spb3batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import panomete.learn.spb3batch.entity.Books;
import panomete.learn.spb3batch.processor.BookItemProcessor;
import panomete.learn.spb3batch.listener.JobCompletionNotificationListener;
import panomete.learn.spb3batch.repository.BookRepository;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {
    final BookRepository bookRepository;

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public FlatFileItemReader<Books> reader() {
        return new FlatFileItemReaderBuilder<Books>().name("bookItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[]{"title", "author", "publisher", "edition", "publishedDate"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Books>() {{
                        setTargetType(Books.class);
                    }})
                .build();
    }

    @Bean
    public BookItemProcessor processor() {
        return new BookItemProcessor();
    }

    @Bean
    public ItemWriter<Books> writer() {
        return items -> {
            for (Books item : items) {
                bookRepository.save(item);
            }
        };
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<Books> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Books, Books> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
