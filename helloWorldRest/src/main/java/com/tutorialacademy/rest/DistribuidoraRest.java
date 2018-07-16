package com.tutorialacademy.rest;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tutorialacademy.http.Estoque;
import com.tutorialacademy.http.Secao;
import com.tutorialacademy.repository.BebidaRepository;
import com.tutorialacademy.repository.EstoqueRepositoty;
import com.tutorialacademy.repository.SecaoRepositoty;
import com.tutorialacademy.repository.entity.BebidaAlcoolicaEntity;
import com.tutorialacademy.repository.entity.BebidaEntity;
import com.tutorialacademy.repository.entity.BebidaNaoAlcoolicaEntity;
import com.tutorialacademy.repository.entity.EstoqueEntity;
import com.tutorialacademy.repository.entity.PessoaEntity;
import com.tutorialacademy.repository.entity.SecaoEntity;
import com.tutorialacademy.repository.entity.enumerator.TipoBebida;

import mock.GeracaoDadosMock;
 
@Path("/") 
public class DistribuidoraRest {
	public DistribuidoraRest() {
		secaoRepositoty.salvarSecoes(GeracaoDadosMock.secoes);
		 bebidaRepository.salvarBebidas(GeracaoDadosMock.bebidas);
	}
	private final BebidaRepository bebidaRepository = new BebidaRepository();
	private final EstoqueRepositoty estoqueRepository = new EstoqueRepositoty();
	private final SecaoRepositoty secaoRepositoty = new SecaoRepositoty();
 
	/**
	 * Esse método lista todas pessoas cadastradas na base
	 * */
	@GET
	@Produces("text/html")
	@Path("/setores/{idSetor}/bebidas")
	public String recuperarBebidasPorSetor(@PathParam("idSetor") Long idSetor){
 
 
		List<BebidaEntity> bebidasEntity = bebidaRepository.GetBebidaPorSetor(idSetor);
		String output = "";
		if(bebidasEntity.size() > 0) {
			for (BebidaEntity entity : bebidasEntity) {
				output += "<p>ID: " + entity.getId().intValue() +" Tipo: "+ entity.getTipoBebida().getDescricao() +" Nome: "+entity.getNome()+ "</p<br>";
						
			}
		}else {
			output = "<p> Nenhum registro encontrado </p>";
		}
	
 
		return output;
	}
	private Boolean verificarSecaoDisponivelPorTipoBebida(Estoque estoque, TipoBebida tipoBebida) {
		Long somaVolumeSecao =  secaoRepositoty.recuperarSomaFluxoSecaoPorTipoBebida(estoque, tipoBebida);
		return somaVolumeSecao + estoque.getVolume() <= SecaoEntity.VOLUME_MAXIMO_SECAO;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/estoques")
	public Response cadastrarRegistroEstoque(Estoque estoque) {
		BebidaEntity bebida = bebidaRepository.recuperarBebidaById(estoque.getBebida());
		if(verificarSecaoDisponivelPorTipoBebida(estoque,bebida.getTipoBebida())) {
	 		EstoqueEntity estoqueEntity = new EstoqueEntity();
			estoqueEntity.setData(estoque.getData());
			estoqueEntity.setTipoEstoque(estoque.getTipoEstoque());
			estoqueEntity.setResponsavel(new PessoaEntity(estoque.getResponsavel().intValue()));
			if(bebida instanceof BebidaAlcoolicaEntity) {
				estoqueEntity.setBebida(new BebidaAlcoolicaEntity(estoque.getBebida()));
			}else {
				estoqueEntity.setBebida(new BebidaNaoAlcoolicaEntity(estoque.getBebida()));
			}
			
			estoqueEntity.setVolume(estoque.getVolume());
			estoqueEntity.setSecao(new SecaoEntity(estoque.getSecao()));
			estoqueRepository.Salvar(estoqueEntity);
		} else {
			return Response.status(Response.Status.CONFLICT)
    				.entity(estoque)
    				.type(MediaType.APPLICATION_JSON).
    				build();
		}
		return Response.status(Response.Status.CREATED).entity(estoque).type(MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/bebidas/{tipoBebida}/estoques")
	public Object[] recuperarVolumeTotalPorTipoBebida(@PathParam("tipoBebida") Long tipoBebida){
		return estoqueRepository.volumeTotalPorTipoBebida(tipoBebida);
	}
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("bebidas/{tipoBebida}/secoes")
	public List<Secao> recuperarSecoesComVagasPorTipoBebida(@PathParam("tipoBebida") Long tipoBebida){
		List<Secao> secoes = new ArrayList<Secao>();
		
		List<SecaoEntity> secoesEntity = secaoRepositoty.recuperarSecoesComVagasPorTipoBebida(tipoBebida);
		for (SecaoEntity s : secoesEntity) {
			Secao secao = new Secao(s);
			secoes.add(secao);
			
		}
		return secoes;
	}
	
	
}