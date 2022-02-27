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

	private Label opcionesLbl;
	private Label musicaLbl;
	private Label volumenLbl;
	private Label nVolumen;
	private Label consejosLbl;
	private LabelStyle settingsStyle;
	private TextButtonStyle musicaTBS;
	private TextButtonStyle consejosTBS;
	private TextButtonStyle masTBS;
	private TextButtonStyle menosTBS;
	private TextButton musicaTB;
	private TextButton consejosTB;
	private TextButton masTB;
	private TextButton menosTB;
	private TextButton volver;
	private Texture background;
	private String vol;

	public SettingsScreen(Demo game) {
		super(game);
		this.background = new Texture("ui/fondoInicio.png");
		settingsStyle = new LabelStyle(ResourceManager.fuentePropia, Color.WHITE);
		uiStage = new Stage();
		opcionesLbl = new Label("Opciones", settingsStyle);
		opcionesLbl.setPosition(Parametros.getAnchoPantalla() * 2 / 5, Parametros.getAltoPantalla() * 4 / 5);
		musicaLbl = new Label("Música: ", settingsStyle);
		musicaLbl.setPosition(Parametros.getAnchoPantalla() * 1 / 4, Parametros.getAltoPantalla() * 3 / 5);
		volumenLbl = new Label("Volumen: ", settingsStyle);
		volumenLbl.setPosition(Parametros.getAnchoPantalla() * 1 / 4, Parametros.getAltoPantalla() * 2 / 5);
		nVolumen = new Label(String.valueOf(Parametros.volumen).substring(0, 3), settingsStyle);
		nVolumen.setPosition(Parametros.getAnchoPantalla() * 5 / 9, Parametros.getAltoPantalla() * 2 / 5);
		consejosLbl = new Label("Consejos: ", settingsStyle);
		consejosLbl.setPosition(Parametros.getAnchoPantalla() * 1 / 5, Parametros.getAltoPantalla() * 1 / 5);

		ponerBotonMusica();
		ponerBotonesVolumen();
		ponerBotonConsejo();
		ponerBotonVolver();
		uiStage.addActor(opcionesLbl);
		uiStage.addActor(musicaLbl);
		uiStage.addActor(volumenLbl);
		uiStage.addActor(nVolumen);
		uiStage.addActor(masTB);
		uiStage.addActor(menosTB);
		uiStage.addActor(musicaTB);
		uiStage.addActor(consejosLbl);
		uiStage.addActor(consejosTB);
		uiStage.addActor(volver);
	}

	private void ponerBotonesVolumen() {
		masTBS = new TextButtonStyle();
		Texture masBtn = ResourceManager.getTexture("ui/mas.png");
		NinePatch masPatchUp = new NinePatch(masBtn);
		masTBS.up = new NinePatchDrawable(masPatchUp);
		masTBS.font = ResourceManager.fuentePropia;
		masTB = new TextButton("", masTBS);
		masTB.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			Parametros.volumen += 0.1f;
			vol = String.valueOf(Parametros.volumen).substring(0, 3);
			Parametros.volumen = Float.parseFloat(vol);
			if (Parametros.volumen > 1)
				Parametros.volumen = 1;
			return false;
		});
		masTB.setPosition(Parametros.getAnchoPantalla() * 9 / 14, Parametros.getAltoPantalla() * 2 / 5);

		menosTBS = new TextButtonStyle();
		Texture menosBtn = ResourceManager.getTexture("ui/menos.png");
		NinePatch menosPatchUp = new NinePatch(menosBtn);
		menosTBS.up = new NinePatchDrawable(menosPatchUp);
		menosTBS.font = ResourceManager.fuentePropia;
		menosTB = new TextButton("", menosTBS);
		menosTB.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			if (Parametros.volumen >= 0.1f)
				Parametros.volumen -= 0.1f;
			vol = String.valueOf(Parametros.volumen).substring(0, 3);
			Parametros.volumen = Float.parseFloat(vol);

			if (Parametros.volumen < 0.1f)
				Parametros.volumen = 0.01f;
			return false;
		});
		menosTB.setPosition(Parametros.getAnchoPantalla() * 7 / 14, Parametros.getAltoPantalla() * 2 / 5);

	}

	private void ponerBotonConsejo() {
		consejosTBS = new TextButtonStyle();
		Texture buttonUncheck = ResourceManager.getTexture("ui/uncheck.png");
		Texture buttonCheck = ResourceManager.getTexture("ui/check.png");
		NinePatch buttonPatchUp = new NinePatch(buttonUncheck);
		NinePatch buttonPatchDown = new NinePatch(buttonCheck);
		consejosTBS.up = new NinePatchDrawable(buttonPatchUp);
		consejosTBS.down = new NinePatchDrawable(buttonPatchDown);
		consejosTBS.checked = new NinePatchDrawable(buttonPatchDown);
		consejosTBS.font = ResourceManager.fuentePropia;

		consejosTB = new TextButton("", consejosTBS);
		consejosTB.setChecked(Parametros.consejos);
		consejosTB.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			Parametros.consejos = !consejosTB.isChecked();
			return false;
		});
		consejosTB.setPosition(Parametros.getAnchoPantalla() * 2 / 4, Parametros.getAltoPantalla() * 3 / 21);

	}

	private void ponerBotonVolver() {
		volver = new TextButton("Volver", ResourceManager.getBoton("ui/rojo.jpg"));
		volver.setPosition(Parametros.getAnchoPantalla() / 20, Parametros.getAltoPantalla() / 20);
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
			return false;
		});
		musicaTB.setPosition(Parametros.getAnchoPantalla() * 2 / 4, Parametros.getAltoPantalla() * 6 / 11);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		uiStage.act();
		nVolumen.setText(String.valueOf(Parametros.volumen));
		uiStage.getBatch().begin();
		uiStage.getBatch().draw(background, 0, 0, Parametros.getAnchoPantalla(), Parametros.getAltoPantalla());
		uiStage.getBatch().end();
		uiStage.draw();
	}
}
