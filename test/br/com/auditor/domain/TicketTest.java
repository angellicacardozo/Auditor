package br.com.auditor.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicketTest {

	/**
		devem ser testados
		condições, loops, operações e polimorfismos, porém, somente aqueles que o próprio
		desenvolvedor escreveu
	 */
	
	@Test
	public void testGetNumber() {
		String ticketNumber= "2012120410000091";
		Ticket ticket= new Ticket("2012120410000091");
		assertTrue(ticketNumber.compareTo(ticket.getNumber())==1);
	}

}
