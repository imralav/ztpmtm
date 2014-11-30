package pl.edu.pb.wi.ztpmtm.gui.screens.impl;

import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.screens.AbstractApplicationScreen;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.TableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.StandardTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ScrollPaneStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameScreen extends AbstractApplicationScreen {
	private static final String DEFAULT_POINTS_AMOUNT = "0";
	private static final String DEFAULT_TOTAL_TIME = "00:00:00";
	private static final float HUD_HEIGHT = 80f;

	private static GameScreen CURRENT_INSTANCE;

	private Label pointsLabel, timeLabel;

	public static void initiateInstance() {
		destroyInstance();
		CURRENT_INSTANCE = new GameScreen();
	}

	public static void destroyInstance() {
		AssetsManager.disposeOf(CURRENT_INSTANCE);
		CURRENT_INSTANCE = null;
	}

	public static GameScreen getInstance() {
		return CURRENT_INSTANCE;
	}

	private GameScreen() {
		super();
	}

	@Override
	public void create() {
		createWindow(Text.EMPTY, WindowStyle.TRANSPARENT);
		createWidgets();
		prepareWindow();
	}

	@Override
	public void show() {
		pointsLabel.setText(DEFAULT_POINTS_AMOUNT);
		timeLabel.setText(DEFAULT_TOTAL_TIME);
		super.show();
	}

	@Override
	protected void createWidgets() {
		// Bez buildera, bo zaawansowany uklad.
		Table table = new Table();

		table.setFillParent(true);
		table.add(WidgetFactory.createScrollPane(createHudWidgets(), ScrollPaneStyle.DEFAULT))
				.height(HUD_HEIGHT).expandX().fillX().expandY().bottom();

		getWindow().add(table).expand().fill();
	}

	@Override
	protected void prepareWindow() {
		getWindow().setFillParent(true);
		getWindow().pack();
	}

	private Actor createHudWidgets() {
		TableBuilder tableBuilder = new StandardTableBuilder(Padding.PAD_2);

		tableBuilder.append(
				pointsLabel = WidgetFactory.createLabel(DEFAULT_POINTS_AMOUNT, LabelStyle.BIG_BLUE), true,
				false);
		tableBuilder.append(timeLabel = WidgetFactory.createLabel(DEFAULT_TOTAL_TIME, LabelStyle.BIG_BLUE),
				true, false);
		tableBuilder.append(WidgetFactory.createTextButton(Text.EXIT, TextButtonStyle.DEFAULT));
		// TODO button action

		return tableBuilder.build();
	}

	@Override
	public void render(float delta) {
		updateGameData(delta);
		Game.getCurrentGame().render();
		super.render(delta);
	}

	private void updateGameData(float delta) {
		Game currentGame = Game.getCurrentGame();
		currentGame.update(delta);
		pointsLabel.setText(currentGame.getPointsAmountToDisplay());
		timeLabel.setText(currentGame.getTotalGameDuration());
	}
}
