package elements;

public enum Consejos {
		CARACOl("Los caracoles son inofensivos, no debería hacerles daño."),
	    SPIDER("Los arañuscos no me gustan un pelo"),
	    BANDIDO("Yo creo que el bandido me quiere apuñalar. Tendré cuidado con él."),
	    PRINCESA("Me da la sensación de que esa princesa es peligrosa. No debería acercarme a ella..."),
	    ENEMIGO("Si salto encima de los enemigos, creo que los podré derrotar.");

	    private String valor;

	    Consejos(String valor) {
	        this.valor = valor;
	    }

	    public String getValor() {
	        return valor;
	    }
}
