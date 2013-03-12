package br.com.auditor.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.auditor.domain.Ticket;
import br.com.auditor.policies.TicketFirstResponseSLAPolicy;

/**
 * Um reposit�rio cont�m todas as inst�ncias de um tipo espec�fico, mas isso n�o significa que voc� precise de um REPOSIT�RIO para casa classe.
 * O tipo pode ser uma superclasse abstrata de uma hierarquia.
 * O REPOSIT�RIO vai delegar � devida infra-estrutura servi�os para que uma tarefa seja cumprida. 
 * O encapsulamento dos mecanismos de armazenagem, recupera��o e consulta � a caracter�stica mais b�sica da implanta��o de um REPOSIT�RIO.
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