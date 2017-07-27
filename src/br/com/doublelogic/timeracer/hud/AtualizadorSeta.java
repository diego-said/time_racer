package br.com.doublelogic.timeracer.hud;

import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;

/**
 * Atualizador sincronizado com a simula��o f�sica para a seta de indica��o do caminho.
 *
 */
public class AtualizadorSeta implements PhysicsUpdateCallback {
	/**
	 * Seta que ser� atualizada.
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
	 * A atualiza��o da seta ser� executada ap�s a atualiza��o da simula��o. 
	 */
	public void afterStep(PhysicsSpace pSpace, float tpf) {
		seta.atualizar();
	}

	/**
	 * Nada ser� executado antes da atualiza��o da simula��o.
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
	}

}
