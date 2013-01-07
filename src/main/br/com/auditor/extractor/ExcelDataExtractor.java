package br.com.auditor.extractor;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.auditor.domain.ARTicket;
import br.com.auditor.domain.EQuee;
import br.com.auditor.domain.EStates;
import br.com.auditor.domain.Ticket;
import br.com.auditor.domain.Update;

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
					tickets.add(openTicketByLineInformation(nextLine[0]));
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
		
		Ticket ticket;
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
	    try {
	    	
	    	String formatedDate= values[3].replaceAll("(^\")|(\"$)", "");
		    calendar.setTime(sdf.parse(formatedDate));
		    
		    // We need to find the current quee
	    	String quee= values[6].replaceAll("(^\")|(\"$)", "");
	    	
	    	switch (quee) {
			default:
				quee= EQuee.TRIAGEM;
				break;
			}
		    
	    	String arNumber= values[11].replaceAll("(^\")|(\"$)", "");
	    	if(arNumber.isEmpty()) {
	    		ticket= new Ticket(values[0], calendar, quee);
	    	} else {
	    		ticket= new ARTicket(values[0], calendar, quee);
	    		((ARTicket) ticket).setArNumber(arNumber);
	    	}
		    
		    // We need to set up Ticket state
		    // Index 5 indicates the closed date
		    formatedDate= values[5].replaceAll("(^\")|(\"$)", "");
			if(!formatedDate.isEmpty()) {
		    	Update update= new Update();
		    	// If empty, the ticket is still open
		    	calendar.setTime(sdf.parse(formatedDate));
		    	update.setState(EStates.CLOSED);
		    	update.setDate(calendar);
		    	update.setQuee(quee);
		    	ticket.applyUpdate(update);
		    }
		    
		    return ticket;
	    
		} catch (ParseException e) {
			System.out.println("\n\nERROR: We found a problem parsing date value ..."+e.getMessage()+"\n\n");
		}
		
		return null;
	}
}
