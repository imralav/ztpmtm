package pl.edu.pb.wi.ztpmtm.entity.decorators;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
	public void render(final Batch batch) {
		entity.render(batch);
		sprite.draw(batch);
	}

	@Override
	public BodyCreator prepareBody() {
		return entity.prepareBody();
	}

}
