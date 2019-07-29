package com.fileEncryption.tasks;



import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;

public class EncryptFileTask implements ItemWriter {

	@Override
	public void write(List items) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
