package pe.springbatch.springbatchlecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class TaskletStepConfiguration {
	@Bean
	public Job batchJob(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new JobBuilder("batchJob", jobRepository)
			//			.start(taskletStep(jobRepository, txManager))
			.start(chunkStep(jobRepository, txManager))
			.build();
	}

	@Bean
	public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("taskletStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				System.out.println("taskletStep has executed");
				return RepeatStatus.FINISHED;
			}, txManager)
			.build();
	}

	@Bean
	public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
		return new StepBuilder("taskletStep", jobRepository)
			.<String, String>chunk(10, txManager)
			.reader(new ListItemReader<>(List.of("item1", "item2", "item3", "item4", "item5")))
			.processor(new ItemProcessor<String, String>() {
				@Override
				public String process(String item) throws Exception {
					return item.toUpperCase();
				}
			})
			.writer(new ItemWriter<String>() {
				@Override
				public void write(Chunk<? extends String> chunk) throws Exception {
					chunk.forEach(item -> System.out.println(item));
				}
			})
			.build();
	}
}