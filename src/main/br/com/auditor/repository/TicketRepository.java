package br.com.auditor.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.auditor.domain.Ticket;
import br.com.auditor.policies.TicketFirstResponseSLAPolicy;

/**
 * Um repositório contém todas as instâncias de um tipo específico, mas isso não significa que você precise de um REPOSITÓRIO para casa classe.
 * O tipo pode ser uma superclasse abstrata de uma hierarquia.
 * O REPOSITÓRIO vai delegar à devida infra-estrutura serviços para que uma tarefa seja cumprida. 
 * O encapsulamento dos mecanismos de armazenagem, recuperação e consulta é a característica mais básica da implantação de um REPOSITÓRIO.
 * 
 * @author angelica.araujo
 *
 */

public class TicketRepository {

	
	/**
	 * Return all tickets with first response delayed according with predefined policies
	 * @return
	 */
	public static List<Ticket> listFirstResponseSLABrokers(List<Ticket> tickets) {
		
		if(tickets.size()==0) {
			return null;
		}
		
		List<Ticket> slaBrokers = new ArrayList<Ticket>();
		
		for (Ticket ticket : tickets) {
			if(!TicketFirstResponseSLAPolicy.isValid(ticket)) {
				slaBrokers.add(ticket);
			}
		}
		
		return slaBrokers;
	}
	
}