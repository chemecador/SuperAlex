package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;

public class Player extends Element {
	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> agachado;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> quieto;

//gestión de tiempos

	public Element pies;
	public boolean tocoSuelo;

	private float walkingSpeed = 600;
	private float fuerzaSaltoChiquito = 30000;
	private float fuerzaSaltoMedio = 60000;
	private float fuerzaSaltoGrande = 65000;
	private float maxSpeedHorizontal = 300;

	public Player(float x, float y, Stage s) {
		super(x, y, s);
		this.maxSpeed = 9000;
		this.deceleration = 20000000;
		tocoSuelo = false;
		frente = loadFullAnimation("player/frente.png", 2, 1, 0.2f, true);
		agachado = loadFullAnimation("player/agachado.png", 1, 1, 0.2f, true);
		drcha = this.loadFullAnimation("player/derecha.png", 2, 1, 0.3f, true);
		izqda = this.loadFullAnimation("player/izquierda.png", 2, 1, 0.3f, true);
		quieto = this.loadFullAnimation("player/depie.png", 1, 1, 0.2f, true);
		this.setRectangle();

		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 10);
		pies.setRectangle();

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

	@Override
	public void act(float delta) {
		super.act(delta);
		controles();
		this.acceleration.add(0, Parametros.gravedad);
		this.applyPhysics(delta);
		colocarPies();
	}

	private void controles() {
		boolean quieto = true;
			//AudioManager.currentMusic.setVolume(Parametros.musicVolume)
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {

			this.setAnimation(izqda);
			this.acceleration.add(-walkingSpeed, 0);

			quieto = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			this.setAnimation(drcha);
			this.acceleration.add(walkingSpeed, 0);

			quieto = false;
		}
		if (quieto) {
			this.setAnimation(this.quieto);
		}
		if (Gdx.input.isKeyPressed(Keys.C) && tocoSuelo) {
			this.setAnimation(agachado);
			this.velocity.x = 0;
			this.velocity.y = 0;
			salta(1);
		}
		if (Gdx.input.isKeyPressed(Keys.V) && tocoSuelo) {
			this.setAnimation(agachado);
			this.velocity.x = 0;
			this.velocity.y = 0;
			salta(2);
		}
		if (Gdx.input.isKeyPressed(Keys.B) && tocoSuelo) {
			this.setAnimation(agachado);
			this.velocity.x = 0;
			this.velocity.y = 0;
			salta(3);
		}

	}

	@Override
	public void applyPhysics(float dt) {
		limitHorizontalSpeed();

		if (tocoSuelo & this.getVelocity().y < 0) {
			this.acceleration.y = 0;
			this.velocity.y = 0;
		}

		velocity.add(acceleration.x * dt, acceleration.y * dt);

		float speed = velocity.len();

		// decrease speed (decelerate) when not accelerating

		if (acceleration.len() == 0)
			speed -= deceleration * dt;

		// keep speed within set bounds
		speed = MathUtils.clamp(speed, 0, maxSpeed);

		// update velocity
		velocity.setLength(speed);

		// update position according to value stored in velocity vector
		moveBy(velocity.x * dt, velocity.y * dt);

		// reset acceleration
		acceleration.set(0, 0);

		/*
		 * // apply acceleration
		 * 
		 * velocity.add(acceleration.x * dt, acceleration.y * dt);
		 * 
		 * float speed = velocity.len();
		 * 
		 * // decrease speed (decelerate) when not accelerating
		 * 
		 * if (acceleration.len() == 0) speed -= deceleration * dt;
		 * 
		 * // keep speed within set bounds speed = MathUtils.clamp(speed, 0, maxSpeed);
		 * 
		 * // update velocity velocity.setLength(speed);
		 * 
		 * if (Math.abs(acceleration.x) > walkingSpeed) { // acceleration o velocity??
		 * if (velocity.x > 0) { velocity.x = walkingSpeed; } else { this.velocity.x =
		 * -walkingSpeed; } } // update position according to value stored in velocity
		 * vector moveBy(velocity.x * dt, velocity.y * dt); // reset acceleration
		 * acceleration.set(0, 0);
		 */
	}

	private void limitHorizontalSpeed() {
		if (Math.abs(velocity.x) > maxSpeedHorizontal) {
			if (velocity.x > 0)
				this.velocity.x = maxSpeedHorizontal;
			else
				this.velocity.x = maxSpeedHorizontal * -1;
		}
	}

	public void colocarPies() {
		this.pies.setPosition(this.getX() + this.getWidth() / 2 - this.getWidth() / 8, this.getY());

	}

	private void salta(int longitud) {
		AudioManager.playSound("audio/sounds/salto.mp3");
		if (longitud == 1) {
			this.acceleration.add(0, fuerzaSaltoChiquito);
			this.tocoSuelo = false;
		}
		if (longitud == 2) {
			this.acceleration.add(0, fuerzaSaltoMedio);
			this.tocoSuelo = false;
		}
		if (longitud == 3) {
			this.acceleration.add(0, fuerzaSaltoGrande);
			this.tocoSuelo = false;
		}
	}

	@Override
	public Vector2 preventOverlap(Element other) {

		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();

		// initial test to improve performance
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return null;

		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

		if (!polygonOverlap)
			return null;

		this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);

		if (Math.abs(mtv.normal.x) > Math.abs(mtv.normal.y)) {
			this.velocity.x = 0;
		}
		return mtv.normal;
	}

}
