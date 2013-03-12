package br.com.auditor.policies;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import br.com.auditor.domain.Ticket;

public class TicketFirstResponseSLAPolicy {

	public static boolean isValid(Ticket ticket) {
		
		if(ticket.getTotalOpenDays() > 3 && ticket.getFirstResponseDate()==null){
			return false;
		}
		
		LocalDate openDate = LocalDate.fromCalendarFields(ticket.getOpenDate());
		LocalDate firstResponseDate= LocalDate.fromCalendarFields(ticket.getFirstResponseDate());
		
		int totalDays= Days.daysBetween(openDate.toDateMidnight(), firstResponseDate.toDateMidnight()).getDays();
		if(totalDays <= 2) {
			return true;
		}
			
		return false;
	}

}
