package br.com.auditor.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Ticket {
	
	private String number;
	private Calendar openDate;
	private List<Update> updates = new ArrayList<Update>();
	
	public Ticket(String number, Calendar openDate) {
		this.number= number;
		this.openDate= openDate;
		
		/**
		 * @todo checkup if this is a new ticket and set NEW states by default
		 * New ticket - a ticket with NO previous state
		 */
		if(this.getCurrentState()==null) {
			Update update= new Update();
			update.setState(EStates.NEW);
			this.updates.add(update);
		}
	}
	
	public String getNumber() {
		return this.number;
	}
	
	public Calendar getOpenDate() {
		return this.openDate;
	}
	
	/**
	 * Aplly an update to a ticket
	 * @param quee
	 */
	public void applyUpdate(Update change) {
		this.updates.add(change);
	}
	
	/**
	 * 
	 * @return Update a ticket current state
	 */
	public Update getCurrentState() {
		if(this.updates.isEmpty()) {
			return null;
		}
		
		return this.updates.get(this.updates.size()-1);
	}
	
	public String getCurrentOwner() {
		if(this.getCurrentState()==null) {
			return null;
		}
		
		Update currentState= this.getCurrentState();
		return currentState.getOwner();
	}
	
	public int getTotalOpenDays() {
		Date past = this.getOpenDate().getTime();
		Date today= new GregorianCalendar().getTime();
		
		return Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
	}
	
}
