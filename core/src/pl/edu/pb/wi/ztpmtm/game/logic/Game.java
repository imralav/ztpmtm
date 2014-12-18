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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Game {
	private static final int INITIAL_FALLING_SPEED = 20;
	public static final float PPM = 100f;
	private static final float PLATFORM_DISTANCE = 100f / PPM;
	private static final float PLATFORM_OFFSET = 50f / PPM;
	private static final float MAX_STEP_DURATION = 1f;
	private static final int UPDATES_IN_SECOND = 60;
	private static final float STEP_DURATION = 1f / UPDATES_IN_SECOND;
	private static final String SEPARATOR = ":";
	private static final int FIRST_DOUBLE_DIGIT_NUMBER = 10;

	private static Game CURRENT_GAME;

	private final Calendar totalTime;
	private float timePassed = 0f;
	private float timeElapsed = 0f;
	private int stepsPassed = 0;
	private final float pointsAmount = 0f;

	// just b2d things...
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera b2dCamera;
	private ViewData viewData;
	private Body screenBottomSensor;

	private Array<B2DEntity> platforms;
	private Array<B2DEntity> platformsToDispose;
	private int amountOfPlatformsToCreate = 0;

	public ViewData getViewData() {
		return viewData;
	}

	private void initiateB2D() {
		world = new World(new Vector2(0f, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		setupContactListener();
		prepareWorld();
	}

	private void setupContactListener() {
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(final Contact contact, final Manifold oldManifold) {
				// TODO Auto-generated method stub
			}

			@Override
			public void postSolve(final Contact contact, final ContactImpulse impulse) {
				// TODO Auto-generated method stub
			}

			@Override
			public void endContact(final Contact contact) {
				// TODO wywołać InteractionStrategy#endContact z aktualnej platformy
			}

			@Override
			public void beginContact(final Contact contact) {
				Platform platform;
				if (contact.getFixtureA().getUserData() instanceof Platform) {
					platform = (Platform) contact.getFixtureA().getUserData();
					if (contact.getFixtureB().getUserData() == null) {
						platformsToDispose.add(platform);
						platforms.removeValue(platform, true);
						amountOfPlatformsToCreate++;
					}
					Gdx.app.log("Debug", "Platform touching");
				}
				if (contact.getFixtureB().getUserData() instanceof Platform) {
					platform = (Platform) contact.getFixtureB().getUserData();
					if (contact.getFixtureA().getUserData() == null) {
						platformsToDispose.add(platform);
						platforms.removeValue(platform, true);
						amountOfPlatformsToCreate++;
					}
					Gdx.app.log("Debug", "Platform touching");
				}
			}

		});
	}

	private void prepareWorld() {
		viewData = ViewData.prepare(Game.PPM);
		// tworzenie platform TODO
		platforms = new Array<B2DEntity>();
		platformsToDispose = new Array<B2DEntity>();
		createPlatforms();
		createWorldBoundSensors();
	}

	private void createWorldBoundSensors() {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2(getViewData().getWidth() / 2f, 0));
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(getViewData().getWidth() / 2f, 1f / Game.PPM);
		fixtureDef.shape = shape;
		screenBottomSensor = world.createBody(bodyDef);
		screenBottomSensor.createFixture(fixtureDef);
	}

	private void createPlatforms() {
		for (float platformY = PLATFORM_OFFSET; platformY <= viewData.getHeight() + PLATFORM_OFFSET; platformY +=
				PLATFORM_DISTANCE) {
			addPlatform(platformY);
		}
	}

	private void addPlatform(final float platformY) {
		final B2DEntity platform = new Platform(platformY);
		platform.createBody(world);
		platforms.add(platform);
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
		timeElapsed += delta;
		delta %= MAX_STEP_DURATION;
		timePassed += delta;
		while (timePassed >= STEP_DURATION) {
			timePassed -= STEP_DURATION;
			updateCalendar(delta);
			// TODO update Box2D world with STEP_DURATION
			world.step(STEP_DURATION, 6, 2);
			addNewPlatforms();
			removePlatforms();
			updatePlatformsSpeed(-(2f * (float) Math.sqrt(timeElapsed) + INITIAL_FALLING_SPEED) / PPM);
		}
	}

	private void removePlatforms() {
		for (final B2DEntity entity : platformsToDispose) {
			entity.dispose(world);
		}
		platformsToDispose.clear();
	}

	private void addNewPlatforms() {
		for (int i = 0; i < amountOfPlatformsToCreate; i++) {
			addPlatform(viewData.getHeight() + PLATFORM_OFFSET);
		}
		amountOfPlatformsToCreate = 0;
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
		renderPlatforms(batch);
	}

	private void updatePlatformsSpeed(final float yVelocity) {
		for (final B2DEntity entity : platforms) {
			entity.changeFallingSpeed(yVelocity);
		}
	}

	private void renderPlatforms(final Batch batch) {
		for (final B2DEntity entity : platforms) {
			entity.render(batch);
		}
	}
}
