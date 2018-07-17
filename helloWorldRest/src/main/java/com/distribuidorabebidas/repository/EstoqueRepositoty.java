package com.distribuidorabebidas.repository;
 
 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.distribuidorabebidas.repository.entity.BebidaEntity;
import com.distribuidorabebidas.repository.entity.EstoqueEntity;
import com.distribuidorabebidas.repository.entity.PessoaEntity;
import com.distribuidorabebidas.repository.entity.SecaoEntity;
import com.distribuidorabebidas.repository.entity.enumerator.TipoBebida;
 
 
 
public class EstoqueRepositoty {
 
	private final EntityManagerFactory entityManagerFactory;
 
	private final EntityManager entityManager;
 
	public EstoqueRepositoty(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("testeRestPU");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
 
	/**
	 * CRIA UM NOVO REGISTRO NO BANCO DE DADOS
	 * */
	public EstoqueEntity Salvar(EstoqueEntity estoqueEntity){
 
		this.entityManager.getTransaction().begin();
		estoqueEntity.setBebida(this.entityManager.find(BebidaEntity.class, estoqueEntity.getBebida().getId()));
		SecaoEntity secao = this.entityManager.find(SecaoEntity.class, estoqueEntity.getSecao().getId());
		estoqueEntity.setResponsavel(this.entityManager.find(PessoaEntity.class, estoqueEntity.getResponsavel().getCodigo()));
		this.entityManager.persist(estoqueEntity);
		this.entityManager.getTransaction().commit();
		return estoqueEntity;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] volumeTotalPorTipoBebida(Long tipoBebida) {
		Enum<TipoBebida> tipoBebidaEnum = TipoBebida.values()[tipoBebida.intValue()];
		this.entityManager.getTransaction().begin();
		Query query = this.entityManager.createNamedQuery(EstoqueEntity.RECUPERAR_VOLUME_ESTOQUE_POR_TIPO_BEBIDA);
		query.setParameter("tipoBebida",tipoBebidaEnum);
		return (Object[]) query.getSingleResult();
	}
 
	/**
	 * ALTERA UM REGISTRO CADASTRADO
	 * */
	public void Alterar(EstoqueEntity estoqueEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(estoqueEntity);
		this.entityManager.getTransaction().commit();
	}
 
	/**
	 * RETORNA TODAS AS PESSOAS CADASTRADAS NO BANCO DE DADOS 
	 * */
	public List<EstoqueEntity> recuperarTodoEstoque(){
 
		TypedQuery<EstoqueEntity> query = this.entityManager.createNamedQuery(EstoqueEntity.RECUPERAR_HISTORICO_GERAL,EstoqueEntity.class);
		List<EstoqueEntity> estoque = query.getResultList();
		return estoque;
	}
 
	/**
	 * CONSULTA UMA PESSOA CADASTRA PELO CÓDIGO
	 * */
	public PessoaEntity GetPessoa(Integer codigo){
 
		return this.entityManager.find(PessoaEntity.class, codigo);
	}
 
	/**
	 * EXCLUINDO UM REGISTRO PELO CÓDIGO
	**/
	public void Excluir(Integer codigo){
 
		PessoaEntity pessoa = this.GetPessoa(codigo);
 
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(pessoa);
		this.entityManager.getTransaction().commit();
 
	}
}