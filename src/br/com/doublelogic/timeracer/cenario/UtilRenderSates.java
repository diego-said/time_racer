package br.com.doublelogic.timeracer.cenario;

import com.jme.scene.state.AlphaState;
import com.jme.system.DisplaySystem;

public class UtilRenderSates {

	public static AlphaState createAlphaEffect(){
		AlphaState as = DisplaySystem.getDisplaySystem().getRenderer().createAlphaState();
		as.setBlendEnabled(true);
        as.setSrcFunction(AlphaState.SB_SRC_ALPHA);
        as.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
        as.setTestEnabled(false);
        as.setEnabled(true);
        return as;
	}
	
}
