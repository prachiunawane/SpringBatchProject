package com.fileEncryption.joblauncher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {
	
	@Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job job;
 
    @GetMapping("/encryptFile")
    public String encryptFileJobLauncher(@RequestParam(name="fileName") String fileName, 
    										@RequestParam(name = "threads") String threadCount) throws Exception {
 
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
        	JobParameters jobParameters = new JobParametersBuilder().addString("fileName", fileName)
        														 .addLong("threads", Long.parseLong(threadCount)).toJobParameters();
                    
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return "Done";
    }

}
