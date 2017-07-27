package br.com.doublelogic.timeracer.hud;


import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.Text;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;

import br.com.doublelogic.timeracer.TimeRacer;
import br.com.doublelogic.timeracer.cenario.UtilRenderSates;
import br.com.doublelogic.timeracer.corrida.Cronometro;


/**
 * Conjunto de informa��es para o jogador. Cont�m um painel transparente na
 * parte inferior da janela, com mensagens, a velocidade do carro e o tempo
 * transcorrido. Tamb�m inclui uma seta (cone) indicadora da dire��o at� o
 * pr�ximo marco.
 */
@SuppressWarnings("serial")
public class Painel extends Node {

	private Text textVelocimetro, textTempo, textMensagem;

	private StringBuffer velocidade = new StringBuffer(30);

	private StringBuffer tempo = new StringBuffer(30);

	private String mensagem = "";

	private TimeRacer jogo;

	/**
	 * Construtor dispon�vel para o HUD. Recebe a inst�ncia de RaceMania (jogo)
	 * para obter objetos da cena que ser�o usados para atualizar as
	 * informa��es, como o carro (velocidade) e a pista (proximo marco para a
	 * seta).
	 * 
	 * @param mania
	 */
	public Painel(TimeRacer jogo) {
		super("hudNode");
		this.jogo = jogo;
		criaFundoTransparente();
		criaTextos();
		criaSeta();
		updateRenderState();
	}

	/**
	 * Cria a seta indicativa para o pr�ximo marco. Essa seta deve ser
	 * atualizada junto com a simula��o f�sica, pelo mesmo motivo que a c�mera:
	 * evitar vibra��o desconfort�vel.
	 */
	private void criaSeta() {
		Seta seta = new Seta(jogo.getPista(), jogo.getCarro());
		this.attachChild(seta);
		// vinculando a atualiza��o da seta � simula��o f�sica
		jogo.getPhysicsSpace().addToUpdateCallbacks(new AtualizadorSeta(seta));
	}

	/**
	 * Cria as int�ncias de Text que ser�o exibidas no painel inferior. Esses
	 * objetos Text s�o posicionados do lado esquerdo (x=0) em alturas
	 * diferentes, a partir da origem (y = 20, ou y = 40, ou y = 60).
	 */
	private void criaTextos() {
		textTempo = Text.createDefaultTextLabel("tempo");
		textTempo.setCullMode(SceneElement.CULL_NEVER);
		textTempo.setTextureCombineMode(TextureState.REPLACE);
		textTempo.setLocalTranslation(new Vector3f(0, 20, 0));
		this.attachChild(textTempo);

		textVelocimetro = Text.createDefaultTextLabel("velocidade");
		textVelocimetro.setCullMode(SceneElement.CULL_NEVER);
		textVelocimetro.setTextureCombineMode(TextureState.REPLACE);
		textVelocimetro.setLocalTranslation(new Vector3f(0, 40, 0));
		this.attachChild(textVelocimetro);

		textMensagem = Text.createDefaultTextLabel("mensagem");
		textMensagem.setCullMode(SceneElement.CULL_NEVER);
		textMensagem.setTextureCombineMode(TextureState.REPLACE);
		textMensagem.setLocalTranslation(new Vector3f(0, 60, 0));
		this.attachChild(textMensagem);
	}

	/**
	 * Cria um painel transparente na parte inferior da tela. O painel deve ser
	 * projetado de modo ortogonal (contra o modo em perspectiva padr�o), com a
	 * inclus�o de uma transpar�ncia Alpha (�ltimo valor da inst�ncia de
	 * ColorRGBA) na cor e acionamento da mesma no RenderState (op��es de
	 * renderiza��o OpenGL).
	 */
	private void criaFundoTransparente() {

		Quad hudQuad = new Quad("hudQuad", DisplaySystem.getDisplaySystem().getWidth(), 96);
		hudQuad.getLocalTranslation().addLocal(new Vector3f(DisplaySystem.getDisplaySystem().getWidth() / 2, 48, 0));
		hudQuad.setDefaultColor(new ColorRGBA(0, 0, 0, 0.5f));
		hudQuad.setLightCombineMode(LightState.OFF);
		hudQuad.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		hudQuad.setRenderState(UtilRenderSates.createAlphaEffect());
		hudQuad.updateRenderState();
		this.attachChild(hudQuad);
	}

	/**
	 * Verifica as informa��es atualizadas e coloca-as nos textos
	 * correspondentes para exibi��o.
	 * 
	 */
	public void atualizar() {
		velocidade.setLength(0);
		velocidade.append("Velocidade: " + jogo.getCarro().getVelocidadeLinear() + " km/h");
		textVelocimetro.print(velocidade);

		tempo.setLength(0);
		tempo.append("Tempo: " + Cronometro.getCronometro().getTempoFormatado());
		textTempo.print(tempo);
		
		if (jogo.getPista().getMarcoAtual() != null) {
			int distancia = (int) jogo.getPista().getMarcoAtual().getWorldTranslation().subtract(jogo.getCarro().getChassi().getWorldTranslation()).length() / 2;
			textMensagem.print("Marco: " + distancia + " m");
		}
		else
			textMensagem.print(mensagem);
	}

	/**
	 * Para atualizar a mensagem que � exibida na primeira linha do painel.
	 * 
	 * @param mensagem
	 *            que ser� exibida
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
