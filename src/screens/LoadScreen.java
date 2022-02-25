package screens;

import game.Demo;
import managers.ResourceManager;

public class LoadScreen extends BScreen {
	public LoadScreen(Demo game) {

		super(game);

		ResourceManager.loadAllResources();

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if (ResourceManager.update()) {
			ResourceManager.botones();
			game.setScreen(new TitleScreen(game));
		}
	}

}
