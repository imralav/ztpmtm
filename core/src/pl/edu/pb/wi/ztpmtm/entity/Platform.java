package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.game.logic.interactionStrategies.InteractionStrategy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends B2DEntity {
	private InteractionStrategy interactionStrategy;

	@Override
	public void update(final float delta) {

	}

	@Override
	public void render(final SpriteBatch batch) {

	}

	/**
	 * @return the interactionStrategy
	 */
	public InteractionStrategy getInteractionStrategy() {
		return interactionStrategy;
	}

	/**
	 * @param interactionStrategy the interactionStrategy to set
	 */
	public void setInteractionStrategy(final InteractionStrategy interactionStrategy) {
		this.interactionStrategy = interactionStrategy;
	}

	@Override
	protected Body prepareBody(final World world, final Object userObject) {
		interactionStrategy = InteractionStrategy.getRandomInteraction();
		final BodyDef bdef = interactionStrategy.prepareBodyDef();
		final FixtureDef fdef = interactionStrategy.prepareFixture();

		final Body body = world.createBody(bdef);
		body.createFixture(fdef);
		return body;
	}

}
