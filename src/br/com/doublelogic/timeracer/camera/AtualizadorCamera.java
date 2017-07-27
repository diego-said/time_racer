package br.com.doublelogic.timeracer.camera;

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
public class AtualizadorCamera implements PhysicsUpdateCallback {
	/**
	 * C�mera que ser� atualizada.
	 */
	private ChaseCameraCustomizada camera;
	/**
	 * Construtor recebe a c�mera para ser atualizada.
	 * @param camera
	 */
	public AtualizadorCamera(ChaseCameraCustomizada camera) {
		this.camera = camera;
	}
	
	/**
	 * A atualiza��o da c�mera ser� executada ap�s a atualiza��o da simula��o f�sica. 
	 */
	public void afterStep(PhysicsSpace pSpace, float tpf) {
		camera.atualizacaoVinculadaFisica(tpf);
	}

	/**
	 * Nada ser� executado antes da atualiza��o.
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
	}

}
