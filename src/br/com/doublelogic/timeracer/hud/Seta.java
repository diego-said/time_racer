package br.com.doublelogic.timeracer.hud;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Cone;

import br.com.doublelogic.timeracer.cenario.UtilRenderSates;
import br.com.doublelogic.timeracer.cenario.carro.Carro;
import br.com.doublelogic.timeracer.corrida.Pista;

/**
 * Seta indicativa do caminho a seguir pelo terreno.
 * Implementação simples baseada em um cone, incluindo transparência Alpha e
 * atualização da posição indicada (rotação do cone) a partir da posição do
 * próximo marco da pista.
 *
 */
public class Seta extends Cone{

	private static final long serialVersionUID = 1L;
	private static ColorRGBA azulClaroTransparente = new ColorRGBA(0.5f,0.5f,1,0.5f);
	private Pista pista;
	private Carro carro;
	
	/**
	 * Construtor da seta direcional, posicionada sobre o carro e apontando para o próximo marco da pista.
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
	 * Usado para atualizar tanto a posição quanto a rotação.
	 */
	public void atualizar(){
		// Posicionando a seta 10 unidades acima da posição atual do carro.
		setLocalTranslation(carro.getChassi().getWorldTranslation().add(0, 10, 0));
		// Obtentendo a direção relativa entre a seta e o marco atual da pista.
		// TODO checar null pointer aqui
		Vector3f direcaoRelativaAlvo = getWorldTranslation().subtract(pista.getMarcoAtual().getWorldTranslation());
		// Fazendo a rotação da seta apontar para o marco, girando sobre o eixo cartesiano Y.
		getLocalRotation().lookAt(direcaoRelativaAlvo, Vector3f.UNIT_Y);
	}
}