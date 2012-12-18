package br.com.auditor.extractor;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.auditor.domain.Ticket;

import au.com.bytecode.opencsv.CSVReader;

public class ExcelDataExtractor {

	public static List<Ticket> extractTicketListFromCSVData(String filepath) {
		
		List<Ticket> tickets= new ArrayList<Ticket>();
		
		try {
			CSVReader reader= new CSVReader(new FileReader(filepath));
			String[] nextLine;
			
			while((nextLine= reader.readNext()) != null) {
				
				// nextLine[] is an array of values from the line
				// We will check if the line corresponds to ticket line
				if(isTicketInformationLine(nextLine[0])) {
					System.out.println("Line value: " + nextLine[0]);
				}
			}
			
		} catch(Exception e){
			System.out.println("Something wasnt in our plans..."+e.getMessage());
		}
		
		return tickets;
	}
	
	public static boolean isTicketInformationLine(String line) {
		String pattern= "([0-9]{16}).*";

		return line.matches(pattern);
	}
	
	public static Ticket openTicketByLineInformation(String line) {
		String[] values= line.split(";");
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
	    try {
			calendar.setTime(sdf.parse(values[3]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new Ticket(values[0], calendar);
	}
}
