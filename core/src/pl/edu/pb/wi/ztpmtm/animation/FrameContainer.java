package pl.edu.pb.wi.ztpmtm.animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class FrameContainer {
	private float frameDuration, elapsedTime = 0;
	private Array<AtlasRegion> regions;
	private int currentFrame = 0;

	public FrameContainer(final float frameDuration,
			final Array<AtlasRegion> array) {
		this.frameDuration = frameDuration;
		this.regions = array;
	}
	
	public void update(final float delta) {
		elapsedTime += delta;
		while (elapsedTime >= frameDuration) {
			elapsedTime -= frameDuration;
			incrementCurrentFrame();
		}
	}

	public void incrementCurrentFrame() {
		currentFrame = ++currentFrame % getRegionsAmount();
	}

	public AtlasRegion getCurrentRegion() {
		return regions.get(currentFrame);
	}

	public int getRegionsAmount() {
		return regions.size;
	}

	/**
	 * @return the frameDuration
	 */
	public float getFrameDuration() {
		return frameDuration;
	}

	/**
	 * @param frameDuration
	 *            the frameDuration to set
	 */
	public void setFrameDuration(final float frameDuration) {
		this.frameDuration = frameDuration;
	}

	/**
	 * @return the regions
	 */
	public Array<AtlasRegion> getRegions() {
		return regions;
	}

	/**
	 * @param regions
	 *            the regions to set
	 */
	public void setRegions(final Array<AtlasRegion> regions) {
		this.regions = regions;
	}

	/**
	 * @return the currentFrame
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * @param currentFrame
	 *            the currentFrame to set
	 */
	public void setCurrentFrame(final int currentFrame) {
		this.currentFrame = currentFrame;
	}
}
