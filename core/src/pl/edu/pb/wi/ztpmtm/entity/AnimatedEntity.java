package pl.edu.pb.wi.ztpmtm.entity;

import pl.edu.pb.wi.ztpmtm.animation.AnimationType;

public abstract class AnimatedEntity extends B2DEntity {

	protected AnimationType animationType;

	/**
	 * @return the animationType
	 */
	public AnimationType getAnimationType() {
		return animationType;
	}

	/**
	 * @param animationType the animationType to set
	 */
	public void setAnimationType(final AnimationType animationType) {
		this.animationType = animationType;
	}

}
