package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Bandido extends Enemigo {
	private Animation quieto;
	private Animation izquierda;
	private Animation derecha;
	private boolean pisa;

	public Bandido(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		peligroso = true;
		tieneCabeza = true;
		velocidad = 200;
		izquierda = loadFullAnimation("enemies/bandidoIzquierda.png", 1, 1, 0.2f, true);
		derecha = loadFullAnimation("enemies/bandidoDerecha.png", 1, 1, 0.2f, true);
		quieto = loadFullAnimation("enemies/bandidoIzquierda.png", 1, 1, 0.2f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		cabeza = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 8);
		cabeza.setRectangle();
		ponerCabeza();
		ponerPies();

	}

	private void ponerCabeza() {
		cabeza.setPosition(this.getX() + this.getWidth() * 4 / 12, this.getY() + this.getHeight() * 7 / 8);
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
	public void morir() {
		super.morir();
		pies.setEnabled(false);
		cabeza.setEnabled(false);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		pisa = false;

		for (Solid pared : nivel.paredes) {
			if (pies.overlaps(pared)) {
				direccion *= -1;
				break;
			}
		}
		for (Solid solido : nivel.suelo) {
			if (pies.overlaps(solido)) {
				pisa = true;
			}
		}

		if (!pisa) {
			direccion *= -1;
		}

		moveBy(direccion * velocidad * delta, 0);
		ponerPies();
		ponerCabeza();

	}

}