package br.com.auditor.extractor;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.auditor.domain.ARTicket;
import br.com.auditor.domain.EStates;
import br.com.auditor.domain.EUpdateClassification;
import br.com.auditor.domain.Quee;
import br.com.auditor.domain.Ticket;
import br.com.auditor.domain.Update;
import br.com.auditor.policies.TicketNewStatePolicie;

import au.com.bytecode.opencsv.CSVReader;

public class ExcelDataExtractor {

	public static List<Ticket> extractTicketListFromCSVData(String filepath, List<Quee> quees) {
		
		List<Ticket> tickets= new ArrayList<Ticket>();
		
		try {
			CSVReader reader= new CSVReader(new FileReader(filepath));
			String[] nextLine;
			
			while((nextLine= reader.readNext()) != null) {
				
				// nextLine[] is an array of values from the line
				// We will check if the line corresponds to ticket line
				if(isTicketInformationLine(nextLine[0])) {
					tickets.add(openTicketByLineInformation(nextLine[0], quees));
				}
			}
			
			reader.close();
			
		} catch(Exception e){
			System.out.println("Something wasnt in our plans..."+e.getMessage());
			System.out.println("Caused by: "+e.getCause());
		}
		
		return tickets;
	}
	
	public static boolean isTicketInformationLine(String line) {
		String pattern= "([0-9]{16}).*";

		return line.matches(pattern);
	}
	
	public static Ticket openTicketByLineInformation(String line, List<Quee> quees) {
		
		String[] values= line.split(";");
		
		Ticket ticket;
		Quee ticketQuee= quees.get(0);
		String queeExcelData, ticketTitle;
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
	    try {
	    	
	    	String formatedDate= values[3].replaceAll("(^\")|(\"$)", "");
		    calendar.setTime(sdf.parse(formatedDate));
		    
		    queeExcelData= values[6].replaceAll("(^\")|(\"$)", "");
		    for (Quee quee : quees) {
		    	if(queeExcelData.contains(quee.getName())) {
		    		ticketQuee= quee;
		    	}
			}
		    
	    	String arNumber= values[12].replaceAll("(^\")|(\"$)", "");
	    	String number= values[0].replaceAll("(^\")|(\"$)", "");
	    	if(arNumber.isEmpty()) {
	    		ticket= new Ticket(number, calendar);
	    	} else {
	    		ticket= new ARTicket(number, calendar);
	    		((ARTicket) ticket).setArNumber(arNumber);
	    	}
	    	
	    	
	    	ticketTitle= values[2].replaceAll("(^\")|(\"$)", "");
	    	ticketTitle= ticketTitle.replaceAll(";", "");
	    	ticket.setTitle(ticketTitle);
	    	
			Update firstUpdate= new Update();
			firstUpdate.setState(EStates.OPEN);
			firstUpdate.setQuee(ticketQuee);
	    	// We need to set the first ticket update
	    	if(TicketNewStatePolicie.validate(ticket)) {
	    		firstUpdate.setState(EStates.NEW);
	    	} else {
	    		firstUpdate.setState(EStates.OPEN);
	    	}
	    	ticket.applyUpdate(firstUpdate);
	    	
	    	String firstResponse= values[1].replaceAll("(^\")|(\"$)", "");
		    if(!firstResponse.isEmpty()) {
		    	Update firstResponseUpdate= new Update();
		    	firstResponseUpdate.setClassification(EUpdateClassification.FIRSTRESPONSE);
		    	calendar.setTime(sdf.parse(formatedDate));
		    	firstResponseUpdate.setDate(calendar);
		    	ticket.applyUpdate(firstResponseUpdate);
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
		    	update.setQuee(ticketQuee);
		    	ticket.applyUpdate(update);
		    }
		    
		    return ticket;
	    
		} catch (ParseException e) {
			System.out.println("\n\nERROR: We found a problem parsing date value ..."+e.getMessage()+"\n\n");
		}
		
		return null;
	}
}
