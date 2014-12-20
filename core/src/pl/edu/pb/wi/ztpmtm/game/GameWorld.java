package pl.edu.pb.wi.ztpmtm.game;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.Platform;
import pl.edu.pb.wi.ztpmtm.entity.Player;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameWorld {
	private static final float PLATFORM_DISTANCE = 150f / Game.PPM;
	private static final float PLATFORM_OFFSET = 50f / Game.PPM;

	private World world;

	private Array<B2DEntity> platforms;
	private Array<B2DEntity> platformsToDispose;
	private boolean createPlatform = false;
	private TextureAtlas platformAtlas;

	private Player player;

	private Body screenBottomSensor;

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
		platform.initialPush(-1f);
		platforms.add(platform);
	}

	public void update(final float delta) {
		world.step(Game.STEP_DURATION, 6, 2);
		addNewPlatforms();
		removePlatforms();
		updatePlatforms(delta);
		player.update(delta);
	}

	private void updatePlatforms(final float delta) {
		for (final B2DEntity entity : platforms) {
			entity.update(delta);
		}
	}

	private void removePlatforms() {
		for (final B2DEntity entity : platformsToDispose) {
			entity.dispose(world);
		}
		platformsToDispose.clear();
	}

	private void addNewPlatforms() {
		if (createPlatform) {
			addPlatform(Game.getCurrentGame().getViewData().getHeight() + PLATFORM_DISTANCE);
			createPlatform = false;
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
						createPlatform = true;
					}
					Gdx.app.log("Debug", "Platform touching");
				}
				if (contact.getFixtureB().getUserData() instanceof Platform) {
					platform = (Platform) contact.getFixtureB().getUserData();
					if (contact.getFixtureA().getUserData() == null) {
						platformsToDispose.add(platform);
						platforms.removeValue(platform, true);
						createPlatform = true;
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
