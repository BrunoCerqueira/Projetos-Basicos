package com.tutorialacademy.repository.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tutorialacademy.http.Secao;
import com.tutorialacademy.repository.entity.enumerator.TipoBebida;

@Entity
@Table(name="SECAO")
@NamedQueries({
	@NamedQuery(name=SecaoEntity.RECUPERAR_SECOES_COM_ESPACO_DISPONIVEL_POR_TIPO_BEBIDA,
			query="Select  DISTINCT s.id, s.nome, SUM(e.volume) From SecaoEntity s "
					+ "LEFT Join s.estoque e LEFT JOIN e.bebida b"
					+ " WHERE s.estoque IS EMPTY OR (b.tipoBebida = :tipoBebida and e.tipoEstoque = 1)"
					+ " GROUP BY s.id,s.nome HAVING  s.estoque IS EMPTY OR (SUM(e.volume)- "
					+ "(SELECT COALESCE(SUM(e1.volume),0)  FROM SecaoEntity s1 Join s1.estoque e1 join e1.bebida b1 WHERE s.id=s1.id and b1.tipoBebida = :tipoBebida and e1.tipoEstoque = 0) >0) "),
	@NamedQuery(name=SecaoEntity.VERIFICAR_TOTAL_ENTRADA_POR_TIPO_BEBIDA,
	query="Select SUM(e.volume)  From SecaoEntity s LEFT Join s.estoque e"
			+ " LEFT JOIN e.bebida b WHERE  s.id = :idSecao and (s.estoque IS EMPTY OR "
			+ "(b.tipoBebida = :tipoBebida and e.tipoEstoque = 1))"),
	@NamedQuery(name=SecaoEntity.VERIFICAR_TOTAL_SAIDA_POR_TIPO_BEBIDA,
	query="Select SUM(e.volume)  From SecaoEntity s LEFT Join s.estoque e"
			+ " LEFT JOIN e.bebida b WHERE  s.id = :idSecao  and (s.estoque IS EMPTY OR "
			+ "(b.tipoBebida = :tipoBebida and e.tipoEstoque = 0))")
})
public class SecaoEntity {
	
	public static final String RECUPERAR_SECOES_COM_ESPACO_DISPONIVEL_POR_TIPO_BEBIDA = "RecuperarSecaoComEspacoDisponivelPorTipoBebida";
	public static final String VERIFICAR_TOTAL_ENTRADA_POR_TIPO_BEBIDA = "verificarTotalEntradaPorTipoBebida";
	public static final String VERIFICAR_TOTAL_SAIDA_POR_TIPO_BEBIDA = "verificarTotalSaidaPorTipoBebida";
	public transient static final Long VOLUME_MAXIMO_SECAO = 200L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String nome;
	
	@Transient
	private Long volumeOcupado;
	@Transient
	private TipoBebida tipoBebida;
	
	@OneToMany(mappedBy="secao")
	private List<EstoqueEntity> estoque;
	
	public SecaoEntity() {
	}
	public SecaoEntity(Secao secao) {
		this.id = secao.getId();
	}
	public SecaoEntity(Long id,String nome, Long volumeOcupado) {
		this.id = id;
		this.nome = nome;
		this.volumeOcupado = volumeOcupado;
	}
	public SecaoEntity(String nome ) {
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	 
	public Long getVolumeOcupado() {
		return volumeOcupado;
	}
	public void setVolumeOcupado(Long volumeOcupado) {
		this.volumeOcupado = volumeOcupado;
	}
	public List<EstoqueEntity> getEstoque() {
		return estoque;
	}
	public void setEstoque(List<EstoqueEntity> estoque) {
		this.estoque = estoque;
	}
	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}
	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}
	 
	  

 
	

}
