package pl.edu.pb.wi.ztpmtm.entity.decorators;

import pl.edu.pb.wi.ztpmtm.animation.AnimationType;
import pl.edu.pb.wi.ztpmtm.animation.FrameContainer;
import pl.edu.pb.wi.ztpmtm.entity.AnimatedEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;

public class AnimatedEntityDecorator extends AnimatedEntity {
	private final AnimatedEntity entity;
	private final ObjectMap<AnimationType, FrameContainer> animations;
	private float elapsedTime = 0;
	private final Sprite sprite;

	public AnimatedEntityDecorator(final AnimatedEntity entity) {
		this.entity = entity;
		animations = new ObjectMap<AnimationType, FrameContainer>();
		sprite = new Sprite();
	}

	/**
	 * @return the entity
	 */
	public AnimatedEntity getEntity() {
		return entity;
	}

	@Override
	public void update(final float delta) {
		entity.update(delta);
		updateFrames(delta);
		updateSprite(sprite);
	}

	private void updateFrames(final float delta) {
		elapsedTime += delta;
		final FrameContainer animation = getCurrentAnimation();
		while (elapsedTime > animation.getFrameDuration()) {
			elapsedTime -= animation.getFrameDuration();
			animation.incrementCurrentFrame();
		}
		sprite.setRegion(getCurrentAnimation().getCurrentRegion());
	}

	private FrameContainer getCurrentAnimation() {
		final AnimationType currentAnimationType = entity.getAnimationType();
		final FrameContainer frame = animations.get(currentAnimationType);
		return frame;
	}

	@Override
	public void render(final SpriteBatch batch) {
		entity.render(batch);
		sprite.draw(batch);
	}

	@Override
	protected Body prepareBody(final World world, final Object userObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
