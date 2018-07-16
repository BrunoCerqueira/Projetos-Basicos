package com.tutorialacademy.repository;
 
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.tutorialacademy.http.Estoque;
import com.tutorialacademy.repository.entity.SecaoEntity;
import com.tutorialacademy.repository.entity.enumerator.TipoBebida;
 
 
 
public class SecaoRepositoty {
 
	private final EntityManagerFactory entityManagerFactory;
 
	private final EntityManager entityManager;
 
	public SecaoRepositoty(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("testeRestPU");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
 
	
	public List<SecaoEntity> recuperarSecoesComVagasPorTipoBebida(Long tipoBebida){
		TipoBebida  tipoBebidaEnum = TipoBebida.values()[tipoBebida.intValue()];
		Query  query = this.entityManager.createNamedQuery(SecaoEntity.RECUPERAR_SECOES_COM_ESPACO_DISPONIVEL_POR_TIPO_BEBIDA);
		query.setParameter("tipoBebida", tipoBebidaEnum);
//		query.setParameter("volumeMaximo", SecaoEntity.VOLUME_MAXIMO_SECAO);
		
		List<SecaoEntity> secoes = new ArrayList<SecaoEntity>();
		List<Object[]> retorno = query.getResultList();
		for (Object[] objects : retorno) {
			SecaoEntity secao = new SecaoEntity();
			secao.setId((Long) objects[0]);
			secao.setNome((String) objects[1]);
			secao.setVolumeOcupado((Long) objects[2]);
			secao.setTipoBebida(tipoBebidaEnum);
			secoes.add(secao);
			
		}
		return secoes;
	}
	
	public Long recuperarSomaFluxoSecaoPorTipoBebida(Estoque estoque, TipoBebida tipoBebida){
		Query  queryEntrada = this.entityManager.createNamedQuery(SecaoEntity.VERIFICAR_TOTAL_ENTRADA_POR_TIPO_BEBIDA);
		queryEntrada.setParameter("tipoBebida", tipoBebida);
		queryEntrada.setParameter("idSecao", estoque.getSecao().getId());
		Long somaVolumeSecaoEntrada = (Long) queryEntrada.getSingleResult();
		
		
		Query  querySaida = this.entityManager.createNamedQuery(SecaoEntity.VERIFICAR_TOTAL_SAIDA_POR_TIPO_BEBIDA);
		querySaida.setParameter("tipoBebida", tipoBebida);
		querySaida.setParameter("idSecao", estoque.getSecao().getId());
		Long somaVolumeSecaoSaida = (Long) querySaida.getSingleResult();
		
		
		somaVolumeSecaoEntrada = somaVolumeSecaoEntrada == null ? 0L : somaVolumeSecaoEntrada;
		somaVolumeSecaoSaida = somaVolumeSecaoSaida == null ? 0L : somaVolumeSecaoSaida;
		return somaVolumeSecaoEntrada - somaVolumeSecaoSaida;
	}
	public Boolean salvarSecoes(List<SecaoEntity> secoes) {
		
		try {
			this.entityManager.getTransaction().begin();
			for (SecaoEntity secaoEntity : secoes) {
				entityManager.persist(secaoEntity);
			}
			this.entityManager.getTransaction().commit();;
		}catch(PersistenceException e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
 
}