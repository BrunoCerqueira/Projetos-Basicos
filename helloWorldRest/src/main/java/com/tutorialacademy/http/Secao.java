package com.tutorialacademy.http;
 
 
import javax.xml.bind.annotation.XmlRootElement;

import com.tutorialacademy.repository.entity.SecaoEntity;
import com.tutorialacademy.repository.entity.enumerator.TipoBebida;
 
@XmlRootElement
public class Secao {
 
	private Long id;
	
	
	
	private String tipoBebida;
	private String nomeSecao;
	private Long volumeOcupado;
	private Long volumeVago;
	public Secao(){
 
	}
	public Secao(Long id, TipoBebida tipoBebida,String nome ) {
		this.id = id;
		this.nomeSecao = nome;
		this.tipoBebida = tipoBebida.getDescricao();
	}
	public Secao(SecaoEntity secao) {
		this.id = secao.getId();
		this.nomeSecao = secao.getNome();
		this.tipoBebida = secao.getTipoBebida().getDescricao();
		this.setVolumeOcupado(secao.getVolumeOcupado() == null ? 0 : secao.getVolumeOcupado() );
		this.setVolumeVago(SecaoEntity.VOLUME_MAXIMO_SECAO - this.getVolumeOcupado());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 
	public String getTipoBebida() {
		return tipoBebida;
	}
	public void setTipoBebida(String tipoBebida) {
		this.tipoBebida = tipoBebida;
	}
	public String getNome() {
		return nomeSecao;
	}
	public void setNome(String nome) {
		this.nomeSecao = nome;
	}
	public Long getVolumeOcupado() {
		return volumeOcupado;
	}
	public void setVolumeOcupado(Long volumeOcupado) {
		this.volumeOcupado = volumeOcupado;
	}
	public Long getVolumeVago() {
		return volumeVago;
	}
	public void setVolumeVago(Long volumeVago) {
		this.volumeVago = volumeVago;
	}

}