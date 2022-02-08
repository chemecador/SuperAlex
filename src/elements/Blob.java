package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Blob extends Enemigo{

	private int direccion;
	private Animation walk;
	private Animation jump;
	private Element pie;
	private Element cabeza;
	private boolean pisa;
	private boolean cabezazo;
	private GameScreen nivel;
	
	
	public Blob(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.nivel=nivel;
		// TODO Auto-generated constructor stub
		jump=loadFullAnimation("enemies/blob_jump.png", 1, 7, 0.15f, true);
		walk= loadFullAnimation("enemies/blob.png", 2, 1, 0.2f, true);
		
		direccion=-1;
		pie=new Element(0,0,s,this.getWidth()/4 ,this.getHeight()/4);
		pie.setRectangle();
		ajustarPie();
		
		cabeza=new Element(0,0,s,this.getWidth()/4 ,this.getHeight()/4 );
		cabeza.setRectangle();
		ajustarCabeza();
		
		velocidad=50;
		
		
		
	}
	
	
	

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		pisa=false;
		cabezazo=false;
		for(Solid solido:nivel.suelo) {
			if(pie.overlaps(solido)) {
				pisa=true;
			}
			if(cabeza.overlaps(solido)) {
				cabezazo=true;
			}
			
		}
		
		if(!pisa || cabezazo) {
			direccion*=-1;
		}
		
		moveBy(direccion*velocidad*delta,0);
		ajustarPie();
		ajustarCabeza();
		
		
		
		
	}




	private void ajustarPie() {
		// TODO Auto-generated method stub
		if(direccion==-1) {
			pie.setPosition(this.getX(), this.getY()-this.getHeight()/8);
		}else {
			pie.setPosition(this.getX()+this.getWidth()*3/4, this.getY()-this.getHeight()/8);
		}
		
	}
	
	private void ajustarCabeza() {
		// TODO Auto-generated method stub
		if(direccion==-1) {
			cabeza.setPosition(this.getX(), this.getY()+this.getHeight()*7/8);
		}else {
			cabeza.setPosition(this.getX()+this.getWidth()*3/4, this.getY()+this.getHeight()*7/8);
		}
		
	}


	
	
	
}
