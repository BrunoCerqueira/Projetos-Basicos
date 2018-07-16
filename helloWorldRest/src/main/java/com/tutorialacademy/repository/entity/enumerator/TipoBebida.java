package com.tutorialacademy.repository.entity.enumerator;

public enum TipoBebida{
	
	ALCOOLICA ("Alcoolica"),
	NAO_ALCOOLICA("Nao Alcoolica");
	private String descricao;
	private TipoBebida(String descricao) {
		this.setDescricao(descricao);
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}