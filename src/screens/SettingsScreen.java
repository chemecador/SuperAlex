package screens;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class SettingsScreen extends BScreen {

	private Label lbl;
	private Label musica;
	private Label volumen;
	private LabelStyle settingsStyle;
	private TextButtonStyle musicaTBS;
	private TextButton musicaTB;
	private TextButton volver;
	private Texture background;
	
	public SettingsScreen(Demo game) {
		super(game);
		this.background = new Texture("ui/fondoinicio.png");
		settingsStyle = new LabelStyle(ResourceManager.fuentePropia, Color.WHITE);
		uiStage = new Stage();
		lbl = new Label("Opciones", settingsStyle);
		lbl.setPosition(Parametros.getAnchoPantalla() * 2 / 5, Parametros.getAltoPantalla() * 4 / 5);
		musica = new Label("Música: ", settingsStyle);
		musica.setPosition(Parametros.getAnchoPantalla() * 1 / 4, Parametros.getAltoPantalla() * 3 / 5);
		volumen = new Label("Volumen: ", settingsStyle);
		volumen.setPosition(Parametros.getAnchoPantalla() * 1 / 4, Parametros.getAltoPantalla() * 2 / 5);

		ponerBotonMusica();
		ponerBotonVolver();
		uiStage.addActor(lbl);
		uiStage.addActor(musica);
		uiStage.addActor(volumen);
		uiStage.addActor(lbl);
		uiStage.addActor(musicaTB);
		uiStage.addActor(volver);
	}

	private void ponerBotonVolver() {
		volver = new TextButton("Volver", ResourceManager.textButtonStyle);
		volver.setPosition(Parametros.getAnchoPantalla() /20, Parametros.getAltoPantalla() /20);
		volver.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new TitleScreen(game));
			return false;
		});
		
	}

	private void ponerBotonMusica() {
		musicaTBS = new TextButtonStyle();
		Texture buttonUncheck = ResourceManager.getTexture("ui/uncheck.png");
		Texture buttonCheck = ResourceManager.getTexture("ui/check.png");
		NinePatch buttonPatchUp = new NinePatch(buttonUncheck);
		NinePatch buttonPatchDown = new NinePatch(buttonCheck);
		musicaTBS.up = new NinePatchDrawable(buttonPatchUp);
		musicaTBS.down = new NinePatchDrawable(buttonPatchDown);
		musicaTBS.checked = new NinePatchDrawable(buttonPatchDown);
		musicaTBS.font = ResourceManager.fuentePropia;
		
		musicaTB = new TextButton("", musicaTBS);
		musicaTB.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			Parametros.musica = !musicaTB.isChecked();
			if (Parametros.musica) System.out.println("SIII");
			else System.out.println("NOOOO");
			return false;
		});
		musicaTB.setPosition(Parametros.getAnchoPantalla() * 2 / 4, Parametros.getAltoPantalla() * 6 / 11);
		
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
