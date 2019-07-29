package com.fileEncryption.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EncryptFileProcessor implements ItemProcessor<String, String> {

	private static final Logger log = LoggerFactory.getLogger(EncryptFileProcessor.class);
	
	@Override
	public String process(String item) throws Exception {
		log.info("Processing line " + item);
		String encryptedLine = encryptLineUsingCeaserCipher(item).toString();
		log.info("Encrypted line " + item);
		return encryptedLine;
	}
	
	private StringBuffer encryptLineUsingCeaserCipher(String line) {
		StringBuffer result= new StringBuffer(); 
		int shift = 1;
        for (int i=0; i<line.length(); i++) 
        { 
            if (Character.isUpperCase(line.charAt(i))) 
            { 
                char ch = (char)(((int)line.charAt(i) + shift - 65) % 26 + 65); 
                result.append(ch); 
            } 
            else
            { 
                char ch = (char)(((int)line.charAt(i) + shift - 97) % 26 + 97); 
                result.append(ch); 
            } 
        } 
        return result; 
	}

}
