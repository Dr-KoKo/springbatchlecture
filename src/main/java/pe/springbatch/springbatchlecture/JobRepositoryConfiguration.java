package pe.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryConfiguration {
	//	@Bean
	//	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager txManager) throws Exception {
	//		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	//		factory.setDataSource(dataSource);
	//		factory.setTransactionManager(txManager);
	//		factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
	//				factory.setTablePrefix("SYSTEM_");
	//		factory.afterPropertiesSet();
	//		return factory.getObject();
	//	}


	//	@Override
	//	public JobRepository jobRepository() throws BatchConfigurationException {
	//		return super.jobRepository();
	//	}

	@Bean
	public JobRepositoryListener jobRepositoryListener(JobRepository jobRepository) throws Exception {
		return new JobRepositoryListener(jobRepository);
	}

	@Bean
	public Job batchJob(JobRepository jobRepository, DataSource dataSource, PlatformTransactionManager txManager) throws Exception {
		return new JobBuilder("Job", jobRepository)
			.start(step1(jobRepository, txManager))
			.next(step2(jobRepository, txManager))
			.listener(jobRepositoryListener(jobRepository))
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
}