package br.com.doublelogic.timeracer.cenario.carro;

import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;

/**
 * Sincronizador de câmera.
 * Deve ser vinculado à engine de física para sincronizar o posicionamento da câmera a cada
 * atualização da simulação. Isso é necessário para evitar-se a vibração decorrente da falta de 
 * sincronia entre as atualizações da engine de física e do loop de renderização OpenGL.
 * @author Diego
 *
 */
public class AtualizadorCarro implements PhysicsUpdateCallback {
	/**
	 * Câmera que será atualizada.
	 */
	private Carro carro;
	/**
	 * Construtor recebe a câmera para ser atualizada.
	 * @param carro
	 */
	public AtualizadorCarro(Carro carro) {
		this.carro = carro;
	}
	
	/**
	 * Nada será executado após da atualização.
	 */	
	public void afterStep(PhysicsSpace pSpace, float tpf) {
	}


	/**
	 * A atualização das forças será executada antes da atualização da simulação física. 
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
		carro.atualizar();
	}

}
