package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class ControlesScreen extends BScreen {
	private Texture background;
	private BitmapFont controlesFuente;
	private LabelStyle controlesStyle;

	public ControlesScreen(Demo game) {
		super(game);
		uiStage = new Stage();
		background = new Texture("ui/fondoInicio.png");
		
		ponerLetra();
		Label opcionesLbl = new Label("Mover a la izquierda: <- , A\n"
				+ "Mover a la derecha: -> , D\n"
				+ "Salto pequeño: C\n"
				+ "Salto Mediano: V\n"
				+ "Salto Grande: B", ResourceManager.consejoStyle);
		opcionesLbl.setPosition(Parametros.getAnchoPantalla() * 1 / 3, Parametros.getAltoPantalla()  /2);
		uiStage.addActor(opcionesLbl);
		
		TextButton volver = new TextButton("Volver", ResourceManager.getBoton("ui/rojo.jpg"));
		volver.setPosition(Parametros.getAnchoPantalla() / 20, Parametros.getAltoPantalla() / 20);
		volver.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new TitleScreen(game));
			return false;
		});
		uiStage.addActor(volver);
	}

	private void ponerLetra() {
		// estilo para botones
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("sans.ttf"));
		FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

		ftfp.size = 45;
		ftfp.color = Color.WHITE;
		ftfp.borderColor = Color.BLACK;

		controlesFuente = ftfg.generateFont(ftfp);
		controlesStyle = new LabelStyle();
		controlesStyle.font = controlesFuente;
		
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		uiStage.act();
		uiStage.getBatch().begin();
		uiStage.getBatch().draw(background, 0, 0, Parametros.getAnchoPantalla(), Parametros.getAltoPantalla());
		uiStage.getBatch().end();
		uiStage.draw();

	}

}
