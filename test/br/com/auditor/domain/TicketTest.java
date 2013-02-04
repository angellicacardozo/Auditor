package br.com.auditor.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.auditor.policies.TicketNewStatePolicie;

public class TicketTest {
	
	static Ticket ticket;
	
	  @BeforeClass 
	  public static void testSetup(){
		  ticket= new Ticket("2012120410000091", new GregorianCalendar(2012, 01, 01));
	  }

	/**
		devem ser testados
		condições, loops, operações e polimorfismos, porém, somente aqueles que o próprio
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
		classes desenvolvidas, mostrando ao desenvolvedor, o que é necessário fazer
	 */
	
	/**
	 * When a ticket open for the first time, it must not contains any update
	 * 	setting its states to NEW
	 */
	@Test
	public void testObtaningCurrentStateInNewInstance() {
		assertTrue(ticket.getCurrentState()==null);
	}
	
	/**
	 * When we set up a ticket owner, it must be registered as an update
	 * This update must indicate that is an owner ticket update
	 */
	@Test
	public void testChangingTicketOwner() {
		String newOwnerName= new String("Paul");
		
		Update ownerUpdate= new Update();
		ownerUpdate.setDate(new GregorianCalendar());
		ownerUpdate.setOwner(newOwnerName);
		
		ticket.applyUpdate(ownerUpdate);
		
		assertTrue(newOwnerName.compareTo(ticket.getCurrentOwner())==0);
	}
	
	
	/**
	 * When we change the ticket quee, it must be registered as an update
	 * This update must indicate thar is a quee update
	 */
	@Test
	public void testChangingTicketQuee() {
		Quee newQuee = new Quee("Other quee", "Other quee");
		
		Update queeUpdate= new Update();
		queeUpdate.setQuee(newQuee);
		
		ticket.applyUpdate(queeUpdate);
		
		assertTrue(newQuee.getName().compareTo(ticket.getCurrentState().getQuee().getName())==0);
	}
	
	/**
	 * When we change the ticket state, it must be registered as an update
	 * this update must indicate that is a state update
	 */
	@Test
	public void testChangingTicketState() {
		Update stateUpdate= new Update();
		stateUpdate.setState(EStates.PENDING);
		
		ticket.applyUpdate(stateUpdate);
		
		assertTrue(EStates.PENDING.compareTo(ticket.getCurrentState().getState())==0);
	}
	
	/**
	 * We also need to know the amount of days that the ticket is open
	 */
	@Test
	public void testRetrieveTicketTotalOpenDays() {
		Date past = ticket.getOpenDate().getTime();
		Date today= new GregorianCalendar().getTime();
		
		int totalDays= Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
		
		assertTrue(ticket.getTotalOpenDays()==totalDays);
	}
	
	@Test
	public void testStatePolicieWithAnNewTicket() {
		
		Ticket ticket= new Ticket("My ticket that must be instantiated with NEW state", new GregorianCalendar());
		
		if(TicketNewStatePolicie.validate(ticket)) {
			Update stateUpdate= new Update();
			stateUpdate.setState(EStates.NEW);
			ticket.applyUpdate(stateUpdate);
		}
		
		assertTrue(EStates.NEW.compareTo(ticket.getCurrentState().getState())==0);
	}
}
