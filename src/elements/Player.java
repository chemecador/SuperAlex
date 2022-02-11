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

public class Player extends Element {
	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> quieto;
	private Array<Bala> balas;

//gestión de vectores
	private int totalBalas = 10;
	private int balaActual = 0;

//gestión de tiempos

	public Element pies;
	public boolean tocoSuelo;

	private float walkingSpeed = 600;
	private float fuerzaSalto = 40000;

	public Player(float x, float y, Stage s) {
		super(x, y, s);
		this.maxSpeed = 10000;
		this.deceleration = 2000000;
		tocoSuelo = false;
		frente = loadFullAnimation("player/frente.png", 2, 1, 0.2f, true);
		espalda = loadFullAnimation("player/agachado.png", 1, 1, 0.2f, true);
		drcha = this.loadFullAnimation("player/derecha.png", 2, 1, 0.3f, true);
		izqda = this.loadFullAnimation("player/izquierda.png", 2, 1, 0.3f, true);
		quieto = this.loadFullAnimation("player/depie.png", 1, 1, 0.2f, true);
		this.setPolygon(6);

		pies = new Element(0, 0, s, this.getWidth()/4, this.getHeight() / 10);
		pies.setRectangle();

		balas = new Array<Bala>();
		for (int i = 0; i < this.totalBalas; i++) {
			balas.add(new Bala(0, 0, s));

		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		controles();
		// aplico graviedad
		this.acceleration.add(0, Parametros.gravedad);
		this.applyPhysics(delta);
		colocarPies();
		//System.out.println(this.velocity.y);

	}

	private void controles() {
		boolean quieto = true;

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.setAnimation(izqda);
			this.acceleration.add(-walkingSpeed, 0);
			quieto = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.setAnimation(drcha);
			this.acceleration.add(walkingSpeed, 0);
			quieto = false;
		}
		if (quieto) {

			this.setAnimation(this.quieto);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && tocoSuelo) {
			salta();

		}

	}

	@Override
	public void applyPhysics(float dt) {
		// apply acceleration
       
		  if(tocoSuelo & this.getVelocity().y<0) {
				
				this.acceleration.y=0;
	        }
		
		
		velocity.add( acceleration.x * dt, acceleration.y * dt );
      
        float speed = velocity.len();
        
        // decrease speed (decelerate) when not accelerating
      
        
        if (acceleration.len() == 0)
            speed -= deceleration * dt;

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // update velocity
        /*if (velocity.len() == 0)
            velocity.set(speed, 0);
        else*/
            velocity.setLength(speed);
        // update position according to value stored in velocity vector
        moveBy( velocity.x * dt, velocity.y * dt );
        // reset acceleration
        acceleration.set(0,0);  
		
	}


	public void colocarPies() {
		this.pies.setPosition(this.getX()+this.getWidth()/2-this.getWidth()/8, this.getY());
		
		
	}
	
	private void salta() {
		this.acceleration.add(0,fuerzaSalto);
		this.tocoSuelo=false;
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
