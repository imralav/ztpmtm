package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.entity.decorators.helpers.DrawData;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class B2DEntity implements Entity {
	protected Body body;

	public void createBody(final World world) {
		body = prepareBody().createBody(world);
		for (final Fixture fixture : body.getFixtureList()) {
			if (fixture.isSensor()) {
				fixture.setUserData(this);
			}
		}
	}

	public abstract BodyCreator prepareBody();

	public float getScreenX() {
		return body.getPosition().x * Game.PPM;
	}

	public float getScreenY() {
		return body.getPosition().y * Game.PPM;
	}

	public float getWorldX() {
		return body.getPosition().x;
	}

	public float getWorldY() {
		return body.getPosition().y;
	}

	public void updateSprite(final Sprite sprite) {
		sprite.setPosition(body.getPosition().x * Game.PPM - sprite.getWidth() / 2f, body.getPosition().y
				* Game.PPM - sprite.getHeight() / 2f);
	}

	public void updateSprite(final DrawData drawData) {
		drawData.x = body.getPosition().x * Game.PPM;
		drawData.y = body.getPosition().y * Game.PPM;
	}

	public void dispose(final World world) {
		world.destroyBody(body);
	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

}
