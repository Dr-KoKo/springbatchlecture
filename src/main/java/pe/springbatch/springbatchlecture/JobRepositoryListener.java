package pe.springbatch.springbatchlecture;

import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;

public class JobRepositoryListener implements JobExecutionListener {
	private final JobRepository jobRepository;

	public JobRepositoryListener(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("requestDate", "20231214")
			.toJobParameters();

		JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
		if (lastJobExecution != null) {
			for (StepExecution stepExecution : lastJobExecution.getStepExecutions()) {
				String stepName = stepExecution.getStepName();
				BatchStatus status = stepExecution.getStatus();
				ExitStatus exitStatus = stepExecution.getExitStatus();

				System.out.println("stepName = " + stepName);
				System.out.println("status = " + status);
				System.out.println("exitStatus = " + exitStatus);
			}
		}
	}
}
