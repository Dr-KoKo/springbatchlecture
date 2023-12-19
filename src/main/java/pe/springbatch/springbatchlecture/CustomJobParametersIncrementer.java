package pe.springbatch.springbatchlecture;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomJobParametersIncrementer implements JobParametersIncrementer {
	static final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public JobParameters getNext(JobParameters parameters) {
		String id = format.format(LocalDateTime.now());
		return new JobParametersBuilder().addString("run.id", id).toJobParameters();
	}
}
