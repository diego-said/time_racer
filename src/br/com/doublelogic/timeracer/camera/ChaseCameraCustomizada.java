package br.com.doublelogic.timeracer.camera;

import java.util.HashMap;

import com.jme.input.ChaseCamera;
import com.jme.input.thirdperson.ThirdPersonMouseLook;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Spatial;

/**
 * Versão personalizada de ChaseCamera.
 * Sobrescreve o método update(float tpf) padrão para evitar que a atualização da cena OpenGL
 * também atualize a posição da câmera (veja a classe AtualizadorCamera).
 * @author Diego
 *
 */
public class ChaseCameraCustomizada extends ChaseCamera {

	/**
	 * Factory method para ChaseCameraCustomizada.
	 * Define um conjunto de propriedades iniciais de restrição antes de instanciar a ChaseCamera. 
	 * @param camera para ser atualizada.
	 * @param alvoCamera alvo que a câmera deve seguir.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ChaseCamera getInstance(Camera camera, Spatial alvoCamera){
		Vector3f targetOffset = new Vector3f();
        targetOffset.y = 3;
        HashMap propriedades = new HashMap();
        propriedades.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "70");
        propriedades.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "10");
        propriedades.put(ChaseCamera.PROP_TARGETOFFSET, targetOffset);
        propriedades.put(ThirdPersonMouseLook.PROP_MAXASCENT, ""+25 * FastMath.DEG_TO_RAD);
        propriedades.put(ThirdPersonMouseLook.PROP_MINASCENT, ""+10 * FastMath.DEG_TO_RAD);
        propriedades.put(ChaseCamera.PROP_INITIALSPHERECOORDS, new Vector3f(15, 0, 30 * FastMath.DEG_TO_RAD));
        propriedades.put(ChaseCamera.PROP_TARGETOFFSET, targetOffset);
        ChaseCameraCustomizada input = new ChaseCameraCustomizada(camera, alvoCamera, propriedades);
        input.setMaxDistance(70);
		input.setMinDistance(10);
		input.setStayBehindTarget(true);
        return input;
	}
	/**
	 * Evita que esse construtor seja usado diretamente
	 * @param camera
	 * @param alvoCamera
	 * @param propriedades
	 */
	private ChaseCameraCustomizada(Camera camera, Spatial alvoCamera, HashMap propriedades) {
		super(camera, alvoCamera, propriedades);
	}
	
	/**
	 * Chamado automaticamente a cada atualização OpenGL.
	 * Não iremos atualizar a câmera aqui.
	 */
	@Override
	public void update(float tpf) {
	}
	
	/**
	 * Atualizador alternativo, que deverá ser vinculado às atualizações da simulação física.
	 * @param tpf tempo decorrido desde a última atualização.
	 */
	public void atualizacaoVinculadaFisica(float tpf){
		super.update(tpf);
	}

}
