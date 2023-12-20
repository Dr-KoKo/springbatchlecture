package pe.springbatch.springbatchlecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class LimitAllowConfiguration {
	@Bean
	public Job batchJob(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("batchJob", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step1 has executed");
				return RepeatStatus.FINISHED;
			}, txManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step2", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step2 has executed");
				throw new RuntimeException("step2 was failed");
				//				return RepeatStatus.FINISHED;
			}, txManager)
			.startLimit(3)
			.build();
	}
}