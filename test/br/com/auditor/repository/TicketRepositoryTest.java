package br.com.auditor.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import br.com.auditor.domain.EUpdateClassification;
import br.com.auditor.domain.Ticket;
import br.com.auditor.domain.Update;

public class TicketRepositoryTest {

	@Test
	public void testListFirstResponseSLABrokers() {
		
		List<Ticket> ticketList= new ArrayList<Ticket>();
		
		Ticket ticketWithOutFirstResponse= new Ticket("123456789", new GregorianCalendar(2013,01,01));
		Ticket ticketWithFirstResponseDelay= new Ticket("987654321", new GregorianCalendar(2013,01,01));
				Update update= new Update();
				update.setDate(new GregorianCalendar(2013,01,15));
				update.setClassification(EUpdateClassification.FIRSTRESPONSE);
				ticketWithFirstResponseDelay.applyUpdate(update);
				
		ticketList.add(ticketWithOutFirstResponse);
		ticketList.add(ticketWithFirstResponseDelay);
		
		List<Ticket> slaBrokers= TicketRepository.listFirstResponseSLABrokers(ticketList);
		
		assertTrue(slaBrokers.size() == 2);
	}

}
