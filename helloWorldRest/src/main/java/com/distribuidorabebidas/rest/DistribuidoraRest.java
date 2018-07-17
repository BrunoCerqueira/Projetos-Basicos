package com.distribuidorabebidas.rest;


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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.distribuidorabebidas.http.Estoque;
import com.distribuidorabebidas.http.Secao;
import com.distribuidorabebidas.mock.GeracaoDadosMock;
import com.distribuidorabebidas.repository.BebidaRepository;
import com.distribuidorabebidas.repository.EstoqueRepositoty;
import com.distribuidorabebidas.repository.SecaoRepositoty;
import com.distribuidorabebidas.repository.entity.BebidaAlcoolicaEntity;
import com.distribuidorabebidas.repository.entity.BebidaEntity;
import com.distribuidorabebidas.repository.entity.BebidaNaoAlcoolicaEntity;
import com.distribuidorabebidas.repository.entity.EstoqueEntity;
import com.distribuidorabebidas.repository.entity.PessoaEntity;
import com.distribuidorabebidas.repository.entity.SecaoEntity;
import com.distribuidorabebidas.repository.entity.enumerator.TipoBebida;

import jersey.repackaged.com.google.common.collect.Lists;
 
@Path("/") 
public class DistribuidoraRest {
	public DistribuidoraRest() {
//		secaoRepositoty.salvarSecoes(GeracaoDadosMock.secoes);
//		 bebidaRepository.salvarBebidas(GeracaoDadosMock.bebidas);
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
	public List<Secao> retornarSecoesDisponivelPorTipoBebida(TipoBebida tipoBebida){
		return null;
		
	}
	private void salvarEstoqueEntityFromEstoque(Estoque  estoque,BebidaEntity bebida){
		
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
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/estoques")
	public Response recuperarRegistroEstoque(){
		List<EstoqueEntity> registrosEstoque = estoqueRepository.recuperarTodoEstoque();
		 List<Estoque> estoques = new ArrayList<Estoque>();
		for (EstoqueEntity estoqueEntity : registrosEstoque) {
			Estoque estoque = new Estoque(estoqueEntity);
			estoques.add(estoque);
		}
		return Response.ok(new GenericEntity<List<Estoque>>(estoques) {}).type(MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/estoques")
	public Response retirarRegistroEstoque(Estoque estoque) {
		
		BebidaEntity bebida = bebidaRepository.recuperarBebidaById(estoque.getBebida());
		if(estoque.getTipoEstoque().equals(EstoqueEntity.TipoAcao.INSERCAO)){
			
			if(verificarSecaoDisponivelPorTipoBebida(estoque,bebida.getTipoBebida())) {
				salvarEstoqueEntityFromEstoque(estoque,bebida);
			} else {
				return Response.status(Response.Status.CONFLICT)
	    				.entity(estoque)
	    				.type(MediaType.APPLICATION_JSON).
	    				build();
			}
		}else{
			if(verificarEstoqueSuficienteParaAtenderRetirada(estoque, bebida.getTipoBebida())){
				salvarEstoqueEntityFromEstoque(estoque,bebida);
				
			}else{
				return Response.status(Response.Status.CONFLICT)
	    				.entity(estoque)
	    				.type(MediaType.APPLICATION_JSON).
	    				build();
			}
			
		}
		return Response.status(Response.Status.CREATED).entity(estoque).type(MediaType.APPLICATION_JSON).build();
	}
	/***
	 * Verifica se o volume de entrada é maior ou igual o solicitado para retirada
	 * @return
	 */
	private Boolean verificarEstoqueSuficienteParaAtenderRetirada(Estoque estoque, TipoBebida tipoBebida ){
		Long somaVolumeSecao =  secaoRepositoty.recuperarSomaFluxoSecaoPorTipoBebida(estoque, tipoBebida);
		return somaVolumeSecao - estoque.getVolume() >=0;
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