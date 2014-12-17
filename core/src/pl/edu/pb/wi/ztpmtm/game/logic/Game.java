package pl.edu.pb.wi.ztpmtm.game.logic;

import java.util.Calendar;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.Platform;
import pl.edu.pb.wi.ztpmtm.game.GameDifficulty;
import pl.edu.pb.wi.ztpmtm.game.logic.view.ViewData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Game {
	public static final float PPM = 100f;
	private static final float PLATFORM_DISTANCE = 100f / PPM;
	private static final float MAX_STEP_DURATION = 1f;
	private static final int UPDATES_IN_SECOND = 60;
	private static final float STEP_DURATION = 1f / UPDATES_IN_SECOND;
	private static final String SEPARATOR = ":";
	private static final int FIRST_DOUBLE_DIGIT_NUMBER = 10;

	private static Game CURRENT_GAME;

	private final Calendar totalTime;
	private float timePassed = 0f;
	private int stepsPassed = 0;
	private final float pointsAmount = 0f;

	// just b2d things...
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera b2dCamera;
	private ViewData viewData;

	public ViewData getViewData() {
		return viewData;
	}

	//
	private Array<B2DEntity> platforms;

	private void initiateB2D() {
		world = new World(new Vector2(0f, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);

		prepareWorld();
	}

	private void prepareWorld() {
		viewData = ViewData.prepare(Game.PPM);
		// tworzenie platform TODO
		platforms = new Array<B2DEntity>();
		createPlatforms();
	}

	private void createPlatforms() {
		for (float platformY = PLATFORM_DISTANCE; platformY < viewData.getHeight(); platformY +=
				PLATFORM_DISTANCE) {
			final B2DEntity platform = new Platform(platformY);
			platform.createBody(world);
			platforms.add(platform);
		}
	}

	private Game(final GameDifficulty gameDifficulty) {
		totalTime = Calendar.getInstance();
		totalTime.set(0, 0, 0, 0, 0, 0);
		// TODO create Box2D world, apply difficulty effect
		initiateB2D();
	}

	public static void prepareGame(final GameDifficulty gameDifficulty) {
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
			// TODO update Box2D world with STEP_DURATION
			world.step(STEP_DURATION, 6, 2);
		}
	}

	private void updateCalendar(final float delta) {
		if (++stepsPassed >= UPDATES_IN_SECOND) {
			stepsPassed -= UPDATES_IN_SECOND;
			totalTime.add(Calendar.SECOND, 1);
		}
	}

	public void render(final Batch batch) {
		// TODO render background, then sprites
		debugRenderer.render(world, b2dCamera.combined);
		for (final B2DEntity entity : platforms) {
			entity.render(batch);
		}
	}
}
