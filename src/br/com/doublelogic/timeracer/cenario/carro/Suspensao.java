package br.com.doublelogic.timeracer.cenario.carro;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.material.Material;

public class Suspensao extends Node {

	private static final Vector3f POSICAO_RELATIVA_RODA = new Vector3f(0,0,2.3f);
	private static final Vector3f EIXO_AMORTECEDOR = new Vector3f(0,1,0);
	private static final float VELOCIDADE_AMORTECEDOR = -15;
	private static final float ACELERACAO_AMORTECEDOR = 750;
	private static final float DESLOCAMENTO_MAXIMO_MOLA = 0.5f;
	private static final float MASSA_BASE = 7.5f;
	private static final Vector3f POSICAO_RELATIVA_BASE = new Vector3f(0,0,4.5f);
	private static final long serialVersionUID = 1L;
	private Roda rodaEsquerda, rodaDireita;
	private DynamicPhysicsNode baseEsquerda, baseDireita;
	
	public Suspensao(final PhysicsSpace pSpace, final DynamicPhysicsNode chassis, final Vector3f position, final boolean volanteDisponivel) {
		super("suspensao");
		
		// criando as duas bases de apoio da suspensão
		baseEsquerda = criaBase(pSpace, chassis, position.add(POSICAO_RELATIVA_BASE));
		baseDireita = criaBase(pSpace, chassis, position.subtract(POSICAO_RELATIVA_BASE));
		
		// criando as duas rodas, uma apoiada em cada base
		rodaEsquerda = new Roda(baseEsquerda, POSICAO_RELATIVA_RODA, volanteDisponivel);
		this.attachChild(rodaEsquerda);
		rodaDireita = new Roda(baseDireita, POSICAO_RELATIVA_RODA.negate(), volanteDisponivel);
		this.attachChild(rodaDireita);
	}
	
	private DynamicPhysicsNode criaBase(final PhysicsSpace pSpace, final DynamicPhysicsNode chassis, final Vector3f posicaoRelativa) {
		// criando um objeto dinâmico para representar a base da suspensão.
		DynamicPhysicsNode baseSuspensao = pSpace.createDynamicNode();
		baseSuspensao.setName("baseSuspensao");
		baseSuspensao.setLocalTranslation(chassis.getLocalTranslation().add(posicaoRelativa));
		baseSuspensao.createBox("baseBox");
		baseSuspensao.setLocalScale(0.1f);
		baseSuspensao.setMass(MASSA_BASE);
		baseSuspensao.setMaterial(Material.GHOST);
		this.attachChild(baseSuspensao);
		
		// criando uma união (junta) entre a BASE da suspensão e o CHASSI
		Joint juntaSuspensao = pSpace.createJoint();
		juntaSuspensao.attach(baseSuspensao, chassis);
		//juntaSuspensao.setSpring(Float.NaN, 0);
		
		// criando um eixo de deslocamento nessa junta para representar o amortecedor
		TranslationalJointAxis amortecedor = juntaSuspensao.createTranslationalAxis();
		amortecedor.setPositionMaximum(DESLOCAMENTO_MAXIMO_MOLA);
		amortecedor.setPositionMinimum(-DESLOCAMENTO_MAXIMO_MOLA);
		amortecedor.setAvailableAcceleration(ACELERACAO_AMORTECEDOR);
		amortecedor.setDesiredVelocity(VELOCIDADE_AMORTECEDOR);
		amortecedor.setDirection(EIXO_AMORTECEDOR);
		
		return baseSuspensao;
	}

	public void acelerar(final int direcao) {
		rodaEsquerda.acelerar(direcao);
		rodaDireita.acelerar(direcao);
	}

	public void soltarAcelerador() {
		rodaEsquerda.soltarAcelerador();
		rodaDireita.soltarAcelerador();
	}

	public void girarVolante(final int direcao) {
		rodaEsquerda.girarVolante(direcao);
		rodaDireita.girarVolante(direcao);
	}

	public void soltarVolante() {
		rodaEsquerda.soltarVolante();
		rodaDireita.soltarVolante();
	}
	
}