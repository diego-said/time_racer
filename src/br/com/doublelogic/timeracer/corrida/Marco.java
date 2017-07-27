package br.com.doublelogic.timeracer.corrida;

import com.jme.bounding.BoundingSphere;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Sphere;

import br.com.doublelogic.timeracer.cenario.UtilRenderSates;
import br.com.doublelogic.timeracer.cenario.carro.Carro;

public class Marco extends Node{

	private static final ColorRGBA COR_MARCO_VALIDO = new ColorRGBA(0.25f,1,0.25f,0.5f);
	private static final ColorRGBA COR_MARCO_INVALIDO = new ColorRGBA(1,0.25f,0.25f,0.5f);
	private boolean jaChecado = false;
	private boolean marcoLiberado = false;
	private Sphere esfera;
	private Marco proximo;
	
	public Marco(Vector3f posicao) {
		esfera = new Sphere("esfera",20,10,1);
		esfera.setDefaultColor(COR_MARCO_INVALIDO);
		esfera.setLocalScale(100);
		esfera.setRenderState(UtilRenderSates.createAlphaEffect());
		this.attachChild(esfera);
		
		this.setLocalTranslation(posicao);
		this.setModelBound(new BoundingSphere());
		this.updateModelBound();
	}
	
	public boolean testaColisao(Carro carro, BoundingCollisionResults resultadosColisoes){
		resultadosColisoes.clear();
		this.calculateCollisions(carro, resultadosColisoes);
		if (!jaChecado && hasCollision(carro, false)){
			if (marcoLiberado){
				marcoLiberado = false;
				jaChecado = true;
				esfera.setDefaultColor(COR_MARCO_INVALIDO);
				if (proximo != null){
					proximo.setMarcoLiberado(true);
					Cronometro.getCronometro().marcarTrecho();
					return true;
				}
				else{
					Cronometro.getCronometro().finalizar();
					return true;
				}
			}
			else {
				// corrigir mensagem do HUD
			}
		}
		return false;
	}

	public boolean isMarcoLiberado() {
		return marcoLiberado;
	}

	public void setMarcoLiberado(boolean marcoLiberado) {
		this.marcoLiberado = marcoLiberado;
		esfera.setDefaultColor(COR_MARCO_VALIDO);
	}

	public Marco getProximo() {
		return proximo;
	}

	public void setProximo(Marco proximo) {
		this.proximo = proximo;
	}

}
