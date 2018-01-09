package com.ffraporti.desafio;

public class Provider {

	private int id;
	
	private String name;
	
	private String email;
	
	private String comment;
	
	private String cnpj;
		
	public Provider() {
		
	}

	public Provider(int id, String name, String email, String comment, String cnpj) {
		super();
		setId(id);
		setName(name);
		setEmail(email);
		setComment(comment);
		setCnpj(cnpj);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	
}
