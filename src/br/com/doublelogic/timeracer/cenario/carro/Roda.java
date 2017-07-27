package br.com.doublelogic.timeracer.cenario.carro;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.material.Material;

import br.com.doublelogic.timeracer.cenario.UtilModels;

public class Roda extends Node {

	private static final Vector3f EIXO_TRACAO = new Vector3f(0, 0, 1);
	private static final Vector3f EIXO_DIRECAO = new Vector3f(0, 1, 0);
	private static final float MASSA_RODA = 3f;
	private static final float ESCALA_PNEU = 2.5f;
	private static final float ESCALA_MODELO = 0.5f;
	private static final String CAMINHO_MODELO_RODA = "wheel.jme";
	private static final long serialVersionUID = 1L;
	private static final float VELOCIDADE_TRACAO = 75;
	private static final float VELOCIDADE_GIRO = 75;
	private static final float GIRO_MAXIMO = 0.4f;
	private static final float ACELERACAO_TRACAO = 800;
	private static final float ACELERACAO_GIRO = 1000;

	private DynamicPhysicsNode pneu;
	private RotationalJointAxis eixoTracao, eixoDirecao;

	public Roda(final DynamicPhysicsNode baseSuspensao, final Vector3f posicaoRelativa, final boolean comDirecao) {
		super("NoRoda");
		pneu = baseSuspensao.getSpace().createDynamicNode();
		pneu.setName("roda");
		pneu.setLocalTranslation(baseSuspensao.getLocalTranslation().add(posicaoRelativa));
		pneu.createSphere("pneu");
		pneu.setLocalScale(ESCALA_PNEU);
		pneu.generatePhysicsGeometry();
		Node modelo3D = UtilModels.carregaModeloJME(CAMINHO_MODELO_RODA);
		modelo3D.setLocalScale(ESCALA_MODELO);
		modelo3D.setName("modeloRoda");
		pneu.attachChild(modelo3D);
		pneu.setMaterial(Material.RUBBER);
		pneu.setMass(MASSA_RODA);

		Joint caixaRoda = baseSuspensao.getSpace().createJoint();
		caixaRoda.attach(baseSuspensao, pneu);
		caixaRoda.setAnchor(pneu.getLocalTranslation().subtract(baseSuspensao.getLocalTranslation()));
		//caixaRoda.setSpring(Float.NaN, 0);

		if (comDirecao) {
			eixoDirecao = caixaRoda.createRotationalAxis();
			eixoDirecao.setDirection(EIXO_DIRECAO);
			eixoDirecao.setAvailableAcceleration(ACELERACAO_GIRO);
		}
		eixoTracao = caixaRoda.createRotationalAxis();
		eixoTracao.setDirection(EIXO_TRACAO);
		if (comDirecao)
			eixoTracao.setRelativeToSecondObject(true);
		if (comDirecao)
			soltarVolante();
		this.attachChild(pneu);
	}

	public void acelerar(final int direcao) {
		eixoTracao.setAvailableAcceleration(ACELERACAO_TRACAO);
		eixoTracao.setDesiredVelocity(direcao*VELOCIDADE_TRACAO);
	}

	public void soltarAcelerador() {
		eixoTracao.setAvailableAcceleration(400);
		eixoTracao.setDesiredVelocity(0);
	}

	public void girarVolante(final int direcao) {
		eixoDirecao.setDesiredVelocity(direcao*VELOCIDADE_GIRO);
		eixoDirecao.setPositionMaximum(GIRO_MAXIMO);
		eixoDirecao.setPositionMinimum(-GIRO_MAXIMO);
	}

	public void soltarVolante() {
		eixoDirecao.setDesiredVelocity(0);
		eixoDirecao.setPositionMaximum(0);
		eixoDirecao.setPositionMinimum(0);
	}

}
