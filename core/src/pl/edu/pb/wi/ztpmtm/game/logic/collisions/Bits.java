package pl.edu.pb.wi.ztpmtm.game.logic.collisions;

public class Bits {
	public static final short NO_COLLISION = 0;
	public static final short PLAYER_CATEGORY_BIT = 1;
	public static final short PLATFORM_CATEGORY_BIT = 2;
	public static final short WORLD_BOUND_CATEGORY_BIT = 4;

	public static final short PLAYER_MASK_BITS = PLATFORM_CATEGORY_BIT | WORLD_BOUND_CATEGORY_BIT;
	public static final short PLATFORM_ABOVE_PLAYER_MASK_BITS = NO_COLLISION;
	public static final short PLATFORM_BELOW_PLAYER_MASK_BITS = PLAYER_CATEGORY_BIT
			| WORLD_BOUND_CATEGORY_BIT;
	public static final short WORLD_BOUND_MASK_BITS = PLAYER_CATEGORY_BIT | PLATFORM_CATEGORY_BIT;

	public static void main(final String... s) {
		System.out.println(WORLD_BOUND_CATEGORY_BIT & PLATFORM_BELOW_PLAYER_MASK_BITS);
		System.out.println(PLATFORM_CATEGORY_BIT & WORLD_BOUND_MASK_BITS);

	}
}
