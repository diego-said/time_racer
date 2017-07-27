package br.com.doublelogic.timeracer.cenario.ceu;

import com.jme.image.Texture;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.scene.Skybox;

import br.com.doublelogic.timeracer.cenario.UtilTexture;

public class Ceu extends Node {
	
	private Skybox skybox;
	private Camera cam;
	
	public Ceu(Camera cam){
		
		//Atribuindo o nome do nó
		super("sky");
		this.cam = cam;
		
		//Criando o Skybox
		skybox = new Skybox("skybox", 10, 10, 10);
		
		//Carregando as texturas
		Texture north = UtilTexture.carregaTextura("resources/imgs/sky/north.jpg");
	    Texture south = UtilTexture.carregaTextura("resources/imgs/sky/south.jpg");
	    Texture east = UtilTexture.carregaTextura("resources/imgs/sky/east.jpg");
	    Texture west = UtilTexture.carregaTextura("resources/imgs/sky/west.jpg");
	    Texture top = UtilTexture.carregaTextura("resources/imgs/sky/top.jpg");
	    Texture bottom = UtilTexture.carregaTextura("resources/imgs/sky/bottom.jpg");
	    
	    //vinculando as texturas ao skybox
	    skybox.setTexture(Skybox.NORTH, north);
	    skybox.setTexture(Skybox.SOUTH, south);
	    skybox.setTexture(Skybox.EAST, east);
	    skybox.setTexture(Skybox.WEST, west);
	    skybox.setTexture(Skybox.UP, top);
	    skybox.setTexture(Skybox.DOWN, bottom);
	    skybox.preloadTextures();
	    this.attachChild(skybox);
		
	}
	
	public Skybox getSkybox() {
		return skybox;
	}

	public void atualizar() {
		skybox.setLocalTranslation(cam.getLocation());
	}
}
