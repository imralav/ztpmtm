package pl.edu.pb.wi.ztpmtm.game;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.Platform;
import pl.edu.pb.wi.ztpmtm.entity.Player;
import pl.edu.pb.wi.ztpmtm.entity.UserData;
import pl.edu.pb.wi.ztpmtm.entity.WorldBound;
import pl.edu.pb.wi.ztpmtm.entity.WorldBound.WorldBoundType;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.collisions.CollisionFilter;
import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
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
	private static final float INITIAL_PLATFORM_FORCE = -10f;
	private static final float PLATFORM_FORCE_CHANGE_INTERVAL = 1f / 2f;
	private final float platformForce = INITIAL_PLATFORM_FORCE;

	private Player player;

	private Array<B2DEntity> worldBounds;

	private double elapsedTime = 0;

	public GameWorld() {
		initiateB2D();
	}

	private void initiateB2D() {
		world = new World(new Vector2(0f, -9.8f), true);
		prepareWorld();
		createPlayer();
		setupContactListener();
	}

	private void createPlayer() {
		player = new Player(world);
		player.createBody(world);
	}

	private void prepareWorld() {
		createPlatforms();
		createWorldBounds();
	}

	private void createWorldBounds() {
		worldBounds = new Array<B2DEntity>();
		for (final WorldBoundType worldBoundType : WorldBoundType.values()) {
			final WorldBound worldBound = new WorldBound(worldBoundType);
			worldBound.createBody(world);
			worldBounds.add(worldBound);
		}
	}

	private void createPlatforms() {
		platforms = new Array<B2DEntity>();
		platformsToDispose = new Array<B2DEntity>();
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
		platform.updateFallingSpeed(platformForce / 10f); // TODO: testowo dzielę na dziesięć by wolniej
		// spadało
		platforms.add(platform);
	}

	public void update(final float delta) {
		platformForceChangeTime += delta;
		elapsedTime += delta;
		// if (platformForceChangeTime >= PLATFORM_FORCE_CHANGE_INTERVAL) {
		// platformForceChangeTime %= PLATFORM_FORCE_CHANGE_INTERVAL;
		// platformForce = INITIAL_PLATFORM_FORCE - (float) Math.sqrt(elapsedTime / 1000f);
		// shouldUpdatePlatformForce = true;
		// }
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
		for (final B2DEntity entity : platforms) {
			entity.update(delta);
			final Platform platform = (Platform) entity;
			if (shouldUpdatePlatformForce) {
				platform.updateFallingSpeed(platformForce);
			}
			updatePlatformCollisionFilter(platform);
			checkPlatformOutOfBounds(platform);
		}
		shouldUpdatePlatformForce = false;
	}

	private void checkPlatformOutOfBounds(final Platform platform) {
		if (platform.getDrawData().y - platform.getSprite().getRegion().getRegionHeight() / 2f < 0f) {
			shouldCreatePlatform = true;
			platformsToDispose.add(platform);
			platforms.removeValue(platform, true);
		}
	}

	private void updatePlatformCollisionFilter(final Platform platform) {
		// is platform below the player?
		if (platform.getDrawData().y + platform.getSprite().getRegion().getRegionHeight() / 2 < player
				.getSprite().getY()) {
			// apply new filter to all platform fixtures
			for (final Fixture fixture : platform.getBody().getFixtureList()) {
				fixture.setFilterData(CollisionFilter.PLATFORM_BELOW_PLAYER.getFilter());
			}
		} else {
			// apply new filter to all platform fixtures
			for (final Fixture fixture : platform.getBody().getFixtureList()) {
				fixture.setFilterData(CollisionFilter.PLATFORM_ABOVE_PLAYER.getFilter());
			}
		}
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
				final UserData userDataA = (UserData) contact.getFixtureA().getUserData();
				final UserData userDataB = (UserData) contact.getFixtureB().getUserData();
				userDataA.endContact(userDataB);
				userDataB.endContact(userDataA);
			}

			@Override
			public void beginContact(final Contact contact) {
				final UserData userDataA = (UserData) contact.getFixtureA().getUserData();
				final UserData userDataB = (UserData) contact.getFixtureB().getUserData();
				userDataA.beginContact(userDataB);
				userDataB.beginContact(userDataA);
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
