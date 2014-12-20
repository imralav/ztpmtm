package pl.edu.pb.wi.ztpmtm.game.logic.interactionStrategies;

import pl.edu.pb.wi.ztpmtm.entity.Player;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public enum InteractionStrategy {
	ICE {
		@Override
		public void setBodyData(final BodyCreator bodyCreator, final float initialY) {
			super.setBodyData(bodyCreator, initialY);
			final FixtureDef platformFixture = bodyCreator.getFixtureDefForName(PLATFORM_FIXTURE_NAME);
			platformFixture.friction = 0f;
		}
	},
	MUD {
		@Override
		public void setBodyData(final BodyCreator bodyCreator, final float initialY) {
			super.setBodyData(bodyCreator, initialY);
			final FixtureDef platformFixture = bodyCreator.getFixtureDefForName(PLATFORM_FIXTURE_NAME);
			platformFixture.restitution = 0f;
			platformFixture.friction = 0.9f;
		}

		@Override
		public void beginCollision(final Player player) {
			// TODO: status SLOWED na playera
		}

		@Override
		public void endCollision(final Player player) {
			// TODO: zdejmuje SLOWED
		}
	},
	RUBBER {
		@Override
		public void setBodyData(final BodyCreator bodyCreator, final float initialY) {
			super.setBodyData(bodyCreator, initialY);
			bodyCreator.getFixtureDefForName(PLATFORM_FIXTURE_NAME).restitution = 1f;
		}

		@Override
		public void beginCollision(final Player player) {
			// TODO wybij w górę
		}
	},
	GRASS,
	STONE {
		// @Override
		// public void setBodyData(final BodyCreator bodyCreator, final float initialY) {
		// super.setBodyData(bodyCreator, initialY);
		// bodyCreator.setType(BodyType.DynamicBody);
		// }

		@Override
		public void beginCollision(final Player player) {
			// TODO stone opada przy kolizji
		}
	},
	SHROOM {
		@Override
		public void beginCollision(final Player player) {
			// TODO ustawia status INVERT_INPUT
		}

		@Override
		public void endCollision(final Player player) {
			// TODO zdejmuje status INVERT_INPUT
		}
	},
	GHOST;

	private static final String PLATFORM_FIXTURE_NAME = "platform";
	private static final String SENSOR_FIXTURE_NAME = "sensor";
	private static final float DEFAULT_HEIGHT = 35f / Game.PPM;
	private static final float DEFAULT_WIDTH = 50f / Game.PPM; // TODO: change according to current points
	private static final float SENSOR_DEFAULT_HEIGHT = 5f / Game.PPM;

	private FixtureDef prepareDefaultFixture(final FixtureDef fixtureDef) {
		fixtureDef.density = 1f;
		fixtureDef.restitution = 0.2f;
		fixtureDef.friction = 0.5f;
		return fixtureDef;
	};

	public void beginCollision(final Player player) {
	}

	public void endCollision(final Player player) {
	}

	public static InteractionStrategy getRandomInteraction() {
		return values()[MathUtils.random(values().length - 1)];
	}

	public void setBodyData(final BodyCreator bodyCreator, final float initialY) {
		final float currentPlatformWidth = DEFAULT_WIDTH;
		final float x =
				MathUtils.random(currentPlatformWidth / 2f, Gdx.graphics.getWidth() / Game.PPM
						- currentPlatformWidth / 2f); // TODO: losuj ixa
		bodyCreator.setPosition(new Vector2(x, initialY));
		bodyCreator.setFixedRotation(true);
		bodyCreator.getBodyDef().gravityScale = 0f;
		final FixtureDef fixtureDef = bodyCreator.createFixtureDef(PLATFORM_FIXTURE_NAME);
		prepareDefaultFixture(fixtureDef);
		final PolygonShape platformShape = new PolygonShape();
		// TODO: set shape according to current points instead of default
		platformShape.setAsBox(currentPlatformWidth, DEFAULT_HEIGHT / 2f);
		fixtureDef.shape = platformShape;

		final FixtureDef topSensor = bodyCreator.createFixtureDef(SENSOR_FIXTURE_NAME);
		topSensor.isSensor = true;
		final PolygonShape sensorShape = new PolygonShape();
		// TODO: set sensor shape
		sensorShape.setAsBox(currentPlatformWidth, SENSOR_DEFAULT_HEIGHT / 2f, new Vector2(0,
				DEFAULT_HEIGHT / 2f), 0);
		topSensor.shape = sensorShape;
	}

}
