package pe.springbatch.springbatchlecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StepBuilderConfiguration {
	@Bean
	public Job batchJob(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("batchJob", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.next(step3(jobRepository, txManager))
			.incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step1 has executed");
				return RepeatStatus.FINISHED;
			}, txManager)
			.build();
	}

	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step2", jobRepository)
			.<String, String>chunk(3, txManager)
			.reader(new ItemReader<String>() {
				@Override
				public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
					return null;
				}
			})
			.processor(new ItemProcessor<String, String>() {
				@Override
				public String process(String item) throws Exception {
					return null;
				}
			})
			.writer(new ItemWriter<String>() {
				@Override
				public void write(Chunk<? extends String> chunk) throws Exception {

				}
			})
			.build();
	}

	@Bean
	public Step step3(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.partitioner(step1(jobRepository, txManager))
			.gridSize(2)
			.build();
	}

	@Bean
	public Step step4(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.job(job(jobRepository, txManager))
			.build();
	}

	@Bean
	public Step step5(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("step1", jobRepository)
			.flow(flow(jobRepository, txManager))
			.build();
	}

	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("job", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.next(step3(jobRepository, txManager))
			.build();
	}

	@Bean
	public Flow flow(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new FlowBuilder<Flow>("flow").start(step2(jobRepository, txManager)).end();
	}
}