package com.distribuidorabebidas.http;
 
 
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.distribuidorabebidas.repository.entity.EstoqueEntity;
import com.distribuidorabebidas.repository.entity.EstoqueEntity.TipoAcao;
 
@XmlRootElement
public class Estoque {
	private Long id;
	private TipoAcao tipoEstoque;
	private Secao secao;
	private Long bebida;
	private Long volume;
	private Long responsavel;
	private Date data;
	public Estoque(){
 
	}
	public Estoque(EstoqueEntity estoque){
		this.setBebida((long) estoque.getBebida().getTipoBebida().ordinal());
		this.setVolume(estoque.getVolume());
//		this.setResponsavel(estoque.getResponsavel());
//		this.
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoAcao getTipoEstoque() {
		return tipoEstoque;
	}
	public void setTipoEstoque(TipoAcao tipoEstoque) {
		this.tipoEstoque = tipoEstoque;
	}
	
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	
	public Secao getSecao() {
		return secao;
	}
	public void setSecao(Secao secao) {
		this.secao = secao;
	}
	public Long getBebida() {
		return bebida;
	}
	public void setBebida(Long bebida) {
		this.bebida = bebida;
	}
	public Long getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
 
	
}