package br.com.doublelogic.timeracer.hud;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Cone;

import br.com.doublelogic.timeracer.cenario.UtilRenderSates;
import br.com.doublelogic.timeracer.cenario.carro.Carro;
import br.com.doublelogic.timeracer.corrida.Pista;

/**
 * Seta indicativa do caminho a seguir pelo terreno.
 * Implementa��o simples baseada em um cone, incluindo transpar�ncia Alpha e
 * atualiza��o da posi��o indicada (rota��o do cone) a partir da posi��o do
 * pr�ximo marco da pista.
 *
 */
public class Seta extends Cone{

	private static final long serialVersionUID = 1L;
	private static ColorRGBA azulClaroTransparente = new ColorRGBA(0.5f,0.5f,1,0.5f);
	private Pista pista;
	private Carro carro;
	
	/**
	 * Construtor da seta direcional, posicionada sobre o carro e apontando para o pr�ximo marco da pista.
	 * @param pista usada para se obter o marco atual
	 * @param carro para posicionar a seta
	 */
	public Seta(Pista pista, Carro carro) {
		super("cone", 5, 10, 2.5f, 7.5f); 
		this.pista = pista;
		this.carro = carro;
		setDefaultColor(azulClaroTransparente);
		setRenderState(UtilRenderSates.createAlphaEffect());
	}

	/**
	 * Usado para atualizar tanto a posi��o quanto a rota��o.
	 */
	public void atualizar(){
		// Posicionando a seta 10 unidades acima da posi��o atual do carro.
		setLocalTranslation(carro.getChassi().getWorldTranslation().add(0, 10, 0));
		// Obtentendo a dire��o relativa entre a seta e o marco atual da pista.
		// TODO checar null pointer aqui
		Vector3f direcaoRelativaAlvo = getWorldTranslation().subtract(pista.getMarcoAtual().getWorldTranslation());
		// Fazendo a rota��o da seta apontar para o marco, girando sobre o eixo cartesiano Y.
		getLocalRotation().lookAt(direcaoRelativaAlvo, Vector3f.UNIT_Y);
	}
}