package pl.edu.pb.wi.ztpmtm.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Entity {
	public void update(float delta);

	public void render(SpriteBatch batch);
}
