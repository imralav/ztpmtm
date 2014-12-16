package pl.edu.pb.wi.ztpmtm.game.logic;

import java.util.Calendar;

import pl.edu.pb.wi.ztpmtm.game.GameDifficulty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game {
	private static final float MAX_STEP_DURATION = 1f;
	private static final int UPDATES_IN_SECOND = 60;
	private static final float STEP_DURATION = 1f / ((float) UPDATES_IN_SECOND);
	private static final String SEPARATOR = ":";
	private static final int FIRST_DOUBLE_DIGIT_NUMBER = 10;
	public static final float PPM = 100f;

	private static Game CURRENT_GAME;

	private Calendar totalTime;
	private float timePassed = 0f;
	private int stepsPassed = 0;
	private float pointsAmount = 0f;
	
	//just b2d things...
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera b2dCamera;
	
	private void initiateB2D() {
		world = new World(new Vector2(0f,-9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();		
		b2dCamera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		
		//adding few bodies for testing
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		bdef.position.set(Gdx.graphics.getWidth() / 2f / PPM, Gdx.graphics.getHeight() / 2f / PPM);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape box = new PolygonShape();
		box.setAsBox(100f / PPM, 5f / PPM);
		fdef.shape = box;
		fdef.density = 1f;
		fdef.restitution = 0f;
		fdef.friction = 1f;
		
		Body paddle = world.createBody(bdef);
		paddle.createFixture(fdef);
		
		bdef.type = BodyType.DynamicBody;
		bdef.position.y += 200f / PPM;
		
		box.setAsBox(10f/PPM, 10f/PPM);
		
		fdef.restitution = 1f;
		
		Body playa = world.createBody(bdef);
		playa.createFixture(fdef);
	}

	private Game(GameDifficulty gameDifficulty) {
		totalTime = Calendar.getInstance();
		totalTime.set(0, 0, 0, 0, 0, 0);
		// TODO create Box2D world, apply difficulty effect
		initiateB2D();
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
			world.step(STEP_DURATION, 6, 2);
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
		debugRenderer.render(world, b2dCamera.combined);
	}
}
