package pl.edu.pb.wi.ztpmtm.entity.decorators;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.entity.decorators.helpers.DrawData;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class StaticDecorator extends B2DEntity {
	// private Sprite sprite;
	private final TiledDrawable sprite;
	private final DrawData drawData;
	private final B2DEntity entity;

	public StaticDecorator(final B2DEntity entity, final TextureRegion region) {
		this.entity = entity;
		sprite = new TiledDrawable(region);
		drawData = new DrawData();
	}

	@Override
	public void update(final float delta) {
		entity.update(delta);
		updateSprite(drawData);
	}

	@Override
	public void render(final Batch batch) {
		entity.render(batch);
		sprite.draw(batch, drawData.x, drawData.y, Game.getCurrentGame().getCurrentPlatformWidth(), sprite
				.getRegion().getRegionHeight());
	}

	@Override
	public BodyCreator prepareBody() {
		return entity.prepareBody();
	}

	@Override
	public void dispose(final World world) {
		entity.dispose(world);
	}

	@Override
	public float getScreenX() {
		return entity.getScreenX();
	}

	@Override
	public float getWorldX() {
		return entity.getWorldX();
	}

	@Override
	public void updateSprite(final DrawData drawData) {
		entity.updateSprite(drawData);
	}

	@Override
	public float getScreenY() {
		return entity.getScreenY();
	}

	@Override
	public void createBody(final World world) {
		entity.createBody(world);
	}

	@Override
	public float getWorldY() {
		return entity.getWorldY();
	}

	@Override
	public void updateSprite(final Sprite sprite) {
		entity.updateSprite(sprite);
	}

}
