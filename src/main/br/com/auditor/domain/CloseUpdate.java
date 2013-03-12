package br.com.auditor.domain;

public class CloseUpdate extends Update {

	private String closeClassification;
	protected String classification = EUpdateClassification.CLOSE;
	protected String state= EStates.CLOSED;
	
	public String getCloseClassification() {
		return closeClassification;
	}

	public void setCloseClassification(String closeClassification) {
		this.closeClassification = closeClassification;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getClassification() {
		return this.classification;
	}
	
	public void setClassification(String classification) {
		// nothing to do here
	}
	
	public void setState(String state) {
		// nothing to do here
	}
	
}
