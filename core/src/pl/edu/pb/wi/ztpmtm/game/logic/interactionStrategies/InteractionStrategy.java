package pl.edu.pb.wi.ztpmtm.game.logic.interactionStrategies;

import pl.edu.pb.wi.ztpmtm.entity.Player;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public enum InteractionStrategy {
	ICE {
		@Override
		public FixtureDef prepareFixture() {
			final FixtureDef fdef = super.prepareFixture();
			fdef.friction = 0f;
			return fdef;
		}
	},
	MUD {
		@Override
		public FixtureDef prepareFixture() {
			final FixtureDef fdef = super.prepareFixture();
			fdef.restitution = 0f;
			fdef.friction = 0.9f;
			return fdef;
		}

		@Override
		public void beginCollision(final Player player) {
			// TODO: status SLOWED na playera
		}

		@Override
		public void endCollision(final Player player) {
			// TODO: zdejmuje SLOWED
		}
	},
	RUBBER {
		@Override
		public FixtureDef prepareFixture() {
			final FixtureDef fdef = super.prepareFixture();
			fdef.restitution = 1f;
			return fdef;
		}

		@Override
		public void beginCollision(final Player player) {
			// TODO wybij w górę
		}
	},
	GRASS,
	STONE {
		@Override
		public BodyDef prepareBodyDef() {
			final BodyDef bdef = new BodyDef();
			bdef.type = BodyType.DynamicBody;
			return bdef;
		}

		@Override
		public void beginCollision(final Player player) {
			// TODO stone opada przy kolizji
		}
	},
	SHROOM {
		@Override
		public void beginCollision(final Player player) {
			// TODO ustawia status INVERT_INPUT
		}

		@Override
		public void endCollision(final Player player) {
			// TODO zdejmuje status INVERT_INPUT
		}
	},
	GHOST;

	public FixtureDef prepareFixture() {
		final FixtureDef fdef = new FixtureDef();
		fdef.density = 1f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.5f;
		return fdef;
	};

	public void beginCollision(final Player player) {
	}

	public void endCollision(final Player player) {
	}

	public static InteractionStrategy getRandomInteraction() {
		return values()[MathUtils.random(values().length - 1)];
	}

	public BodyDef prepareBodyDef() {
		final BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		return bdef;
	}
}
