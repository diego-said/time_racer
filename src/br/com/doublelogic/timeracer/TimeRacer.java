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
 * Thread principal do jogo, com a inicialização dos objetos que irão compor a cena. 
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
	 * Cria uma instância de Janken e inicia a thread de renderização.
	 * Nessa thread será chamado o método simpleInitGame(), e e seguida o loop principal
	 * controle do jogo, que executa infinitamente a sequência básica:
	 * 1 - Entrada de comandos;
	 * 2 - Atualização da cena - incluindo simulação física e método simpleUpdate() -;
	 * 3 - Renderização da cena.
	 * @param args
	 */
	public static void main(final String[] args) {
		TimeRacer game = new TimeRacer();
		// Obrigando o surgimento da tela de configuração, com um logotipo personalizado.
		game.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG,TimeRacer.class.getResource("resources/imgs/logo.jpg"));
		// Iniciando a thread do jogo.
		game.start();
		
	}

	protected void simpleInitGame() {
		// algumas otimizações personalizadas simples para o jogo
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
	 * Modificação da configuração padrão para melhorar desempenho.
	 */
	private void configuresOptimizations() {
		// removendo triangulos virados para trás através de backface culling, para ganhar desempenho
		CullState cs = display.getRenderer().createCullState();
		cs.setCullMode(CullState.CS_BACK);
		rootNode.setRenderState(cs);
		// desabilitando a fonte padrão de luz, que é muito fraca. A cena será desenhada sem luzes OpenGL
		lightState.setEnabled(false);
	}
	
	/**
	 * Cria um nó para a árvore (Scene Graph) que representa o céu da cena.
	 * Esse céu precisa ser posicionado relativamente à camera, motivo pelo qual a mesma
	 * é passada como Parâmetro para seu construtor.
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
	 * Criação do nó que representa o terreno acidentado.
	 * Esse terreno contém uma representação física portanto exige o controlador da simulação
	 * como parâmetro de seu construtor.
	 */
	private void criarTerreno() {
		terreno = new Terreno(getPhysicsSpace());
		rootNode.attachChild(terreno);
	}
	
	/**
	 * Cria a representação da pista, composta por diversos marcos dispostos sobre o terreno.
	 * Pista será responsável por identificar o momento em que o carro passa pelos marcos.
	 */
	private void criaPista() {
		pista = new Pista(terreno);
		rootNode.attachChild(pista);
	}
	
	/**
	 * Instancia o carro que será controlado no jogo. São passados como
	 * parâmetros para o construtor o controlador da simulação física e a posição inicial desejada
	 * para o carro. A posição onde o carro irá ser criado deve ter sua altura (Y) relativa ao
	 * terreno. Para isso usamos um método que obtem a altura do terreno nessa
	 * posição onde iremos criar o carro.
	 */
	private void criarCarro() {
		Vector3f posicao = new Vector3f();
		posicao.y = terreno.getBlocoTerreno().getHeight(posicao) + 10;
		carro = new Carro(getPhysicsSpace(), posicao);
		terreno.attachChild(carro);
		// Adicionando callback que irá atualizar a força do macaco hidraulico (aperte ESPACO se virar o carro)
		getPhysicsSpace().addToUpdateCallbacks(new AtualizadorCarro(carro));
		// configurando a gravidade do mundo para grudar o carro no chão...
		getPhysicsSpace().setDirectionalGravity(new Vector3f(0, -30, 0));
	}
	
	/**
	 * Cria o Painel de informações (HUD - heads up display) para o jogador.
	 * O painel, como os demais objetos da cena, herda de Node (Nó), e deve
	 * ser vinculado ao nó principal do Scene Graph (rootNode);
	 */
	private void criaHUD() {
		hud = new Painel(this);
		rootNode.attachChild(hud);
	}
	
	/**
	 * Altera o controlador da camera e do teclado (InputHandler - input) para uma ChaseCamera personalizada.
	 * Esse novo controlador irá mover a câmera em perseguição ao objeto alvo - segundo parâmetro do construtor -,
	 * nesse caso o chassi do carro. Também são adicionado os eventos para controle do carro, como aceleração e manobra,
	 * vinculando-os às teclas correspondentes.
	 */
	private void criaControladorCameraTeclado() {
		input = ChaseCameraCustomizada.getInstance(cam, carro.getChassi());
		input.addAction(new ActionAcelerador(carro, Carro.FRENTE),	InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_UP, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionAcelerador(carro, Carro.TRAS), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_DOWN, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionDirecao(carro, Carro.ESQUERDA), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_LEFT, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionDirecao(carro, Carro.DIREITA), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_RIGHT, InputHandler.AXIS_NONE, false);
		input.addAction(new ActionMacacoHidraulico(carro), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_SPACE, InputHandler.AXIS_NONE, false);
		
		// Para atualizar a câmera junto com a simulação física (veja comentários em AtualizadorCamera e ChaseCameraCustomizada)
		getPhysicsSpace().addToUpdateCallbacks(	new AtualizadorCamera((ChaseCameraCustomizada) input));
	}
	
	/**
	 * Atualiza os objetos da cena dentro do loop principal de controle.
	 * Executado após a checagem de controles e a atualização da simulação física, mas antes
	 * da renderização da cena. A maioria dos objetos será atualizada pela engine de física.
	 * Apenas algumas atualizações que não são automáticas devem ser chamadas aqui.
	 */
	@Override
	protected void simpleUpdate() {
		super.simpleUpdate();
		//atualiza o controlador de áudio posicional
		controleAudio.updateAudio();
		// atualiza o posicionamento do céu, relativo à câmera
		ceu.atualizar();
		// checa a passagem do carro pelos marcos da pista
		pista.checaMarcos(carro);
		// atualiza as informações do painel
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
