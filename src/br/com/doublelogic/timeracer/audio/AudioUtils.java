package br.com.doublelogic.timeracer.audio;

import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;
import com.jmex.audio.AudioTrack.TrackType;

import br.com.doublelogic.timeracer.TimeRacer;

/**
 * Classe responsável por dar load em um arquivo de música ou de efeito sonóro.
 * @author Diego
 *
 */

public class AudioUtils {

	//Método para dar load em uma música
	public static AudioTrack getMusic(String path) {
		AudioTrack sound = AudioSystem.getSystem().createAudioTrack(TimeRacer.class.getResource(path), false);
        sound.setType(TrackType.MUSIC);
        sound.setRelative(true);
        sound.setVolume(0.1f);
        sound.setLooping(false);
        return sound;
    }

	//Método para dar load em um som
	public static AudioTrack getSFX(String path) {
        AudioTrack sound = AudioSystem.getSystem().createAudioTrack(TimeRacer.class.getResource(path), false);
        sound.setType(TrackType.ENVIRONMENT);
        sound.setRelative(true);
        sound.setLooping(true);
		sound.setVolume(1f);
        return sound;
    }
	
}
