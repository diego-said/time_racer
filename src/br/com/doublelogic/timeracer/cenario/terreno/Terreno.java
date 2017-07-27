package br.com.doublelogic.timeracer.cenario.terreno;

import javax.swing.ImageIcon;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.material.Material;
import com.jmex.terrain.TerrainPage;
import com.jmex.terrain.util.FaultFractalHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

import br.com.doublelogic.timeracer.TimeRacer;
import br.com.doublelogic.timeracer.cenario.UtilTexture;

/**
 * Classe responsável por criar um terreno acidentado para o jogo de corrida
 * @author Diego
 *
 */
public class Terreno extends Node {

	private TerrainPage blocoterreno = null;

	public Terreno(PhysicsSpace pSpace) {
		criaTerreno();
		criaRepresentacaoFisica(pSpace);
		criaObstaculos(pSpace);
	}

	//Método responsável por criar 50 obstáculos aleatórios
	private void criaObstaculos(PhysicsSpace space) {
		for (int i = 1; i <= 50; i++)
			criaObstaculo(space);
	}

	//Método responsável por criar uma caixa de madeira em um ponto aleatório do mapa
	private void criaObstaculo(PhysicsSpace space) {
		float x = (float) (Math.random() * 15000);
		float z = (float) (Math.random() * 15000);
		Vector3f posicao = new Vector3f(x, 0, z);
		posicao.y = blocoterreno.getHeight(posicao) + 50;
		Node caixa = new ObstaculoMovel(space, "box.jme", posicao);
		this.attachChild(caixa);
	}

	public TerrainPage getBlocoTerreno() {
		return blocoterreno;
	}

	//Método responsável por criar a área do terreno
	private void criaTerreno() {
		FaultFractalHeightMap heightMap = new FaultFractalHeightMap(257, 32, 0, 255, 0.75f);
		Vector3f escalaTerreno = new Vector3f(200, 10, 200);
		Vector3f posicao = new Vector3f(-200, -45, -200);
		blocoterreno = new TerrainPage("Terrain", 33, heightMap.getSize(), escalaTerreno, heightMap.getHeightMap(), true);
		blocoterreno.setLocalTranslation(posicao);
		
		aplicaTextura(heightMap);
		
		this.attachChild(blocoterreno);
	}

	//Método responsável por carregar e aplicar as texturas
	private void aplicaTextura(FaultFractalHeightMap heightMap) {
		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(heightMap);
		pt.addTexture(new ImageIcon(TimeRacer.class.getResource("resources/imgs/textures/grassb.png")), -128, 0, 128);
		pt.addTexture(new ImageIcon(TimeRacer.class.getResource("resources/imgs/textures/dirt.jpg")), 0, 128, 255);
		pt.addTexture(new ImageIcon(TimeRacer.class.getResource("resources/imgs/textures/highest.jpg")), 128, 255, 384);
		pt.createTexture(512);
		Texture texturaBasica = TextureManager.loadTexture(pt.getImageIcon().getImage(), Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
		UtilTexture.configuraTextura(texturaBasica);

		Texture texturaDetalhe = UtilTexture.carregaTextura("resources/imgs/textures/Detail.jpg");
		texturaDetalhe.setWrap(Texture.WM_WRAP_S_WRAP_T);

		TextureState aplicadorTexturas = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		aplicadorTexturas.setTexture(texturaBasica, 0);
		aplicadorTexturas.setTexture(texturaDetalhe, 1);
		aplicadorTexturas.setEnabled(true);

		blocoterreno.setDetailTexture(1, 128);
		blocoterreno.setRenderState(aplicadorTexturas);
		blocoterreno.updateRenderState();
	}

	private void criaRepresentacaoFisica(PhysicsSpace pSpace) {
		StaticPhysicsNode staticNode = pSpace.createStaticNode();
		staticNode.attachChild(blocoterreno);
		staticNode.generatePhysicsGeometry(true);
		staticNode.setMaterial(Material.WOOD);
		this.attachChild(staticNode);
	}
	
}
