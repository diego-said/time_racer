package br.com.doublelogic.timeracer.corrida;

import java.util.ArrayList;
import java.util.List;

import com.jme.intersection.BoundingCollisionResults;
import com.jme.math.Vector3f;
import com.jme.scene.Node;

import br.com.doublelogic.timeracer.cenario.carro.Carro;
import br.com.doublelogic.timeracer.cenario.terreno.Terreno;

public class Pista extends Node {

	// necessário para posicionar os postes corretamente sobre o terreno
	private Terreno terreno;
	private List<Marco> marcos;
	private Marco marcoAtual;
	private BoundingCollisionResults resultadosColisoes;
	
	public Pista(Terreno terreno) {
		super();
		this.terreno = terreno;
		this.marcos = new ArrayList<Marco>();
		this.resultadosColisoes = new BoundingCollisionResults();
		criaMarcos();		
	}

	private void criaMarcos() {
		Marco novoMarco;
		novoMarco = criaMarco(new Vector3f(1000,0,0), null);
		novoMarco = criaMarco(new Vector3f(10000,0,2000),novoMarco);
		novoMarco = criaMarco(new Vector3f(12000,0,15000),novoMarco);
		novoMarco = criaMarco(new Vector3f(5000,0,5000),novoMarco);
	}

	private Marco criaMarco(final Vector3f posicao, final Marco anterior) {
		posicao.y = terreno.getBlocoTerreno().getHeight(posicao) - 50;
		Marco marco = new Marco(posicao);
		marcos.add(marco);
		if (marcoAtual == null){
			marco.setMarcoLiberado(true);
			marcoAtual = marco;
		}
		if (anterior != null)
			anterior.setProximo(marco);
		this.attachChild(marco);
		return marco;
	}
	
	public void checaMarcos(Carro carro){
		for (Marco marco : marcos) {
			if (marco.testaColisao(carro, resultadosColisoes))
				marcoAtual = marco.getProximo();
		}
	}

	public Marco getMarcoAtual() {
		return marcoAtual;
	}
	
}