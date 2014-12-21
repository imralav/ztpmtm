package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.entity.UserData.EntityType;
import pl.edu.pb.wi.ztpmtm.entity.creation.BodyCreator;
import pl.edu.pb.wi.ztpmtm.entity.decorators.helpers.DrawData;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.game.logic.interactionStrategies.InteractionStrategy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class Platform extends B2DEntity {
	private InteractionStrategy interactionStrategy;
	private final float initialY;
	private final TiledDrawable sprite;
	private final DrawData drawData;
	private final UserData platformUserData;

	public Platform() {
		this(Game.getCurrentGame().getViewData().getSpawnHeight());
	}

	public Platform(final float initialY) {
		this.initialY = initialY;
		drawData = new DrawData();
		sprite = new TiledDrawable();
		platformUserData = new UserData(EntityType.PLATFORM, this);
	}

	@Override
	public void update(final float delta) {
		updateSprite(drawData);
	}

	@Override
	public void render(final Batch batch) {
		final float currentPlatformWidth = Game.getCurrentGame().getCurrentPlatformWidth();
		sprite.draw(batch, drawData.x - currentPlatformWidth / 2f, drawData.y
				- sprite.getRegion().getRegionHeight() / 2f, currentPlatformWidth, sprite.getRegion()
				.getRegionHeight());
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

	/**
	 * Besides creating body using {@link B2DEntity#createBody(World)} this method sets
	 * all of body's fixtures user data to the one created in {@link Platform#Platform(float)}
	 */
	@Override
	public void createBody(final World world) {
		super.createBody(world);
		applyUserDataToAllFixtures(platformUserData);
	}

	public void setRegion(final AtlasRegion region) {
		sprite.setRegion(region);
	}

	public void updateFallingSpeed(final float velocity) {
		body.setLinearVelocity(0, velocity);
	}

	/**
	 * @return the drawData
	 */
	public DrawData getDrawData() {
		return drawData;
	}

	/**
	 * @return the sprite
	 */
	public TiledDrawable getSprite() {
		return sprite;
	}
}
