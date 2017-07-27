package br.com.doublelogic.timeracer.camera;

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
public class AtualizadorCamera implements PhysicsUpdateCallback {
	/**
	 * Câmera que será atualizada.
	 */
	private ChaseCameraCustomizada camera;
	/**
	 * Construtor recebe a câmera para ser atualizada.
	 * @param camera
	 */
	public AtualizadorCamera(ChaseCameraCustomizada camera) {
		this.camera = camera;
	}
	
	/**
	 * A atualização da câmera será executada após a atualização da simulação física. 
	 */
	public void afterStep(PhysicsSpace pSpace, float tpf) {
		camera.atualizacaoVinculadaFisica(tpf);
	}

	/**
	 * Nada será executado antes da atualização.
	 */
	public void beforeStep(PhysicsSpace pSpace, float tpf) {
	}

}
