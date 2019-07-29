package com.fileEncryption.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class FileEncryptionReader implements ItemReader<String> {
	
	private BufferedReader bin;
	private static final Logger log = LoggerFactory.getLogger(FileEncryptionReader.class);
	 
	public FileEncryptionReader(String fileName) {
		try {
			this.bin = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			log.error("File Not found " + e);
		}
	}
	
	public FileEncryptionReader()
	{
		
	}

	@Override
	public synchronized String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		System.out.println("Reading line");
		
		String text = "";
		while(text != null)
		{
			text = bin.readLine();
			System.out.println(text);
			return text;
		}
		return null;		
	}


}
