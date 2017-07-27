package br.com.doublelogic.timeracer.cenario.carro;

import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;

/**
 * Sincronizador de c�mera.
 * Deve ser vinculado � engine de f�sica para sincronizar o posicionamento da c�mera a cada
 * atualiza��o da simula��o. Isso � necess�rio para evitar-se a vibra��o decorrente da falta de 
 * sincronia entre as atualiza��es da engine de f�sica e do loop de renderiza��o OpenGL.
 * @author Diego
 *
 */
public class AtualizadorCarro implements PhysicsUpdateCallback {
	/**
	 * C�mera que ser� atualizada.
	 */
	private Carro carro;
	/**
	 * Construtor recebe a c�mera para ser atualizada.
	 * @param carro
	 */
	public AtualizadorCarro(Carro carro) {
		this.carro = carro;
	}
	
	/**
	 * Nada ser� executado ap�s da atualiza��o.
	 */	
	public void afterStep(PhysicsSpace pSpace, float tpf) {
	}


	/**
	 * A atualiza��o das for�as ser� executada antes da atualiza��o da simula��o f�sica. 
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
		carro.atualizar();
	}

}
