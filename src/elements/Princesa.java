package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import screens.GameScreen;

public class Princesa extends Enemigo {

	private float tiempoComportamiento;
	private float cuentaComportamiento;

	private float distanciaVision;
	private float aceleracion;

	private float tiempoDisparo;
	private float cuentaDisparo;
	private Array<Corazon> corazones;
	boolean persiguiendo;
	private int numCorazones;
	private int corazonActual;
	private int numero;
	private int direccion;
	private int velocidad;
	private Animation quieta;
	private Animation izquierda;
	private Animation derecha;
	private Element pies;
	private boolean pisa;

	public Princesa(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.setEnabled(true);
		peligroso = true;
		persiguiendo = false;
		cuentaComportamiento = 0;
		tiempoComportamiento = 0.5f;
		distanciaVision = 72;
		numCorazones = 5;
		corazonActual = 0;
		corazones = new Array<Corazon>();
		tiempoDisparo = 3;
		cuentaDisparo = 0;
		velocidad = 50;
		izquierda = loadFullAnimation("enemies/princesaIzquierda.png", 2, 1, 0.2f, true);
		derecha = loadFullAnimation("enemies/princesaDerecha.png", 2, 1, 0.2f, true);
		quieta = loadFullAnimation("enemies/princesaQuieta.png", 1, 1, 0.2f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		ponerPies();
		
		for(int i=0; i<numCorazones; i++) {
			corazones.add(new Corazon(0,0,s,100));
			
		}

	}

	private void ponerPies() {
		if (direccion == -1) {
			this.setAnimation(izquierda);
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
		} else {
			this.setAnimation(derecha);
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
		}

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

		if (this.getEnabled()) {
			if (persiguiendo) {
				perseguir();
				if (cuentaDisparo <= 0) {
					System.out.println("Te disparo");
					disparar();
				} else {
					cuentaDisparo -= delta;
				}
			} else {
				if (Math.sqrt(Math.pow(this.getX() - Parametros.jugadorx, 2)
						+ Math.pow(this.getY() - Parametros.jugadory, 2)) < distanciaVision) {
					System.out.println("Te persigo");
					persiguiendo = true;

				} else {
					if (cuentaComportamiento <= 0) {
						numero = (int) Math.floor(Math.random() * 5 + 1);
						cuentaComportamiento = tiempoComportamiento;
					} else {
						cuentaComportamiento -= delta;
					}

					switch (numero) {
					case 1:
						acceleration.add(-aceleracion, 0);
						break;
					case 2:
						acceleration.add(aceleracion, 0);
						break;
					case 3:
						acceleration.add(0, -aceleracion);
						break;
					case 4:
						acceleration.add(0, aceleracion);
						break;
					case 5:
						acceleration.add(0, 0);
						this.cuentaComportamiento = 0f;
						break;
					}
				}
			}
			this.applyPhysics(delta);
		}
		for (Corazon c : corazones) {
			if (c.getEnabled() && c.overlaps(nivel.player)) {
				Parametros.nivel--;
				GameScreen.isAlive = false;
				c.setEnabled(false);

			}

		}
		ponerPies();

	}

	public void perseguir() {
		if (Parametros.jugadorx < this.getX()) {
			acceleration.add(-aceleracion, 0);
		} else if (Parametros.jugadorx > this.getX()) {
			acceleration.add(aceleracion, 0);
		}
		if (Parametros.jugadory < this.getY()) {
			acceleration.add(0, -aceleracion);
		} else if (Parametros.jugadory > this.getY()) {
			acceleration.add(0, aceleracion);
		}
	}

	public void disparar() {
		float x = Parametros.jugadorx - this.getX();
		float y = Parametros.jugadory - this.getY();
		Vector2 vector = new Vector2(x, y).nor();
		cuentaDisparo = tiempoDisparo;
		corazones.get(this.corazonActual).disparar(vector.x, vector.y, this.getX(), this.getY());
		corazonActual++;
		corazonActual = corazonActual % numCorazones;

	}

}