package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.entity.UserData.EntityType;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.collisions.CollisionFilter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldBound extends B2DEntity {
	private static final String SENSOR_FIXTURENAME = "sensor";
	private final WorldBoundType worldBoundType;
	private final UserData worldBoundUserData;

	public WorldBound(final WorldBoundType worldBoundType) {
		this.worldBoundType = worldBoundType;
		worldBoundUserData = new UserData(worldBoundType.getEntityType(), this);
	}

	public enum WorldBoundType {
		LEFT(EntityType.WORLD_BOUND) {
			@Override
			public void setBodyDef(final BodyDef bodyDef) {
				bodyDef.position.set(0, Game.getCurrentGame().getViewData().getHeight() / 2f);
				bodyDef.type = BodyType.StaticBody;
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(0f, -Game.getCurrentGame().getViewData().getHeight() / 2f),
						new Vector2(0, Game.getCurrentGame().getViewData().getHeight() / 2f));
				return sensorShape;
			}

			@Override
			public void setFixtureDef(final FixtureDef fixtureDef) {
				fixtureDef.isSensor = false;
			}
		},
		RIGHT(EntityType.WORLD_BOUND) {
			@Override
			public void setBodyDef(final BodyDef bodyDef) {
				bodyDef.position.set(Game.getCurrentGame().getViewData().getWidth(), Game.getCurrentGame()
						.getViewData().getHeight() / 2f);
				bodyDef.type = BodyType.StaticBody;
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(0, -Game.getCurrentGame().getViewData().getHeight() / 2f),
						new Vector2(new Vector2(0, Game.getCurrentGame().getViewData().getHeight() / 2f)));
				return sensorShape;
			}

			@Override
			public void setFixtureDef(final FixtureDef fixtureDef) {
				fixtureDef.isSensor = false;
			}
		},
		TOP(EntityType.WORLD_BOUND) {
			@Override
			public void setBodyDef(final BodyDef bodyDef) {
				bodyDef.position.set(Game.getCurrentGame().getViewData().getWidth() / 2f, Game
						.getCurrentGame().getViewData().getHeight());
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(-Game.getCurrentGame().getViewData().getWidth() / 2f, 0),
						new Vector2(Game.getCurrentGame().getViewData().getWidth() / 2f, 0));
				return sensorShape;
			}

			@Override
			public void setFixtureDef(final FixtureDef fixtureDef) {
				fixtureDef.isSensor = false;
			}
		},
		BOTTOM(EntityType.WORLD_BOUND) {
			@Override
			public void setBodyDef(final BodyDef bodyDef) {
				bodyDef.position.set(Game.getCurrentGame().getViewData().getWidth() / 2f, 0);
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(-Game.getCurrentGame().getViewData().getWidth() / 2f, 0),
						new Vector2(Game.getCurrentGame().getViewData().getWidth() / 2f, 0));
				return sensorShape;
			}

			@Override
			public void setFixtureDef(final FixtureDef fixtureDef) {
				fixtureDef.isSensor = false;
			}
		};
		private EntityType entityType;

		private WorldBoundType(final EntityType entityType) {
			this.entityType = entityType;
		}

		public abstract void setBodyDef(BodyDef bodyDef);

		public abstract void setFixtureDef(FixtureDef fixtureDef);

		public abstract Shape getShape();

		/**
		 * @return the entityType
		 */
		public EntityType getEntityType() {
			return entityType;
		}
	}

	@Override
	public void update(final float delta) {

	}

	@Override
	public void render(final Batch batch) {

	}

	@Override
	public BodyCreator prepareBody() {
		final BodyCreator bodyCreator = new BodyCreator(BodyType.StaticBody);
		worldBoundType.setBodyDef(bodyCreator.getBodyDef());
		final FixtureDef worldBoundFixture = bodyCreator.createFixtureDef(SENSOR_FIXTURENAME);
		worldBoundFixture.shape = worldBoundType.getShape();
		worldBoundFixture.filter.categoryBits = CollisionFilter.WORLD_BOUND.getFilter().categoryBits;
		worldBoundFixture.filter.maskBits = CollisionFilter.WORLD_BOUND.getFilter().maskBits;
		worldBoundType.setFixtureDef(worldBoundFixture);
		return bodyCreator;
	}

	@Override
	public void createBody(final World world) {
		super.createBody(world);
		applyUserDataToAllFixtures(worldBoundUserData);
	}
}
