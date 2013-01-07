package br.com.auditor.domain;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public class ExpertTicketAlgorithm implements AgeCounterAlgorithmBase {

	@Override
	public ETicketAgeGroupPolicies calculateAge(Ticket ticket) {
		if((ticket.getTotalOpenDays() >= 11 && ticket.getTotalOpenDays() <= 30) && EStates.CLOSED.compareTo(ticket.getCurrentState().getState())!=0) {
			return ETicketAgeGroupPolicies.BETWEENELEVENANDTHIRTY;
		}
		
		ticket.setAgeCounter(new MasterTicketAlgorithm());
		
		return ticket.getAge();
	}

}
