package br.com.doublelogic.timeracer.cenario.carro;


import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;

public class ActionAcelerador implements InputActionInterface {

	Carro carro;
	int direcao;
	
	public ActionAcelerador(final Carro car, final int direcao) {
		this.carro = car;
		this.direcao = direcao;
	}
	
	public void performAction(final InputActionEvent e) {
		if ( e.getTriggerPressed() ) {
            carro.acelerar(direcao);
        }
        else {
            carro.soltarAcelerador();
        }
	}

}
