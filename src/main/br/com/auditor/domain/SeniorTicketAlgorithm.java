package br.com.auditor.domain;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public class SeniorTicketAlgorithm implements AgeCounterAlgorithmBase {

	@Override
	public ETicketAgeGroupPolicies calculateAge(Ticket ticket) {
		if(ticket.getTotalOpenDays() >= 60 && EStates.CLOSED.compareTo(ticket.getCurrentState().getState())!=0) {
			return ETicketAgeGroupPolicies.SIXTYOROLDER;
		}
		
		return null;
	}

}
