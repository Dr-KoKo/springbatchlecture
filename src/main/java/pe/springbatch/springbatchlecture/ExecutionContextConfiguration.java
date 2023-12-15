package pe.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {
	private final ExecutionContextTasklet1 tasklet1;
	private final ExecutionContextTasklet2 tasklet2;
	private final ExecutionContextTasklet3 tasklet3;
	private final ExecutionContextTasklet4 tasklet4;

	@Bean
	public Job batchJob(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("Job", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.next(step3(jobRepository, txManager))
			.next(step4(jobRepository, txManager))
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.tasklet(tasklet1, txManager)
			.build();
	}

	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step2", jobRepository)
			.tasklet(tasklet2, txManager)
			.build();
	}

	@Bean
	public Step step3(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step3", jobRepository)
			.tasklet(tasklet3, txManager)
			.build();
	}

	@Bean
	public Step step4(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step4", jobRepository)
			.tasklet(tasklet4, txManager)
			.build();
	}
}