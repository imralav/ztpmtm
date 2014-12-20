package pl.edu.pb.wi.ztpmtm.game.logic;

import java.util.Calendar;

import pl.edu.pb.wi.ztpmtm.game.GameDifficulty;
import pl.edu.pb.wi.ztpmtm.game.GameRenderer;
import pl.edu.pb.wi.ztpmtm.game.GameWorld;
import pl.edu.pb.wi.ztpmtm.game.logic.view.ViewData;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Game {
	public static final float PPM = 100f;
	public static final float MIN_PLATFORM_WIDTH = 100f;
	private static final float MAX_STEP_DURATION = 1f;
	private static final int UPDATES_IN_SECOND = 60;
	public static final float STEP_DURATION = 1f / UPDATES_IN_SECOND;
	private static final String SEPARATOR = ":";
	private static final int FIRST_DOUBLE_DIGIT_NUMBER = 10;
	private static final float MAX_PLATFORM_WIDTH = 200;

	private static Game CURRENT_GAME;

	private GameWorld gameWorld;
	private GameRenderer gameRenderer;

	private final Calendar totalTime;
	private float timePassed = 0f;
	private int stepsPassed = 0;
	private final float pointsAmount = 0f;

	private ViewData viewData;

	private Game(final GameDifficulty gameDifficulty) {
		totalTime = Calendar.getInstance();
		totalTime.set(0, 0, 0, 0, 0, 0);
	}

	public static void prepareGame(final GameDifficulty gameDifficulty) {
		CURRENT_GAME = new Game(gameDifficulty);
		CURRENT_GAME.viewData = ViewData.prepare(Game.PPM);
		CURRENT_GAME.gameWorld = new GameWorld();
		CURRENT_GAME.gameRenderer = new GameRenderer(CURRENT_GAME.gameWorld);
	}

	public static Game getCurrentGame() {
		return CURRENT_GAME;
	}

	public ViewData getViewData() {
		return viewData;
	}

	public float getCurrentPlatformWidth() {
		float currentWidth = MAX_PLATFORM_WIDTH - pointsAmount;
		currentWidth = currentWidth < MIN_PLATFORM_WIDTH ? MIN_PLATFORM_WIDTH : currentWidth;
		return currentWidth;
	}

	public float getPointsAmount() {
		return pointsAmount;
	}

	public String getPointsAmountToDisplay() {
		return String.valueOf((int) getPointsAmount());
	}

	public String getTotalGameDuration() {
		return toDoubleDigitNumber(totalTime.get(Calendar.HOUR)) + SEPARATOR
				+ toDoubleDigitNumber(totalTime.get(Calendar.MINUTE)) + SEPARATOR
				+ toDoubleDigitNumber(totalTime.get(Calendar.SECOND));
	}

	private String toDoubleDigitNumber(final int number) {
		if (number < FIRST_DOUBLE_DIGIT_NUMBER) {
			return "0" + number;
		}
		return String.valueOf(number);
	}

	public void update(float delta) {
		delta %= MAX_STEP_DURATION;
		timePassed += delta;
		while (timePassed >= STEP_DURATION) {
			timePassed -= STEP_DURATION;
			updateCalendar(delta);
			gameWorld.update(delta);
		}
	}

	private void updateCalendar(final float delta) {
		if (++stepsPassed >= UPDATES_IN_SECOND) {
			stepsPassed -= UPDATES_IN_SECOND;
			totalTime.add(Calendar.SECOND, 1);
		}
	}

	public void render(final Batch batch) {
		gameRenderer.render(batch);
	}
}
