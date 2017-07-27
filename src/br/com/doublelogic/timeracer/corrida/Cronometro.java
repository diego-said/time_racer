package br.com.doublelogic.timeracer.corrida;

import com.jme.scene.Node;

/**
 * Marcador de tempo para a corrida.
 * Usado para atualizar o tempo decorrido e formatá-lo para o painel.
 *
 */
public class Cronometro extends Node {

	/**
	 * Singleton
	 */
	private static Cronometro contador;
	
	private long tempoAcumulado, inicio;
	private boolean cronometrando;
	// Para evitar a instanciação de objetos a cada frame, o que prejudicaria
	// o desempenho por conta da coleta de lixo.
	private StringBuffer tempoFormatado;
	
	/**
	 * Singleton factory method
	 * @return
	 */
	public static Cronometro getCronometro(){
		if (contador == null)
			contador = new Cronometro();
		return contador;
	}
	
	private Cronometro(){
		cronometrando = false;
		tempoFormatado = new StringBuffer(10);
	}
	
	/**
	 * Atualiza o tempo acumulado em milisegundos
	 *
	 */
	private void atualizar(){
		if (cronometrando){
			long tempoAtual = System.currentTimeMillis();
			tempoAcumulado = tempoAtual - inicio;
		}
	}
	
	/**
	 * Inicia a corrida na passagem pelo primeiro marco.
	 * Pode ser utilizado para registrar tempos parciais (não implementado).
	 */
	public void marcarTrecho() {
		if (!cronometrando){
			cronometrando = true;
			inicio = System.currentTimeMillis();
		}
	}

	public void finalizar() {
		cronometrando = false;
	}
	
	public StringBuffer getTempoFormatado(){
		atualizar();
		int decimos = (int) tempoAcumulado % 1000;
		int segundos = (int) (tempoAcumulado/1000) % 60;
		int minutos =  (int) tempoAcumulado/60000;
		tempoFormatado.setLength(0);
		if (minutos < 10)
			tempoFormatado.append("0");
		tempoFormatado.append(minutos+":");
		if (segundos < 10)
			tempoFormatado.append("0");
		tempoFormatado.append(segundos+":"+decimos);
		return tempoFormatado;
	}
	
}
