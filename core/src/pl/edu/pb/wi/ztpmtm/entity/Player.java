package pl.edu.pb.wi.ztpmtm.entity;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pb.wi.ztpmtm.animation.AnimationType;
import pl.edu.pb.wi.ztpmtm.animation.FrameContainer;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends AnimatedEntity {

	private static final float WALKING_FRAMERATE = 1f / 8f;
	private static final String WALK = "walk";
	private static final float IDLE_FRAMERATE = 1;
	private static final String IDLE = "idle";
	private static final float JUMP_FRAMERATE = 1;
	private static final String JUMP = "jump";

	private final Sprite sprite;
	private final float elapsedTime = 0;
	private final float speed = 10;
	private AnimationType currentAnimation;
	private final Map<AnimationType, FrameContainer> animation;

	public Player(final World world) {
		animation = new HashMap<AnimationType, FrameContainer>();
		setAnimations();
		sprite = new Sprite(animation.get(currentAnimation).getCurrentRegion());
		createBody(world);
	}

	@Override
	public void update(final float delta) {
		animation.get(currentAnimation).update(delta);
		updateSprite(sprite);

	}

	@Override
	public void render(final Batch batch) {
		sprite.draw(batch);
	}

	@Override
	public BodyCreator prepareBody() {
		final BodyCreator bodyCreator = new BodyCreator();
		bodyCreator.setPosition(new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f));
		final FixtureDef fixtureDef = bodyCreator.createFixtureDef("body");
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(10f / Game.PPM, 10f / Game.PPM);
		fixtureDef.shape = shape;
		return bodyCreator;
	}

	private void setAnimations() {
		final TextureAtlas atlas = AssetsManager.INSTANCE.getAsset(Asset.PLAYER, TextureAtlas.class);
		animation.put(AnimationType.WALK, new FrameContainer(WALKING_FRAMERATE, atlas.findRegions(WALK)));
		animation.put(AnimationType.IDLE, new FrameContainer(IDLE_FRAMERATE, atlas.findRegions(IDLE)));
		animation.put(AnimationType.JUMP, new FrameContainer(JUMP_FRAMERATE, atlas.findRegions(JUMP)));

		currentAnimation = AnimationType.IDLE;
	}

	public void draw(final SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void handleInput(final float delta) {
		// if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
		// currentAnimation = animation.get(WALK);
		// body.applyLinearImpulse(new Vector2(-speed / Game.PPM, 0), body.getWorldCenter(), true);
		// sprite.setScale(-1, 1);
		// } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
		// body.applyLinearImpulse(new Vector2(speed / Game.PPM, 0), body.getWorldCenter(), true);
		// currentAnimation = animation.get(WALK);
		// sprite.setScale(1, 1);
		// } else {
		// currentAnimation = animation.get(IDLE);
		// }
		// if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && collidesWithGround()) {
		//
		// body.applyForceToCenter(0f, 100f, true);
		// }
	}

}
