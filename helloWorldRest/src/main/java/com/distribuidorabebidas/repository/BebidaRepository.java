package com.distribuidorabebidas.repository;
 
 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.distribuidorabebidas.repository.entity.BebidaEntity;
import com.distribuidorabebidas.repository.entity.PessoaEntity;
 
 
 
public class BebidaRepository {
 
	private final EntityManagerFactory entityManagerFactory;
 
	private final EntityManager entityManager;
 
	public BebidaRepository(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("testeRestPU");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
 
	/**
	 * CRIA UM NOVO REGISTRO NO BANCO DE DADOS
	 * */
	public PessoaEntity Salvar(PessoaEntity pessoaEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(pessoaEntity);
		this.entityManager.getTransaction().commit();
		return pessoaEntity;
	}
	public BebidaEntity recuperarBebidaById(Long idBebida) {
		return this.entityManager.find(BebidaEntity.class, idBebida);
	}
 
	/**
	 * ALTERA UM REGISTRO CADASTRADO
	 * */
	public void Alterar(PessoaEntity pessoaEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(pessoaEntity);
		this.entityManager.getTransaction().commit();
	}
 
	/**
	 * RETORNA TODAS AS PESSOAS CADASTRADAS NO BANCO DE DADOS 
	 * */
	@SuppressWarnings("unchecked")
	public List<PessoaEntity> TodasPessoas(){
 
		return this.entityManager.createQuery("SELECT p FROM PessoaEntity p ORDER BY p.nome").getResultList();
	}
 
	/**
	 * CONSULTA UMA PESSOA CADASTRA PELO CÓDIGO
	 * */
	public List<BebidaEntity> GetBebidaPorSetor(Long idSetor){
 
		TypedQuery<BebidaEntity> query = this.entityManager.createNamedQuery(BebidaEntity.BEBIDAS_BY_SETOR,BebidaEntity.class);
		query.setParameter("idSecao", idSetor);
		return query.getResultList();
	}
	public Boolean salvarBebidas(List<BebidaEntity> bebidas) {
		try {
			this.entityManager.getTransaction().begin();
			for (BebidaEntity bebida : bebidas) {
				entityManager.persist(bebida);
			}
			this.entityManager.getTransaction().commit();
		}catch(PersistenceException e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
 
	/**
	 * EXCLUINDO UM REGISTRO PELO CÓDIGO
	**/
//	public void Excluir(Integer codigo){
// 
//		PessoaEntity pessoa = this.GetPessoa(codigo);
// 
//		this.entityManager.getTransaction().begin();
//		this.entityManager.remove(pessoa);
//		this.entityManager.getTransaction().commit();
// 
//	}
}