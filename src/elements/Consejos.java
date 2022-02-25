package elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Consejos {
	CARACOl("Los caracoles son inofensivos, no debería hacerles daño..."), 
	SPIDER("Los arañuscos no me gustan un pelo..."),
	BANDIDO("Yo creo que el bandido me quiere apuñalar. Tendré cuidado con él..."),
	PRINCESA("Me da la sensación de que esa princesa es peligrosa. No debería acercarme a ella..."),
	ENEMIGO("Si salto encima de los enemigos, creo que los podré derrotar...");

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
