package pe.springbatch.springbatchlecture;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {
//	@Bean
//	public Job batchJob1(JobRepository jobRepository, PlatformTransactionManager txManager) throws Exception {
//		return new JobBuilder("Job1", jobRepository)
//			.start(step1(jobRepository, txManager))
//			.next(step2(jobRepository, txManager))
//			.build();
//	}

	@Bean
	public Job batchJob2(JobRepository jobRepository, PlatformTransactionManager txManager) throws Exception {
		return new JobBuilder("Job2", jobRepository)
			.start(flow(jobRepository, txManager))
			.next(step5(jobRepository, txManager))
			.end()
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

	@Bean
	public Step step5(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step5", jobRepository)
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
					System.out.println("step5 has executed");
					return RepeatStatus.FINISHED;
				}
			}, txManager)
			.build();
	}

	@Bean
	public Flow flow(JobRepository jobRepository, PlatformTransactionManager txManager) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
		return flowBuilder
			.start(step3(jobRepository, txManager))
			.next(step4(jobRepository, txManager))
			.end();
	}
}