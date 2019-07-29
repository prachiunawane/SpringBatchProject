package com.fileEncryption.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.fileEncryption.listener.JobCompletionNotificationListener;
import com.fileEncryption.processor.EncryptFileProcessor;
import com.fileEncryption.reader.FileEncryptionReader;

@Configuration
public class FileEncryptionBatchConfig {
		
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    private static final String OVERRIDDEN_BY_EXPRESSION = null;
      
    @Bean
    public TaskExecutor taskExecutor(@Value("#{jobParameters[threads]}") int threads) {
    	System.out.println("threads received" + threads);
    	SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
    	taskExecutor.setConcurrencyLimit(threads);
    	return taskExecutor;
    }
     
    @Bean
    @StepScope
    public FileEncryptionReader reader(@Value("#{jobParameters[fileName]}") String fileName)
    {
    	System.out.println("file name received" + fileName);
    	return new FileEncryptionReader(fileName);
    }
    
    @Bean
    public EncryptFileProcessor processor()
    {
    	return new EncryptFileProcessor();
    }
    
    @Bean
    public FlatFileItemWriter<String> writer()
    {
        FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>();
        writer.setResource(new FileSystemResource("output/outputData.txt"));;     
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<String>());
        return writer;
    }
    
    @Bean
    public Job readFileForEncryption(JobCompletionNotificationListener listener) {
        return jobBuilderFactory
                .get("readFileForEncryption")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
	public Step step1() {
		return stepBuilderFactory
				.get("step1")
				.<String, String>chunk(10)
				.reader(reader(OVERRIDDEN_BY_EXPRESSION))
				.processor(processor())
				.writer(writer())
				.taskExecutor(taskExecutor(0))
				.build();
				
	}
}
