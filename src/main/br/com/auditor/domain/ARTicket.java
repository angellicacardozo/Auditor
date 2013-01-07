package br.com.auditor.domain;

import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ARTicket extends Ticket {

	private String arNumber;
	
	public ARTicket(String number, Calendar openDate, String quee) {
		super(number, openDate, quee);
	}

	public String getArNumber() {
		return arNumber;
	}

	public void setArNumber(String arNumber) {
		this.arNumber = arNumber;
	}
	
	public String toString() {
		DateTime jodaTime = new DateTime(this.getOpenDate().getTimeInMillis(),DateTimeZone.forTimeZone(TimeZone.getTimeZone("US/Central")));
		return "AR:"+this.getArNumber()+", Numero do ticket: "+this.getNumber()+", Data de abertura do chamado: "+jodaTime+", Total de dias em aberto: "+this.getTotalOpenDays();
	}
	
}
