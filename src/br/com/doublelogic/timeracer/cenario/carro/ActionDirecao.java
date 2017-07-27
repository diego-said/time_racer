package br.com.doublelogic.timeracer.cenario.carro;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;

public class ActionDirecao implements InputActionInterface {

	Carro carro;
	int direcao;
	
	public ActionDirecao(Carro car, int direction) {
		this.carro = car;
		this.direcao = direction;
	}

	public void performAction(final InputActionEvent e) {
		if ( e.getTriggerPressed() ) {
            carro.girarVolante(direcao);
        }
        else {
            carro.soltarVolante();
        }
	}

}
