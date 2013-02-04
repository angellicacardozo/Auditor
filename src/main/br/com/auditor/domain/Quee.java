package br.com.auditor.domain;

public class Quee {
	private String name;
	private String alias;

	public Quee(String name, String alias) {
		this.name= name;
		this.setAlias(alias);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
