package mock;

import java.util.ArrayList;
import java.util.List;

import com.tutorialacademy.repository.entity.BebidaAlcoolicaEntity;
import com.tutorialacademy.repository.entity.BebidaEntity;
import com.tutorialacademy.repository.entity.BebidaNaoAlcoolicaEntity;
import com.tutorialacademy.repository.entity.SecaoEntity;

public class GeracaoDadosMock {
	
	public  static List<BebidaEntity> bebidas = new ArrayList<BebidaEntity>();
	public  static List<SecaoEntity> secoes = new ArrayList<SecaoEntity>();
	static{
		 BebidaEntity bebida = new BebidaAlcoolicaEntity();
		 bebida.setNome("Brahma");
		 
		 BebidaEntity bebida2 = new BebidaAlcoolicaEntity();
		 bebida2.setNome("SKOL");
		 
		 BebidaEntity bebida3 = new BebidaAlcoolicaEntity();
		 bebida3.setNome("Antartica");
		 
		 BebidaEntity bebida4 = new BebidaAlcoolicaEntity();
		 bebida4.setNome("Heineken");
		
		 
		 BebidaEntity bebida5 = new BebidaNaoAlcoolicaEntity();
		 bebida5.setNome("Coca Cola");
		 
		 BebidaEntity bebida6 = new BebidaNaoAlcoolicaEntity();
		 bebida6.setNome("Suco+");
		 
		 BebidaEntity bebida7 = new BebidaNaoAlcoolicaEntity();
		 bebida7.setNome("Fanta");
		 bebidas.add(bebida);
		 bebidas.add(bebida2);
		 bebidas.add(bebida3);
		 bebidas.add(bebida4);
		 bebidas.add(bebida5);
		 bebidas.add(bebida6);
		 bebidas.add(bebida7);
		 
		 SecaoEntity s1 = new SecaoEntity("Primeria Secao");
		 SecaoEntity s2 = new SecaoEntity("Segunda Secao");
		 SecaoEntity s3 = new SecaoEntity("Terceira Secao");
		 SecaoEntity s4 = new SecaoEntity("Quarta Secao");
		 SecaoEntity s5 = new SecaoEntity("Quinta Secao");
		 secoes.add(s1);
		 secoes.add(s2);
		 secoes.add(s3);
		 secoes.add(s4);
		 secoes.add(s5);
	}

}
