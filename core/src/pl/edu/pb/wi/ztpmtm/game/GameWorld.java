package pl.edu.pb.wi.ztpmtm.game;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.Platform;
import pl.edu.pb.wi.ztpmtm.entity.Player;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.collisions.Bits;
import pl.edu.pb.wi.ztpmtm.game.logic.collisions.CollisionFilter;
import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameWorld {
	private static final float PLATFORM_DISTANCE = 150f / Game.PPM;
	private static final float PLATFORM_OFFSET = 50f / Game.PPM;

	private float platformForceChangeTime = 0;

	private World world;

	private Array<B2DEntity> platforms;
	private Array<B2DEntity> platformsToDispose;
	private boolean shouldCreatePlatform = false;
	private boolean shouldUpdatePlatformForce = false;
	private TextureAtlas platformAtlas;
	private static final float INITIAL_PLATFORM_FORCE = -1f;
	private static final float PLATFORM_FORCE_CHANGE_INTERVAL = 1f / 2f;
	private float platformForce = INITIAL_PLATFORM_FORCE;

	private Player player;

	private Body screenBottomSensor;
	private double elapsedTime = 0;

	public GameWorld() {
		initiateB2D();
	}

	private void initiateB2D() {
		world = new World(new Vector2(0f, -9.8f), true);
		prepareWorld();
		player = new Player(world);
		setupContactListener();
	}

	private void prepareWorld() {
		platforms = new Array<B2DEntity>();
		platformsToDispose = new Array<B2DEntity>();
		createPlatforms();
		createWorldBoundSensors();
	}

	private void createWorldBoundSensors() {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2(Game.getCurrentGame().getViewData().getWidth() / 2f, 0));
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(Game.getCurrentGame().getViewData().getWidth() / 2f, 1f / Game.PPM);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Bits.WORLD_BOUND_SENSOR_CATEGORY_BIT;
		fixtureDef.filter.maskBits = Bits.PLATFORM_CATEGORY_BIT | Bits.PLAYER_CATEGORY_BIT;
		screenBottomSensor = world.createBody(bodyDef);
		screenBottomSensor.createFixture(fixtureDef);
	}

	private void createPlatforms() {
		platformAtlas = AssetsManager.INSTANCE.getAsset(Asset.PLATFORMS, TextureAtlas.class);
		for (float platformY = PLATFORM_OFFSET; platformY <= Game.getCurrentGame().getViewData().getHeight()
				+ PLATFORM_OFFSET; platformY += PLATFORM_DISTANCE) {
			addPlatform(platformY);
		}
	}

	private void addPlatform(final float platformY) {
		final Platform platform = new Platform(platformY);
		platform.createBody(world);
		platform.setRegion(platformAtlas.findRegion(platform.getInteractionStrategy().getTextureName()));
		platform.updateFallingSpeed(platformForce);
		platforms.add(platform);
	}

	public void update(final float delta) {
		platformForceChangeTime += delta;
		elapsedTime += delta;
		if (platformForceChangeTime >= PLATFORM_FORCE_CHANGE_INTERVAL) {
			platformForceChangeTime %= PLATFORM_FORCE_CHANGE_INTERVAL;
			platformForce = INITIAL_PLATFORM_FORCE - (float) Math.sqrt(elapsedTime / 1000f);
			shouldUpdatePlatformForce = true;
		}
		world.step(Game.STEP_DURATION, 6, 2);
		addNewPlatforms();
		removePlatforms();
		updatePlatforms(delta);
		player.update(delta);
	}

	/**
	 * Updates data connected to platforms:
	 * 1. general Entity#update()
	 * 2. update of platforms' falling speed
	 * 3. update of platforms' collision filters
	 * @param delta time between since last update
	 */
	private void updatePlatforms(final float delta) {
		int platformsAbove = 0;
		int platformsBelow = 0;
		for (final B2DEntity entity : platforms) {
			entity.update(delta);
			final Platform platform = (Platform) entity;
			if (shouldUpdatePlatformForce) {
				platform.updateFallingSpeed(platformForce);
			}
			// is platform below the player?
			if (platform.getDrawData().y + platform.getSprite().getRegion().getRegionHeight() < player
					.getSprite().getY()) {
				// apply new filter to all platform fixtures
				for (final Fixture fixture : platform.getBody().getFixtureList()) {
					fixture.setFilterData(CollisionFilter.PLATFORM_BELOW_PLAYER.getFilter());
				}
				platformsBelow++;
			} else {
				// apply new filter to all platform fixtures
				for (final Fixture fixture : platform.getBody().getFixtureList()) {
					fixture.setFilterData(CollisionFilter.PLATFORM_ABOVE_PLAYER.getFilter());
				}
				platformsAbove++;
			}
		}
		// Gdx.app.log("Debug", "platforms below: " + platformsBelow + " platforms above: " + platformsAbove);
		shouldUpdatePlatformForce = false;
	}

	private void removePlatforms() {
		for (final B2DEntity entity : platformsToDispose) {
			entity.dispose(world);
		}
		platformsToDispose.clear();
	}

	private void addNewPlatforms() {
		if (shouldCreatePlatform) {
			addPlatform(Game.getCurrentGame().getViewData().getHeight() + PLATFORM_DISTANCE);
			shouldCreatePlatform = false;
		}
	}

	private void setupContactListener() {
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(final Contact contact, final Manifold oldManifold) {
			}

			@Override
			public void postSolve(final Contact contact, final ContactImpulse impulse) {
			}

			@Override
			public void endContact(final Contact contact) {
			}

			@Override
			public void beginContact(final Contact contact) {
				Platform platform;
				if (contact.getFixtureA().getUserData() instanceof Platform) {
					platform = (Platform) contact.getFixtureA().getUserData();
					if (contact.getFixtureB().getUserData() == null) {
						platformsToDispose.add(platform);
						platforms.removeValue(platform, true);
						shouldCreatePlatform = true;
					}
					Gdx.app.log("Debug", "Platform touching");
				}
				if (contact.getFixtureB().getUserData() instanceof Platform) {
					platform = (Platform) contact.getFixtureB().getUserData();
					if (contact.getFixtureA().getUserData() == null) {
						platformsToDispose.add(platform);
						platforms.removeValue(platform, true);
						shouldCreatePlatform = true;
					}
					Gdx.app.log("Debug", "Platform touching");
				}
			}

		});
	}

	/**
	 * @return the platforms
	 */
	public Array<B2DEntity> getPlatforms() {
		return platforms;
	}

	/**
	 * @return the platformsToDispose
	 */
	public Array<B2DEntity> getPlatformsToDispose() {
		return platformsToDispose;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}
}
