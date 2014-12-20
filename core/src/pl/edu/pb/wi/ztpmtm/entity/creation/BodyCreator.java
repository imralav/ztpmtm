package pl.edu.pb.wi.ztpmtm.entity.creation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;

public class BodyCreator {
	private static final BodyType DEFAULT_BODY_TYPE = BodyType.KinematicBody;
	private final BodyDef bodyDef;
	private final ObjectMap<String, FixtureDef> fixtureDefs;

	public BodyCreator() {
		this(DEFAULT_BODY_TYPE);
	}

	public BodyCreator(final BodyType bodyType) {
		bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		fixtureDefs = new ObjectMap<String, FixtureDef>();
	}

	public Body createBody(final World world) {
		final Body body = world.createBody(bodyDef);
		for (final FixtureDef fixtureDef : fixtureDefs.values()) {
			body.createFixture(fixtureDef);
		}
		return body;
	}

	public void setPosition(final Vector2 position) {
		bodyDef.position.set(position);
	}

	public void setType(final BodyType bodyType) {
		bodyDef.type = bodyType;
	}

	public void setFixedRotation(final boolean isFixed) {
		bodyDef.fixedRotation = isFixed;
	}

	public FixtureDef createFixtureDef(final String forName) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDefs.put(forName, fixtureDef);
		return fixtureDef;
	}

	public BodyDef getBodyDef() {
		return bodyDef;
	}

	public FixtureDef getFixtureDefForName(final String name) {
		return fixtureDefs.get(name);
	}
}
