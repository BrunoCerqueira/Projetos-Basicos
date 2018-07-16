package com.tutorialacademy.repository.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ESTOQUE")
@NamedQueries({
	@NamedQuery(name=EstoqueEntity.RECUPERAR_VOLUME_ESTOQUE_POR_TIPO_BEBIDA, 
			query="SELECT SUM(e.volume),b.tipoBebida FROM EstoqueEntity e JOIN e.bebida b WHERE b.tipoBebida = :tipoBebida GROUP BY b.tipoBebida"
		 )
	
})
public class EstoqueEntity {
	public static final String RECUPERAR_VOLUME_ESTOQUE_POR_TIPO_BEBIDA = "EstoqueEntity.recuperarVolumeEstoquePorTipoBebida";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoAcao tipoEstoque;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="ID_SECAO")
	private SecaoEntity secao;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_BEBIDA")
	private BebidaEntity bebida;
	
	private Long volume;
	
	@ManyToOne
	@JoinColumn(name="ID_RESPONSAVEL")
	private PessoaEntity responsavel;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	public enum TipoAcao{
		RETIRADA,
		INSERCAO;
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


	public BebidaEntity getBebida() {
		return bebida;
	}


	public void setBebida(BebidaEntity bebida) {
		this.bebida = bebida;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public Long getVolume() {
		return volume;
	}


	public void setVolume(Long volume) {
		this.volume = volume;
	}


	public PessoaEntity getResponsavel() {
		return responsavel;
	}


	public void setResponsavel(PessoaEntity responsavel) {
		this.responsavel = responsavel;
	}


	public SecaoEntity getSecao() {
		return secao;
	}


	public void setSecao(SecaoEntity secao) {
		this.secao = secao;
	}

}
