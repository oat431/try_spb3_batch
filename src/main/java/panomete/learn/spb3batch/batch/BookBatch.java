package panomete.learn.spb3batch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import panomete.learn.spb3batch.entity.Books;
import panomete.learn.spb3batch.listener.JobCompletionNotificationListener;
import panomete.learn.spb3batch.processor.BookItemProcessor;
import panomete.learn.spb3batch.reader.BookItemReader;
import panomete.learn.spb3batch.repository.BookRepository;
import panomete.learn.spb3batch.writer.BookItemWriter;

@Configuration
@RequiredArgsConstructor
public class BookBatch {
    final BookRepository bookRepository;

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public BookItemProcessor processor() {
        return new BookItemProcessor();
    }

    @Bean
    public BookItemWriter writer() {
        return new BookItemWriter(bookRepository);
    }

    @Bean
    public BookItemReader reader() {
        return new BookItemReader(this.fileInput);
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<Books> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Books, Books> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
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
