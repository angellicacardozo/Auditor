package br.com.auditor.domain;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public final class FreshlyTicketAlgorithm implements AgeCounterAlgorithmBase {

	@Override
	public ETicketAgeGroupPolicies calculateAge(Ticket ticket) {
		
		if(ticket.getTotalOpenDays() <= 10 && EStates.CLOSED.compareTo(ticket.getCurrentState().getState())!=0) {
			return ETicketAgeGroupPolicies.LESSTHANOREQUALTENDAYS;
		}
		
		ticket.setAgeCounter(new ExpertTicketAlgorithm());
		
		return ticket.getAge();
	}

}
