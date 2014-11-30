package pl.edu.pb.wi.ztpmtm.game.logic;

import java.util.Calendar;

import pl.edu.pb.wi.ztpmtm.game.GameDifficulty;

public class Game {
	private static final float MAX_STEP_DURATION = 1f;
	private static final int UPDATES_IN_SECOND = 60;
	private static final float STEP_DURATION = 1f / ((float) UPDATES_IN_SECOND);
	private static final String SEPARATOR = ":";
	private static final int FIRST_DOUBLE_DIGIT_NUMBER = 10;

	private static Game CURRENT_GAME;

	private Calendar totalTime;
	private float timePassed = 0f;
	private int stepsPassed = 0;
	private float pointsAmount = 0f;

	private Game(GameDifficulty gameDifficulty) {
		totalTime = Calendar.getInstance();
		totalTime.set(0, 0, 0, 0, 0, 0);
		// TODO create Box2D world, apply difficulty effect
	}

	public static void prepareGame(GameDifficulty gameDifficulty) {
		CURRENT_GAME = new Game(gameDifficulty);
	}

	public static Game getCurrentGame() {
		return CURRENT_GAME;
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

	private String toDoubleDigitNumber(int number) {
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
			// TODO update Box2D world with STEP_DURATION
		}
	}

	private void updateCalendar(float delta) {
		if (++stepsPassed >= UPDATES_IN_SECOND) {
			stepsPassed -= UPDATES_IN_SECOND;
			totalTime.add(Calendar.SECOND, 1);
		}
	}

	public void render() {
		// TODO render background, then sprites
	}
}
