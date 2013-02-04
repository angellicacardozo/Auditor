package br.com.auditor.policies;

import br.com.auditor.domain.Ticket;

public class TicketNewStatePolicie {

	public static Boolean validate(Ticket ticket) {
		
		if(ticket.getTotalOpenDays() < 6) {
			return true;
		}
		
		return false;
	}
}
