package pl.edu.pb.wi.ztpmtm.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Entity {
	public void update(float delta);

	public void render(Batch batch);
}
