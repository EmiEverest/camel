package com.eip.demo.model;

public class Post {
	private Integer id;
	private String messaggio;
	private String destinatari;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public Post(Integer id, String messaggio, String destinatari) {
		super();
		this.id = id;
		this.messaggio = messaggio;
		this.destinatari = destinatari;
	}
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
