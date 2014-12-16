package pl.edu.pb.wi.ztpmtm.animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class FrameContainer {
	private float frameDuration;
	private AtlasRegion[] regions;
	private AnimationType defaultAnimationType;
	private int currentFrame = 0;

	public FrameContainer(final float frameDuration, final AtlasRegion[] regions,
			final AnimationType defaultAnimationType) {
		this.frameDuration = frameDuration;
		this.regions = regions;
		this.defaultAnimationType = defaultAnimationType;
	}

	public void incrementCurrentFrame() {
		currentFrame = ++currentFrame % getRegionsAmount();
	}

	public AtlasRegion getCurrentRegion() {
		return regions[currentFrame];
	}

	public int getRegionsAmount() {
		return regions.length;
	}

	/**
	 * @return the frameDuration
	 */
	public float getFrameDuration() {
		return frameDuration;
	}

	/**
	 * @param frameDuration the frameDuration to set
	 */
	public void setFrameDuration(final float frameDuration) {
		this.frameDuration = frameDuration;
	}

	/**
	 * @return the regions
	 */
	public AtlasRegion[] getRegions() {
		return regions;
	}

	/**
	 * @param regions the regions to set
	 */
	public void setRegions(final AtlasRegion[] regions) {
		this.regions = regions;
	}

	/**
	 * @return the defaultAnimationType
	 */
	public AnimationType getDefaultAnimationType() {
		return defaultAnimationType;
	}

	/**
	 * @param defaultAnimationType the defaultAnimationType to set
	 */
	public void setDefaultAnimationType(final AnimationType defaultAnimationType) {
		this.defaultAnimationType = defaultAnimationType;
	}

	/**
	 * @return the currentFrame
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * @param currentFrame the currentFrame to set
	 */
	public void setCurrentFrame(final int currentFrame) {
		this.currentFrame = currentFrame;
	}
}
