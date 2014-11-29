package pl.edu.pb.wi.ztpmtm;

import pl.edu.pb.wi.ztpmtm.managers.ApplicationManager;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.managers.MusicManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

// Wzorzec kreacyjny: singleton. Istnieje jedna instancja słuchacza aplikacji.
public enum Core implements ApplicationListener {
	INSTANCE;

	@Override
	public void create() {
		initiateManagers();
		InterfaceManager.INSTANCE.showFirstScreen();
	}

	private void initiateManagers() {
		for (ApplicationManager manager : Managers.MANAGERS) {
			manager.create();
		}
	}

	@Override
	public void resize(int width, int height) {
		InterfaceManager.INSTANCE.resize(width, height);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(3, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		InterfaceManager.INSTANCE.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void pause() {
		// Metody Androida.
	}

	@Override
	public void resume() {
		// Metody Androida.
	}

	@Override
	public void dispose() {
		disposeOfManagers();
	}

	private void disposeOfManagers() {
		for (ApplicationManager manager : Managers.MANAGERS) {
			AssetsManager.disposeOf(manager);
		}
	}

	public void exit() {
		// Telling InterfaceManager to hide screen and close application:
		InterfaceManager.INSTANCE.setScreen(null);
	}

	private static class Managers { // TODO add new managers
		private final static ApplicationManager[] MANAGERS = { LocalizationManager.INSTANCE,
				AssetsManager.INSTANCE, InterfaceManager.INSTANCE, MusicManager.INSTANCE,
				DialogManager.INSTANCE };
	}

}