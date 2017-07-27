package br.com.doublelogic.timeracer;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.scene.state.CullState;
import com.jmex.physics.util.SimplePhysicsGame;

import br.com.doublelogic.timeracer.audio.AudioController;
import br.com.doublelogic.timeracer.camera.AtualizadorCamera;
import br.com.doublelogic.timeracer.camera.ChaseCameraCustomizada;
import br.com.doublelogic.timeracer.cenario.carro.ActionAcelerador;
import br.com.doublelogic.timeracer.cenario.carro.ActionDirecao;
import br.com.doublelogic.timeracer.cenario.carro.ActionMacacoHidraulico;
import br.com.doublelogic.timeracer.cenario.carro.AtualizadorCarro;
import br.com.doublelogic.timeracer.cenario.carro.Carro;
import br.com.doublelogic.timeracer.cenario.ceu.Ceu;
import br.com.doublelogic.timeracer.cenario.terreno.Terreno;
import br.com.doublelogic.timeracer.corrida.Pista;
import br.com.doublelogic.timeracer.hud.Painel;

/**
 * Thread principal do jogo, com a inicializa��o dos objetos que ir�o compor a cena. 
 * @author Diego
 *
 */
public class TimeRacer extends SimplePhysicsGame {
	
	private Ceu ceu;
	private AudioController controleAudio;
	private Terreno terreno;
	private Carro carro;
	private Pista pista;
	private Painel hud;
	
	/**
	 * Cria uma inst�ncia de Janken e inicia a thread de renderiza��o.
	 * Nessa thread ser� chamado o m�todo simpleInitGame(), e e seguida o loop principal
	 * controle do jogo, que executa infinitamente a sequ�ncia b�sica:
	 * 1 - Entrada de comandos;
	 * 2 - Atualiza��o da cena - incluindo simula��o f�sica e m�todo simpleUpdate() -;
	 * 3 - Renderiza��o da cena.
	 * @param args
	 */
	public static void main(final String[] args) {
		TimeRacer game = new TimeRacer();
		// Obrigando o surgimento da tela de configura��o, com um logotipo personalizado.
		game.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG,TimeRacer.class.getResource("resources/imgs/logo.jpg"));
		// Iniciando a thread do jogo.
		game.start();
		
	}

	protected void simpleInitGame() {
		// algumas otimiza��es personalizadas simples para o jogo
		configuresOptimizations();
		
		criarCeu();
		criarTerreno();
		criarCarro();
		criaControladorCameraTeclado();
		controladorAudio();
		criaPista();
		criaHUD();
		
	}
	
	/**
	 * Modifica��o da configura��o padr�o para melhorar desempenho.
	 */
	private void configuresOptimizations() {
		// removendo triangulos virados para tr�s atrav�s de backface culling, para ganhar desempenho
		CullState cs = display.getRenderer().createCullState();
		cs.setCullMode(CullState.CS_BACK);
		rootNode.setRenderState(cs);
		// desabilitando a fonte padr�o de luz, que � muito fraca. A cena ser� desenhada sem luzes OpenGL
		lightState.setEnabled(false);
	}
	
	/**
	 * Cria um n� para a �rvore (Scene Graph) que representa o c�u da cena.
	 * Esse c�u precisa ser posicionado relativamente � camera, motivo pelo qual a mesma
	 * � passada como Par�metro para seu construtor.
	 */
	private void criarCeu() {
		ceu = new Ceu(cam);
		rootNode.attachChild(ceu);
	}
	
	/**
	 * Inicia o controle de audio
	 */
	private void controladorAudio(){
		controleAudio = new AudioController(cam);
		carro.setEfeitoAudioMotor(controleAudio.add3DEffect("resources/audio/sounds/jeep.ogg"));
		
	}
	
	/**
	 * Cria��o do n� que representa o terreno acidentado.
	 * Esse terreno cont�m uma representa��o f�sica portanto exige o controlador da simula��o
	 * como par�metro de seu construtor.
	 */
	private void criarTerreno() {
		terreno = new Terreno(getPhysicsSpace());
		rootNode.attachChild(terreno);
	}
	
	/**
	 * Cria a representa��o da pista, composta por diversos marcos dispostos sobre o terreno.
	 * Pista ser� respons�vel por identificar o momento em que o carro passa pelos marcos.
	 */
	private void criaPista() {
		pista = new Pista(terreno);
		rootNode.attachChild(pista);
	}
	
	/**
	 * Instancia o carro que ser� controlado no jogo. S�o passados como
	 * par�metros para o construtor o controlador da simula��o f�sica e a posi��o inicial desejada
	 * para o carro. A posi��o onde o carro ir� ser criado deve ter sua altura (Y) relativa ao
	 * terreno. Para isso usamos um m�todo que obtem a altura do terreno nessa
	 * posi��o onde iremos criar o carro.
	 */
	private void criarCarro() {
		Vector3f posicao = new Vector3f();
		posicao.y = terreno.getBlocoTerreno().getHeight(posicao) + 10;
		carro = new Carro(getPhysicsSpace(), posicao);
		terreno.attachChild(carro);
		// Adicionando callback que ir� atualizar a for�a do macaco hidraulico (aperte ESPACO se virar o carro)
		getPhysicsSpace().addToUpdateCallbacks(new AtualizadorCarro(carro));
		// configurando a gravidade do mundo para grudar o carro no ch�o...
		getPhysicsSpace().setDirectionalGravity(new Vector3f(0, -30, 0));
	}
	
	/**
	 * Cria o Painel de informa��es (HUD - heads up display) para o jogador.
	 * O painel, como os demais objetos da cena, herda de Node (N�), e deve
	 * ser vinculado ao n� principal do Scene Graph (rootNode);
	 */
	private void criaHUD() {
		hud = new Painel(this);
		rootNode.attachChild(hud);
	}
	
	/**
	 * Altera o controlador da camera e do teclado (InputHandler - input) para uma ChaseCamera personalizada.
	 * Esse novo controlador ir� mover a c�mera em persegui��o ao objeto alvo - segundo par�metro do construtor -,
	 * nesse caso o chassi do carro. Tamb�m s�o adicionado os eventos para controle do carro, como acelera��o e manobra,
	 * vinculando-os �s teclas correspondentes.
	 */
	private void criaControladorCameraTeclado() {
		input = ChaseCameraCustomizada.getInstance(cam, carro.getChassi());
		input.addAction(new ActionAcelerador(carro, Carro.FRENTE),	InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_UP, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionAcelerador(carro, Carro.TRAS), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_DOWN, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionDirecao(carro, Carro.ESQUERDA), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_LEFT, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionDirecao(carro, Carro.DIREITA), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_RIGHT, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionMacacoHidraulico(carro), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_SPACE, InputHandler.AXIS_NONE, false);
		
		// Para atualizar a c�mera junto com a simula��o f�sica (veja coment�rios em AtualizadorCamera e ChaseCameraCustomizada)
		getPhysicsSpace().addToUpdateCallbacks(	new AtualizadorCamera((ChaseCameraCustomizada) input));
	}
	
	/**
	 * Atualiza os objetos da cena dentro do loop principal de controle.
	 * Executado ap�s a checagem de controles e a atualiza��o da simula��o f�sica, mas antes
	 * da renderiza��o da cena. A maioria dos objetos ser� atualizada pela engine de f�sica.
	 * Apenas algumas atualiza��es que n�o s�o autom�ticas devem ser chamadas aqui.
	 */
	@Override
	protected void simpleUpdate() {
		super.simpleUpdate();
		//atualiza o controlador de �udio posicional
		controleAudio.updateAudio();
		// atualiza o posicionamento do c�u, relativo � c�mera
		ceu.atualizar();
		// checa a passagem do carro pelos marcos da pista
		pista.checaMarcos(carro);
		// atualiza as informa��es do painel
		hud.atualizar();
	}

	public Ceu getCeu() {
		return ceu;
	}

	public Terreno getTerreno() {
		return terreno;
	}

	public Carro getCarro() {
		return carro;
	}

	public Pista getPista() {
		return pista;
	}

	public Painel getHud() {
		return hud;
	}

}
