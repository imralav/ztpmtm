package pl.edu.pb.wi.ztpmtm.gui.screens;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.utilities.InterfaceUtilities;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.Background;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.BackgroundStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class AbstractApplicationScreen implements ApplicationScreen {
	protected final static float STAGE_FADING_TIME = 0.25f;

	private final Stage stage;
	private Window window;
	private Background background;

	protected AbstractApplicationScreen() {
		stage = new Stage(new ScreenViewport(), InterfaceManager.INSTANCE.getStageBatch());
	}

	protected abstract void createWidgets();

	protected void createWindow(Text title, WindowStyle style) {
		window = WidgetFactory.createWindow(title, style);
		stage.addActor(window);
	}

	protected void prepareWindow() {
		window.pack();
		InterfaceUtilities.centerWindow(window);
	}

	protected Window getWindow() {
		return window;
	}

	protected void createBackground() {
		background = WidgetFactory.createBackground(BackgroundStyle.DEFAULT);
		if (stage != null) {
			stage.addActor(background);
			// Drawing the background below all other widgets:
			background.setZIndex(0);
		}
	}

	@Override
	public void resize(final int width, final int height) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				// Order is important - stage should be updated AFTER the window.
				updateWindowPosition(width, height);
				updateStageViewport(width, height);
			}

		});
	}

	private void updateWindowPosition(final int width, final int height) {
		if (window != null) {
			InterfaceUtilities.updateWindowPosition(window, stage.getWidth(), stage.getHeight(),
					(float) width, (float) height);
		}
	}

	private void updateStageViewport(final int width, final int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				addFadingInAction();
			}
		});
	}

	/** Fades in the screen, gives input focus back to the stage. */
	private void addFadingInAction() {
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(STAGE_FADING_TIME),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						Gdx.input.setInputProcessor(stage);
					}
				})));
	}

	@Override
	public void hide() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				clearInputProcessor();
				addFadingOutAction();
			}

		});
	}

	private void clearInputProcessor() {
		Gdx.input.setInputProcessor(null);
	}

	private void addFadingOutAction() {
		stage.addAction(Actions.sequence(Actions.delay
		// Hiding all currently shown dialogs - if a dialog is actually hidden, scene fade out is delayed:
				(DialogManager.INSTANCE.hideAllDialogs() ? DialogManager.DEFAULT_DIALOG_FADING_TIME : 0f),
				Actions.fadeOut(STAGE_FADING_TIME), Actions.run(new Runnable() {
					@Override
					public void run() {
						InterfaceManager.INSTANCE.showScheduledScreen();
					}
				})));
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public Stage getStage() {
		return stage;
	}

	@Override
	public Background getBackground() {
		return background;
	}

}