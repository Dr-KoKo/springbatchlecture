package pe.springbatch.springbatchlecture;


import org.springframework.batch.core.Job;
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
public class JobConfiguration2 {
	@Bean
	public Job batchJob2(JobRepository jobRepository, PlatformTransactionManager txManager) throws Exception {
		return new JobBuilder("Job2", jobRepository)
			.start(step3(jobRepository, txManager))
			.next(step4(jobRepository, txManager))
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

	@Bean
	public Step step4(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step4", jobRepository)
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
					System.out.println("step4 has executed");
					return RepeatStatus.FINISHED;
				}
			}, txManager)
			.build();
	}
}