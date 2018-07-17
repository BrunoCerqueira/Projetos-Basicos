package com.distribuidorabebidas.repository.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.distribuidorabebidas.repository.entity.enumerator.TipoBebida;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO",discriminatorType=DiscriminatorType.INTEGER)
@Table(name="BEBIDA")
@NamedQueries({
	@NamedQuery(name=BebidaEntity.BEBIDAS_BY_SETOR, 
			query = "SELECT b.nome, b.id FROM BebidaEntity b JOIN b.registrosEstoque e  WHERE e.secao.id = :idSecao and e.tipoEstoque = 1"),
	 
})
public abstract class BebidaEntity {
	public static final String BEBIDAS_BY_SETOR = "BebidaEntity.getBebidasBySetor";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="TIPO_BEBIDA")
	@Enumerated(EnumType.ORDINAL)
	private TipoBebida tipoBebida;
	
	private String nome;
	
	@OneToMany(mappedBy="bebida")
	private List<EstoqueEntity> registrosEstoque;
	
	public BebidaEntity() {
	}
	public BebidaEntity(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	} 
	public void setId(Long id) {
		this.id = id;
	}
	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}
	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}
	public List<EstoqueEntity> getRegistrosEstoque() {
		return registrosEstoque;
	}
	public void setRegistrosEstoque(List<EstoqueEntity> registrosEstoque) {
		this.registrosEstoque = registrosEstoque;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	 
	
	
	
}
