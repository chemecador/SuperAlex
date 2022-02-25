package elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Consejos {
	CARACOl("Los caracoles son inofensivos, no deber�a hacerles da�o..."), 
	SPIDER("Los ara�uscos no me gustan un pelo..."),
	BANDIDO("Yo creo que el bandido me quiere apu�alar. Tendr� cuidado con �l..."),
	PRINCESA("Me da la sensaci�n de que esa princesa es peligrosa. No deber�a acercarme a ella..."),
	ENEMIGO("Si salto encima de los enemigos, creo que los podr� derrotar...");

	private String valor;

	Consejos(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	private static final List<Consejos> CONSEJOS = Collections.unmodifiableList(Arrays.asList(values()));

	private static final int SIZE = CONSEJOS.size();
	private static final Random RANDOM = new Random();

	public static String consejoRandom() {
		return CONSEJOS.get(RANDOM.nextInt(SIZE)).getValor().toString();
	}
}
