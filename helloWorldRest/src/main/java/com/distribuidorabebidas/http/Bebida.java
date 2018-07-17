package com.distribuidorabebidas.http;
 
 
import javax.xml.bind.annotation.XmlRootElement;

import com.distribuidorabebidas.repository.entity.enumerator.TipoBebida;
 
@XmlRootElement
public class Bebida {
	 public Bebida() {}
	 public Bebida(int codigo, String tipoBebida,String nome) {
		 this.codigo = codigo;
		 this.tipoBebida = tipoBebida;
		 this.nome = nome;
	 }
	private int codigo;
	private String tipoBebida;
	private String nome;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTipoBebida() {
		return tipoBebida;
	}
	public void setTipoBebida(String tipoBebida) {
		this.tipoBebida = tipoBebida;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
 
 
}