package pl.edu.pb.wi.ztpmtm.game.logic.collisions;

import com.badlogic.gdx.physics.box2d.Filter;

public enum CollisionFilter {
	PLAYER(Bits.PLAYER_CATEGORY_BIT, Bits.PLAYER_MASK_BITS),
	PLATFORM_BELOW_PLAYER(Bits.PLATFORM_CATEGORY_BIT, Bits.PLATFORM_BELOW_PLAYER_MASK_BITS),
	PLATFORM_ABOVE_PLAYER(Bits.NO_COLLISION, Bits.PLATFORM_ABOVE_PLAYER_MASK_BITS),
	WORLD_BOUND(Bits.WORLD_BOUND_CATEGORY_BIT, Bits.WORLD_BOUND_MASK_BITS);

	private Filter filter;

	private CollisionFilter(final short categoryBits, final short maskBits) {
		filter = new Filter();
		filter.categoryBits = categoryBits;
		filter.maskBits = maskBits;
	}

	public Filter getFilter() {
		return filter;
	}

	public void apply(final Filter filter) {
		filter.categoryBits = this.filter.categoryBits;
		filter.maskBits = this.filter.maskBits;
	}
}
