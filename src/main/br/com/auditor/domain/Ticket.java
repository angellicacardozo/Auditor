package br.com.auditor.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import br.com.auditor.policies.ETicketAgeGroupPolicies;

public class Ticket {
	
	private String number;
	private String title;
	private Calendar openDate;
	private List<Update> updates = new ArrayList<Update>();
	private AgeCounterAlgorithmBase ageCounter;
	
	public Ticket(String number, Calendar openDate) {
		this.number= number;
		this.openDate= openDate;
		this.setAgeCounter(new FreshlyTicketAlgorithm());
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
		
		Update currentState= this.getCurrentState();
		if(currentState!=null) {
			if(change.getOwner()==null) {
				change.setOwner(currentState.getOwner());
			}
			
			if(change.getQuee()==null) {
				change.setQuee(currentState.getQuee());
			}
			
			if(change.getState()==null) {
				change.setState(currentState.getState());
			}
		}
		
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
		LocalDate past = LocalDate.fromCalendarFields(this.getOpenDate());
		LocalDate today= LocalDate.now();
		
		return Days.daysBetween(past.toDateMidnight(), today.toDateMidnight()).getDays();
	}
	
	public String toString() {
		DateTime jodaTime = new DateTime(this.getOpenDate().getTimeInMillis(),DateTimeZone.forTimeZone(TimeZone.getTimeZone("US/Central")));
		return this.getTitle()+"\nNumero do ticket: "+this.getNumber()+", Data de abertura do chamado: "+jodaTime+", Total de dias em aberto: "+this.getTotalOpenDays()+", Estado: "+this.getCurrentState().getState();
	}

	public void setAgeCounter(AgeCounterAlgorithmBase ageCounter) {
		this.ageCounter = ageCounter;
	}
	
	public ETicketAgeGroupPolicies getAge() {
		return this.ageCounter.calculateAge(this);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Calendar getFirstResponseDate() {
		
		if(this.updates.size() == 0) {
			return null;
		}
		
		Update firstResponse = null;
		for (Update update : this.updates) {
			if(update.getClassification()!=null && update.getClassification().compareTo(EUpdateClassification.FIRSTRESPONSE)==0) {
				firstResponse= update;
				break;
			}
		}
		
		return firstResponse.getDate();
	}
	
}
