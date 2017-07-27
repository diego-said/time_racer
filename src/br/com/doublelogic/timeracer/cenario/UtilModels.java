package br.com.doublelogic.timeracer.cenario;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import com.jme.bounding.BoundingBox;
import com.jme.scene.Node;
import com.jme.util.TextureKey;
import com.jme.util.export.binary.BinaryImporter;

import br.com.doublelogic.timeracer.TimeRacer;

public class UtilModels {

	private static URL pathTexture;

	// Determina o caminho relativo das texturas dos modelos
	static {
		pathTexture = TimeRacer.class.getResource("resources/models/");
		TextureKey.setOverridingLocation(pathTexture);
	}
	
	/**
	 * Método que captura a localização do modelo
	 * @param caminho com o nome do arquivo do modelo.
	 * @return URL do arquivo
	 */
	public static URL getModel(String fileName){
		return TimeRacer.class.getResource("resources/models/" + fileName);
	}
	
	/**
	 * Carrega um modelo no formato JME de um arquivo, criando uma caixa de colisão.
	 * 
	 * @param caminho nome do arquivo do modelo JME.
	 * @return o modelo carregado
	 * @throws IOException
	 */
	public static Node carregaModeloJME(String fileName) {
		try {
			URL urlModelo = getModel(fileName);
			BufferedInputStream leitorBinario = new BufferedInputStream(urlModelo.openStream());
			Node modelo = (Node) BinaryImporter.getInstance().load(leitorBinario);
			modelo.setModelBound(new BoundingBox());
			modelo.updateModelBound();
			return modelo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
