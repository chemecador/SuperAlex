package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Bandido extends Enemigo {

	private int direccion;
	private int velocidad;
	private Animation quieto;
	private Animation izquierda;
	private Animation derecha;
	public Element pies;
	public Element cabeza;
	private boolean pisa;
	private boolean cabezazo;

	public Bandido(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		// TODO Auto-generated constructor stub
		this.setEnabled(true);
		velocidad = 200;
		izquierda = loadFullAnimation("enemies/bandidoIzquierda.png", 1, 1, 0.2f, true);
		derecha = loadFullAnimation("enemies/bandidoDerecha.png", 1, 1, 0.2f, true);
		quieto = loadFullAnimation("enemies/bandidoIzquierda.png", 1, 1, 0.2f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		cabeza = new Element(0, 0, s, this.getWidth()*4/5, this.getHeight()/8);
		cabeza.setRectangle();
		ponerCabeza();
		ponerPies();

	}

	private void ponerCabeza() {
		
		cabeza.setPosition(this.getX() + this.getWidth()*1/10, this.getY() + this.getHeight() * 7 / 8);
	}

	private void ponerPies() {
		if (direccion == -1) {
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
			this.setAnimation(izquierda);
		} else {
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
			this.setAnimation(derecha);
		}

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		pisa = false;
		cabezazo = false;
		for (Solid solido : nivel.suelo) {
			if (pies.overlaps(solido)) {
				pisa = true;
			}
			if (cabeza.overlaps(solido)) {
				cabezazo = true;
			}
		}
		
		if (!pisa || cabezazo) {
			direccion *= -1;
		}

		moveBy(direccion * velocidad * delta, 0);
		ponerPies();
		ponerCabeza();

	}

}
