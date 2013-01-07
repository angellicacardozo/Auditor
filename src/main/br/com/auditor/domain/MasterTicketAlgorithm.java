package br.com.auditor.domain;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public class MasterTicketAlgorithm implements AgeCounterAlgorithmBase {

	@Override
	public ETicketAgeGroupPolicies calculateAge(Ticket ticket) {
		if((ticket.getTotalOpenDays() >= 31 && ticket.getTotalOpenDays() <= 59) && EStates.CLOSED.compareTo(ticket.getCurrentState().getState())!=0) {
			return ETicketAgeGroupPolicies.BETWEENTHIRTYORSIXTYDAYS;
		}
		
		ticket.setAgeCounter(new SeniorTicketAlgorithm());
		
		return ticket.getAge();
	}

}
