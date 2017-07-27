package br.com.doublelogic.timeracer.cenario.carro;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;

public class ActionMacacoHidraulico implements InputActionInterface {

	Carro carro;
	
	public ActionMacacoHidraulico(Carro carro) {
		this.carro = carro;
	}

	public void performAction(InputActionEvent e) {
		if ( e.getTriggerPressed() ) {
            carro.ligarMacaco();
        }
        else {
            carro.desligarMacaco();
        }
	}

}
