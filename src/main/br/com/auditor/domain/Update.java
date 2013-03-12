package br.com.auditor.domain;

import java.util.Calendar;

public class Update {
	private Quee quee;
	protected String state;
	private Calendar date;
	private String owner= new String("SYSTEM");
	protected String classification;
	
	public Quee getQuee() {
		return quee;
	}
	public void setQuee(Quee quee) {
		this.quee = quee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public void setOwner(String owner) {
		this.owner= owner;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getClassification() {
		return classification;
	}
	
	public void setClassification(String classification) {
		this.classification = classification;
	}
}
