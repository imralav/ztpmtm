package pl.edu.pb.wi.ztpmtm.managers.gui;

import pl.edu.pb.wi.ztpmtm.gui.screens.ApplicationScreen;
import pl.edu.pb.wi.ztpmtm.gui.screens.impl.LoadingScreen;
import pl.edu.pb.wi.ztpmtm.gui.screens.impl.MenuScreen;
import pl.edu.pb.wi.ztpmtm.managers.ApplicationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public enum InterfaceManager implements ApplicationManager {
	INSTANCE;

	private Skin interfaceStyle;
	private SpriteBatch stageBatch;
	private ApplicationScreen currentScreen, scheduledScreen;
	private boolean shouldReload;

	@Override
	public void create() {
		assignInterfaceStyle();
		createStageBatch();
		initiateScreens();
	}

	private void assignInterfaceStyle() {
		interfaceStyle = AssetsManager.INSTANCE.loadInterfaceSkin();
	}

	private void createStageBatch() {
		stageBatch = new SpriteBatch();
	}

	private void initiateScreens() {
		LoadingScreen.initiateInstance();
		MenuScreen.initiateInstance();
		// LobbyScreen.initiateInstance();
		// TODO initiate rest of screens
	}

	public void processPostLoadingActions() {
		createMissingScreens();
		setScreen(MenuScreen.getInstance());
	}

	private void createMissingScreens() {
		MenuScreen.getInstance().create();
		// LobbyScreen.getInstance().create();
		// TODO create rest of the screens
	}

	public void showFirstScreen() {
		LoadingScreen.getInstance().create();
		setScreen(LoadingScreen.getInstance());
	}

	public void render(float delta) {
		if (currentScreen != null) {
			currentScreen.render(delta);
		}
	}

	public void resize(int width, int height) {
		DialogManager.INSTANCE.resize(width, height);
		if (currentScreen != null) {
			currentScreen.resize(width, height);
		}
	}

	public void setScreen(ApplicationScreen nextScreen) {
		if (currentScreen == null) {
			changeCurrentScreenTo(nextScreen);
		} else if (currentScreen == nextScreen) {
			return;
		} else {
			scheduledScreen = nextScreen;
			currentScreen.hide();
		}
	}

	public void showScheduledScreen() {
		if (scheduledScreen != null) {
			changeCurrentScreenTo(scheduledScreen);
			scheduledScreen = null;
		} else {
			if (shouldReload) {
				reloadCurrentScreens();
			} else {
				Gdx.app.exit();
			}
		}
	}

	private void changeCurrentScreenTo(ApplicationScreen nextScreen) {
		if (nextScreen != null) {
			updateNewScreenBackground(nextScreen);

			currentScreen = nextScreen;
			currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			currentScreen.show();
		}
	}

	private void updateNewScreenBackground(ApplicationScreen nextScreen) {
		if (currentScreen != null) {
			if (nextScreen.getBackground() != null && currentScreen.getBackground() != null) {
				nextScreen.getBackground().setBackgroundOffset(
						currentScreen.getBackground().getBackgroundOffset());
			}
		}
	}

	public boolean isScreenShown() {
		return currentScreen != null;
	}

	public Skin getInterfaceStyle() {
		return interfaceStyle;
	}

	public SpriteBatch getStageBatch() {
		return stageBatch;
	}

	public ApplicationScreen getCurrentScreen() {
		return currentScreen;
	}

	public void reloadScreens() {
		shouldReload = true;
		setScreen(null);
	}

	private void reloadCurrentScreens() {
		shouldReload = false;
		recreateScreens();
		changeCurrentScreenTo(getNewInstanceOfCurrentScreen());
	}

	private ApplicationScreen getNewInstanceOfCurrentScreen() {
		if (currentScreen instanceof MenuScreen) {
			return MenuScreen.getInstance();
		}
		// // Should never happen:
		return LoadingScreen.getInstance();
	}

	private void recreateScreens() {
		MenuScreen.initiateInstance();
		MenuScreen.getInstance().create();
		// LobbyScreen.initiateInstance();
		// LobbyScreen.getInstance().create();
		// TODO recreate other screens
	}

	@Override
	public void dispose() {
		disposeOfScreens();
		disposeOfResources();
	}

	private void disposeOfScreens() {
		LoadingScreen.destroyInstance();
		MenuScreen.destroyInstance();
		// LobbyScreen.destroyInstance();
		// TODO dispose of the rest of the screens
	}

	private void disposeOfResources() {
		AssetsManager.disposeOf(interfaceStyle);
		AssetsManager.disposeOf(stageBatch);
	}

}
