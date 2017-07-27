package br.com.doublelogic.timeracer.audio;

import com.jme.renderer.Camera;
import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;
import com.jmex.audio.MusicTrackQueue.RepeatType;


/**
 * Classe responsável pelo controle de audio do jogo
 * @author Diego
 *
 */
public class AudioController {

	private AudioSystem audioSystem;
	
	public AudioController(Camera cam) {
		audioSystem = AudioSystem.getSystem();
		// A camera (cam) é a posição do "ouvido" em um sistema de audio 3D
		audioSystem.getEar().trackOrientation(cam);
		audioSystem.getEar().trackPosition(cam);
		
		addMusic();
	}

	private void addMusic() {
		audioSystem.getMusicQueue().setRepeatType(RepeatType.ALL);
		audioSystem.getMusicQueue().addTrack(AudioUtils.getMusic("resources/audio/music/crazy.ogg"));
		audioSystem.getMusicQueue().play();
	}
	
	public AudioTrack add3DEffect(String path){
		AudioTrack efeito = AudioUtils.getSFX(path);
        return efeito;
	}

	public void updateAudio() {
		audioSystem.update();
	}
	
}
