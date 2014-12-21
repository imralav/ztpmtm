package pl.edu.pb.wi.ztpmtm.entity;

import com.badlogic.gdx.Gdx;

public class UserData {
	private EntityType entityType;
	private B2DEntity entity;

	public UserData(final EntityType entityType, final B2DEntity entity) {
		super();
		this.entityType = entityType;
		this.entity = entity;
	}

	public enum EntityType {
		PLAYER {
			@Override
			protected void beginContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "Player#beginContact");
				test(contactingUserData);
			}

			@Override
			protected void endContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "Player#endContact");
				test(contactingUserData);
			}
		},
		PLATFORM {
			@Override
			protected void beginContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "Platform#beginContact");
				test(contactingUserData);
			}

			@Override
			protected void endContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "Platform#endContact");
				test(contactingUserData);
			}
		},
		WORLD_BOUND {
			@Override
			protected void beginContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "WorldBound#beginContact");
				test(contactingUserData);
			}

			@Override
			protected void endContact(final UserData contactingUserData) {
				Gdx.app.log("Debug", "WorldBound#endContact");
				test(contactingUserData);
			}
		};

		protected abstract void beginContact(UserData contactingUserData);

		protected abstract void endContact(UserData contactingUserData);

		protected void test(final UserData contactingUserData) {
			switch (contactingUserData.getEntityType()) {
				case PLATFORM:
					Gdx.app.log("    ", "with Platform");
					break;
				case PLAYER:
					Gdx.app.log("    ", "with Player");
					break;
				case WORLD_BOUND:
					Gdx.app.log("    ", "with World Bound");
					break;
				default:
					Gdx.app.log("    ", "with No idea what");
					break;
			}
		}
	}

	public void beginContact(final UserData contactingUserData) {
		entityType.beginContact(contactingUserData);
	}

	public void endContact(final UserData contactingUserData) {
		entityType.endContact(contactingUserData);
	}

	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(final EntityType entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the entity
	 */
	public B2DEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(final B2DEntity entity) {
		this.entity = entity;
	}
}
