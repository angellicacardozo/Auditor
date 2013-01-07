package br.com.auditor.domain;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public interface AgeCounterAlgorithmBase {
	public ETicketAgeGroupPolicies calculateAge(Ticket ticket);
}
