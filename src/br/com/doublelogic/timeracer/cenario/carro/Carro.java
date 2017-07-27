package br.com.doublelogic.timeracer.cenario.carro;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.audio.AudioTrack;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.material.Material;

import br.com.doublelogic.timeracer.cenario.UtilModels;

public class Carro extends Node{

	public static final int FRENTE = 1;
	public static final int TRAS = -1;
	public static final int DIREITA = 1;
	public static final int ESQUERDA = -1;
	private static final String CAMINHO_MODELO_CHASSI = "jeep1.jme";
	private static final float MASSA_CHASSI = 50;
	private static final float DISTANCIA_ENTRE_EIXOS = 15;
	private static final float ALTURA_SUSPENSAO = -2.5f;
	private DynamicPhysicsNode chassi;
	private Suspensao suspensaoTraseira, suspensaoDianteira;
	private boolean macacoAcionado = false;
	private AudioTrack efeitoAudioMotor;
	
	public Carro(final PhysicsSpace pSpace, final Vector3f posicao) {
		super("carro");
		criaChassi(pSpace, posicao);
		criaSuspensao(pSpace);
	}

	private void criaSuspensao(final PhysicsSpace pSpace) {
		suspensaoTraseira = new Suspensao(pSpace, chassi, new Vector3f(-DISTANCIA_ENTRE_EIXOS/2,ALTURA_SUSPENSAO,0),false);
		this.attachChild(suspensaoTraseira);
		suspensaoDianteira = new Suspensao(pSpace, chassi, new Vector3f(DISTANCIA_ENTRE_EIXOS/2,ALTURA_SUSPENSAO,0),true);
		this.attachChild(suspensaoDianteira);
	}

	private void criaChassi(final PhysicsSpace pSpace,final Vector3f posicao) {
		chassi = pSpace.createDynamicNode();
		chassi.setName("chassi");
		chassi.setLocalTranslation(posicao);
		chassi.attachChild(UtilModels.carregaModeloJME(CAMINHO_MODELO_CHASSI));
		//chassi.createBox("box");
		chassi.generatePhysicsGeometry(true);
		chassi.setMaterial( Material.WOOD );
		//chassi.setMaterial( Material.GHOST );
		chassi.setMass(MASSA_CHASSI);
		this.attachChild(chassi);
	}
	
	public void atualizar(){
		checarForcaMacaco();
		if (efeitoAudioMotor != null)
			efeitoAudioMotor.setPitch(1+((float)getVelocidadeLinear())/200);
	}

	private void checarForcaMacaco() {
		if (macacoAcionado){
			chassi.addForce(Vector3f.UNIT_Y.mult(5000));
		}
	}

	public void acelerar(final int direcao) {
		suspensaoTraseira.acelerar(direcao);
		suspensaoDianteira.acelerar(direcao);
	}
	
	public void soltarAcelerador(){
		suspensaoTraseira.soltarAcelerador();
		suspensaoDianteira.soltarAcelerador();
	}

	public void girarVolante(final int direcao) {
		suspensaoDianteira.girarVolante(direcao);
	}
	
	public void soltarVolante(){
		suspensaoDianteira.soltarVolante();
	}
	
	public void ligarMacaco() {
		macacoAcionado = true;
	}

	public void desligarMacaco() {
		macacoAcionado = false;	
	}

	public int getVelocidadeLinear() {
		return (int) chassi.getLinearVelocity(Vector3f.ZERO).length();
	}

	public DynamicPhysicsNode getChassi() {
		return chassi;
	}

	public void setEfeitoAudioMotor(AudioTrack efeitoAudioMotor) {
		this.efeitoAudioMotor = efeitoAudioMotor;
		efeitoAudioMotor.play();
	}
	
	
}
