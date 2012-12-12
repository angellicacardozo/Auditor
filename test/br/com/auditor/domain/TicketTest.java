package br.com.auditor.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

public class TicketTest {
	
	static Ticket ticket;
	
	  @BeforeClass 
	  public static void testSetup(){
		  ticket= new Ticket("2012120410000091", new GregorianCalendar(2012, 01, 01));
	  }

	/**
		devem ser testados
		condi��es, loops, opera��es e polimorfismos, por�m, somente aqueles que o pr�prio
		desenvolvedor escreveu
	 */
	
	@Test
	public void testGetNumber() {
		assertTrue(ticket.getNumber()!=null);
	}
	
	@Test
	public void testGetOpenDate() {
		assertTrue(ticket.getOpenDate()!=null);
	}
	
	/**
		Os testes seriam os primeiros clientes a usarem as
		classes desenvolvidas, mostrando ao desenvolvedor, o que � necess�rio fazer
	 */
	
	/**
	 * 
	 */
	@Test
	public void testObtaningCurrentStateInNewInstance() {
		Update currentState= ticket.getCurrentState();
		assertTrue(EStates.NEW.compareTo(currentState.getState())==0);
	}

}
