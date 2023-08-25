package panomete.learn.spb3batch.listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import panomete.learn.spb3batch.repository.BookRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    final BookRepository bookRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        int count = bookRepository.findAll().size();
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED!");
            log.info("Found {} books in the database.", count);
        }
    }
}