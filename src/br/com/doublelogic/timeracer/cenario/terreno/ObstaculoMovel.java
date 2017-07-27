package br.com.doublelogic.timeracer.cenario.terreno;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.material.Material;

import br.com.doublelogic.timeracer.cenario.UtilModels;

public class ObstaculoMovel extends Node {

	private DynamicPhysicsNode representacaoFisica;
	
	public ObstaculoMovel(PhysicsSpace pSpace, String caminho, Vector3f posicao) {
		super("obstaculoMovel");
		representacaoFisica = pSpace.createDynamicNode();
		representacaoFisica.setLocalTranslation(posicao);
		representacaoFisica.attachChild(UtilModels.carregaModeloJME(caminho));
		representacaoFisica.setMaterial(Material.WOOD);
		representacaoFisica.setMass(50);
		representacaoFisica.setLocalScale(.25f);
		representacaoFisica.generatePhysicsGeometry();
		this.attachChild(representacaoFisica);		
	}
	
}
