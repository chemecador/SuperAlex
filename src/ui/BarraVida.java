package ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Element;
import game.Parametros;

public class BarraVida extends Element{

	Element fondo;
	public BarraVida(float x, float y, Stage s) {
		super(x, y, s);
		// TODO Auto-generated constructor stub
		this.setEnabled(true);
		fondo=new Element(x,y,s);
		
		
		this.loadFullAnimation("ui/rojo.jpg",1,1,200,true);
		fondo.loadFullAnimation("ui/morado.jpg", 1, 1, 200, true);
		
		this.setPosition(x,y);
		
		this.toFront();
		
		
		
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(animation.getKeyFrame(animationTime), 
		getX(), getY(), getOriginX(), getOriginY(), 
		animation.getKeyFrame(animationTime).getRegionWidth()*Parametros.vida/Parametros.maxVida,
		animation.getKeyFrame(animationTime).getRegionHeight(),
		getScaleX()	, getScaleY(), getRotation());
	}
	
	
	
	
	

}
