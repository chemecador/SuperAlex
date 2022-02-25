package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Caracol extends Enemigo {
	private Animation izq;
	private Animation der;
	private boolean pisa;

	public Caracol(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		tieneCabeza = true;
		peligroso = false;
		this.setEnabled(true);
		velocidad = 5;
		izq = loadFullAnimation("enemies/caracolIzquierda.png", 1, 1, 0.2f, true);
		der = loadFullAnimation("enemies/caracolDerecha.png", 1, 1, 0.2f, true);
		direccion = -1;
		setRectangle();
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		cabeza = new Element(0, 0, s, this.getWidth(), this.getHeight() / 8);
		cabeza.setRectangle();
		ponerCabeza();
		ponerPies();

	}

	@Override
	public void setRectangle() {
		float w, h;
		if (this.polyWidth != getWidth() && this.polyWidth > 0) {
			w = this.polyWidth;
		} else {
			w = this.getWidth();
		}
		if (this.polyHigh != this.getHeight() && this.polyHigh > 0) {
			h = this.polyHigh;
		} else {
			h = getHeight();
		}
		float[] vertices = { padX, padY, w - padX, padY, w - padX, h - padY, padX, h - padY };
		colision = new Polygon(vertices);
		this.setOrigin(w / 2, h / 2);
	}

	private void ponerCabeza() {
		cabeza.setPosition(this.getX(), this.getY() + this.getHeight() * 7 / 8);
	}

	private void ponerPies() {
		if (direccion == -1) {
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
			this.setAnimation(izq);
		} else {
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
			this.setAnimation(der);
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