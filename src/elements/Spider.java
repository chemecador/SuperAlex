package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Spider extends Enemigo {
	private Animation spider;
	private boolean pisa;

	public Spider(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		tieneCabeza = true;
		peligroso = true;
		velocidad = 100;
		spider = loadFullAnimation("enemies/spider.png", 2, 1, 0.4f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		cabeza = new Element(0, 0, s, this.getWidth()*3/4, this.getHeight()/8);
		cabeza.setRectangle();
		ponerCabeza();
		ponerPies();
	}
	private void ponerCabeza() {
		cabeza.setPosition(this.getX()+5, this.getY() + this.getHeight() * 7 / 8);
	}

	private void ponerPies() {
		if (direccion == -1) {
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
		} else {
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
		}
	}
	@Override
	public void morir() {
		super.morir();
		pies.setEnabled(false);
		cabeza.setEnabled(false);
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
		w = w*2/3;
		float[] vertices = { padX + 10, padY, w - padX + 10, padY, w - padX + 10, h - padY, padX + 10, h - padY };
		colision = new Polygon(vertices);
		this.setOrigin(w / 2, h / 2);
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
		ponerPies();
		ponerCabeza();
		moveBy(direccion * velocidad * delta, 0);
	}
	
}
