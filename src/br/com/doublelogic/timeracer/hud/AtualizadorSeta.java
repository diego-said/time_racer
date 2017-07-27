package br.com.doublelogic.timeracer.hud;

import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;

/**
 * Atualizador sincronizado com a simulação física para a seta de indicação do caminho.
 *
 */
public class AtualizadorSeta implements PhysicsUpdateCallback {
	/**
	 * Seta que será atualizada.
	 */
	private Seta seta;
	
	/**
	 * Construtor recebe a seta para ser atualizada.
	 * @param seta
	 */
	public AtualizadorSeta(Seta seta) {
		this.seta = seta;
	}
	
	/**
	 * A atualização da seta será executada após a atualização da simulação. 
	 */
	public void afterStep(PhysicsSpace pSpace, float tpf) {
		seta.atualizar();
	}

	/**
	 * Nada será executado antes da atualização da simulação.
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
	}

}
