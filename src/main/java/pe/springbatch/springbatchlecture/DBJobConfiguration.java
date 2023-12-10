package pe.springbatch.springbatchlecture;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DBJobConfiguration {

	@Bean
	public Job helloJob(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("Job", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.next(step3(jobRepository, txManager))
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("step1 has executed");
						return RepeatStatus.FINISHED;
					}
				}, txManager)
			.build();
	}

	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step2", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("step2 has executed");
//						throw new RuntimeException("step2 has failed");
						return RepeatStatus.FINISHED;
					}
				}, txManager)
			.build();
	}

	@Bean
	public Step step3(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step3", jobRepository)
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
					System.out.println("step3 has executed");
					return RepeatStatus.FINISHED;
				}
			}, txManager)
			.build();
	}
}
