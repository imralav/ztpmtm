package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.entity.UserData.EntityType;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.collisions.Bits;

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
		worldBoundUserData = new UserData(EntityType.WORLD_BOUND, this);
	}

	public enum WorldBoundType {
		LEFT {
			@Override
			public void setPosition(final BodyDef bodyDef) {
				bodyDef.position.set(0, Game.getCurrentGame().getViewData().getHeight() / 2f);
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(0f, 0f), new Vector2(0, Game.getCurrentGame().getViewData()
						.getHeight()));
				return sensorShape;
			}
		},
		RIGHT {
			@Override
			public void setPosition(final BodyDef bodyDef) {
				bodyDef.position.set(Game.getCurrentGame().getViewData().getWidth(), Game.getCurrentGame()
						.getViewData().getHeight() / 2f);
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(Game.getCurrentGame().getViewData().getWidth(), 0), new Vector2(
						new Vector2(Game.getCurrentGame().getViewData().getWidth(), Game.getCurrentGame()
								.getViewData().getHeight())));
				return sensorShape;
			}
		},
		BOTTOM {
			@Override
			public void setPosition(final BodyDef bodyDef) {
				bodyDef.position.set(Game.getCurrentGame().getViewData().getWidth() / 2f, 0);
			}

			@Override
			public Shape getShape() {
				final EdgeShape sensorShape = new EdgeShape();
				sensorShape.set(new Vector2(0, 0), new Vector2(
						Game.getCurrentGame().getViewData().getWidth(), 0));
				return sensorShape;
			}
		};

		public abstract void setPosition(BodyDef bodyDef);

		public abstract Shape getShape();
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
		worldBoundType.setPosition(bodyCreator.getBodyDef());
		final FixtureDef sensorFixture = bodyCreator.createFixtureDef(SENSOR_FIXTURENAME);
		sensorFixture.isSensor = true;
		sensorFixture.shape = worldBoundType.getShape();
		sensorFixture.filter.categoryBits = Bits.WORLD_BOUND_CATEGORY_BIT;
		sensorFixture.filter.maskBits = Bits.WORLD_BOUND_MASK_BITS;
		return bodyCreator;
	}

	@Override
	public void createBody(final World world) {
		super.createBody(world);
		applyUserDataToAllFixtures(worldBoundUserData);
	}
}
