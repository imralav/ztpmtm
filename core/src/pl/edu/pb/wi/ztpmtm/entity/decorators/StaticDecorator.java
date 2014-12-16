package pl.edu.pb.wi.ztpmtm.entity.decorators;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class StaticDecorator extends B2DEntity {
	private Sprite sprite;
	private final B2DEntity entity;

	public StaticDecorator(final B2DEntity entity) {
		this.entity = entity;
	}

	@Override
	public void update(final float delta) {
		entity.update(delta);
		updateSprite(sprite);
	}

	@Override
	public void render(final SpriteBatch batch) {
		entity.render(batch);
		sprite.draw(batch);
	}

	@Override
	protected Body prepareBody(final World world, final Object userObject) {
		return null;
	}

}
