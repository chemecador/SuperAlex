package elements;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Enemigo extends Element {

	public int velocidad;
	public int direccion;
	public boolean peligroso;
	private boolean vivo;
	public GameScreen nivel;
	public Element cabeza;
	public Element pies;
	public boolean tieneCabeza; //public ??
	
	public Enemigo(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s);
		this.tieneCabeza = false;
		this.vivo = true;
		this.nivel = nivel;
		this.setEnabled(true);
	}
	public void morir() {
		this.setEnabled(false);
		this.vivo = false;
		this.pies.setEnabled(false);
		this.cabeza.setEnabled(false);
	}
	public boolean isAlive() {
		return this.vivo;
	}
	public boolean tieneCabeza() {
		return tieneCabeza;
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
		w = w / 2;
		float[] vertices = { padX + 25, padY, w - padX + 25, padY, w - padX + 25, h - padY, padX + 25, h - padY };
		colision = new Polygon(vertices);
		this.setOrigin(w / 2, h / 2);
	}
}
