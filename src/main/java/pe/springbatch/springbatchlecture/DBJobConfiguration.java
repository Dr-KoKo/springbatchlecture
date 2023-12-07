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

					JobParameters jobParameters1 = contribution.getStepExecution().getJobParameters();
					String name1 = jobParameters1.getString("name");
					Long seq1 = jobParameters1.getLong("seq");
					Date date1 = jobParameters1.getDate("date");
					Double age1 = jobParameters1.getDouble("age");

					System.out.println("name1 = " + name1);
					System.out.println("seq1 = " + seq1);
					System.out.println("date1 = " + date1);
					System.out.println("age1 = " + age1);

					Map<String, Object> jobParameters2 = chunkContext.getStepContext().getJobParameters();
					String name2 = (String)jobParameters2.get("name");
					Long seq2 = (Long)jobParameters2.get("seq");
					Date date2 = (Date)jobParameters2.get("date");
					Double age2 = (Double)jobParameters2.get("age");

					System.out.println("name2 = " + name2);
					System.out.println("seq2 = " + seq2);
					System.out.println("date2 = " + date2);
					System.out.println("age2 = " + age2);

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
}
