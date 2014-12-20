package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.interactionStrategies.InteractionStrategy;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Platform extends B2DEntity {
	private InteractionStrategy interactionStrategy;
	private final float initialY;

	public Platform() {
		this(Game.getCurrentGame().getViewData().getSpawnHeight());
	}

	public Platform(final float initialY) {
		this.initialY = initialY;
	}

	@Override
	public void update(final float delta) {

	}

	@Override
	public void render(final Batch batch) {

	}

	/** @return the interactionStrategy */
	public InteractionStrategy getInteractionStrategy() {
		return interactionStrategy;
	}

	/** @param interactionStrategy the interactionStrategy to set */
	public void setInteractionStrategy(final InteractionStrategy interactionStrategy) {
		this.interactionStrategy = interactionStrategy;
	}

	@Override
	public BodyCreator prepareBody() {
		interactionStrategy = InteractionStrategy.getRandomInteraction();
		final BodyCreator bodyCreator = new BodyCreator();
		interactionStrategy.setBodyData(bodyCreator, initialY);
		return bodyCreator;
	}

}
