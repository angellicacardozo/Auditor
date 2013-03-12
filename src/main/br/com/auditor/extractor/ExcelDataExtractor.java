package br.com.auditor.extractor;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.auditor.domain.ARTicket;
import br.com.auditor.domain.CloseUpdate;
import br.com.auditor.domain.EStates;
import br.com.auditor.domain.EUpdateClassification;
import br.com.auditor.domain.Quee;
import br.com.auditor.domain.Ticket;
import br.com.auditor.domain.Update;
import br.com.auditor.policies.TicketNewStatePolicy;

import au.com.bytecode.opencsv.CSVReader;

public class ExcelDataExtractor {
	
	final static int QUEE_POSITION= 6;
	final static int AR_POSITION=13;
	final static int TICKET_NUMBER=0;
	final static int TICKET_TITLE_POSITION=2;
	final static int FIRST_RESPONSE_POSITION=12;
	final static int CLOSED_DATE_POSITION= 5;
	final static int STATE_POSITION=7;
	final static int OPEN_DATE_POSITION=3;
	final static int CLIENT_POSITION= 9;
	final static int CLOSE_CLASSIFICATION= 16;

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
	    	
	    	String formatedDate= values[OPEN_DATE_POSITION].replaceAll("(^\")|(\"$)", "");
		    calendar.setTime(sdf.parse(formatedDate));
		    
		    queeExcelData= values[QUEE_POSITION].replaceAll("(^\")|(\"$)", "");
		    for (Quee quee : quees) {
		    	if(queeExcelData.contains(quee.getName())) {
		    		ticketQuee= quee;
		    	}
			}
		    
	    	String arNumber= values[AR_POSITION].replaceAll("(^\")|(\"$)", "");
	    	String number= values[TICKET_NUMBER].replaceAll("(^\")|(\"$)", "");
	    	if(arNumber.isEmpty()) {
	    		ticket= new Ticket(number, calendar);
	    	} else {
	    		ticket= new ARTicket(number, calendar);
	    		((ARTicket) ticket).setArNumber(arNumber);
	    	}
	    	
	    	ticketTitle= values[TICKET_TITLE_POSITION].replaceAll("(^\")|(\"$)", "");
	    	ticketTitle= ticketTitle.replaceAll(";", "");
	    	ticket.setTitle(ticketTitle);
	    	
	    	String client= values[CLIENT_POSITION].replaceAll("(^\")|(\"$)", "");
	    	ticket.setClient(client);
	    	
			Update firstUpdate= new Update();
			firstUpdate.setState(EStates.OPEN);
			firstUpdate.setQuee(ticketQuee);
			firstUpdate.setDate(calendar);
	    	// We need to set the first ticket update
	    	if(TicketNewStatePolicy.validate(ticket)) {
	    		firstUpdate.setState(EStates.NEW);
	    	} else {
	    		firstUpdate.setState(EStates.OPEN);
	    	}
	    	ticket.applyUpdate(firstUpdate);
	    	
	    	formatedDate= values[FIRST_RESPONSE_POSITION].replaceAll("(^\")|(\"$)", "");
		    if(!formatedDate.isEmpty()) {
		    	Update firstResponseUpdate= new Update();
		    	firstResponseUpdate.setClassification(EUpdateClassification.FIRSTRESPONSE);
		    	Calendar firstResponseCalendar = Calendar.getInstance();
		    	firstResponseCalendar.setTime(sdf.parse(formatedDate));
		    	firstResponseUpdate.setDate(firstResponseCalendar);
		    	ticket.applyUpdate(firstResponseUpdate);
		    }
	    	
		    // We need to set up Ticket state
		    String ticket_state= values[STATE_POSITION].replaceAll("(^\")|(\"$)", "");
		    if(ticket_state.contains("closed") || ticket_state.contains("o aplicavel")) {
			    // This ticket is closed
			    formatedDate= values[CLOSED_DATE_POSITION].replaceAll("(^\")|(\"$)", "");
				if(!formatedDate.isEmpty()) {
			    	CloseUpdate update= new CloseUpdate();
			    	calendar = Calendar.getInstance();
			    	Calendar closeCalendar = Calendar.getInstance();
			    	closeCalendar.setTime(sdf.parse(formatedDate));
			    	update.setDate(closeCalendar);
			    	update.setQuee(ticketQuee);
			    	update.setCloseClassification(values[CLOSE_CLASSIFICATION].replaceAll("(^\")|(\"$)", ""));
			    	ticket.applyUpdate(update);
			    }
		    }
		    
		    return ticket;
	    
		} catch (ParseException e) {
			System.out.println("\n\nERROR: We found a problem parsing date value ..."+e.getMessage()+"\n\n");
		}
		
		return null;
	}
}
