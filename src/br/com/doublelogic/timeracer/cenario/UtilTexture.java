package br.com.doublelogic.timeracer.cenario;

import com.jme.image.Texture;
import com.jme.util.TextureManager;

import br.com.doublelogic.timeracer.TimeRacer;

public class UtilTexture {

	public static Texture carregaTextura(String caminho){
		Texture textura = TextureManager.loadTexture(TimeRacer.class.getResource(caminho),Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
		textura.setApply(Texture.AM_COMBINE);
		configuraTextura(textura);
		return textura;
	}

	public static void configuraTextura(Texture textura) {
		textura.setCombineFuncRGB(Texture.ACF_MODULATE);
		textura.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		textura.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		textura.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
		textura.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		textura.setCombineScaleRGB(1.0f);
	}
}
