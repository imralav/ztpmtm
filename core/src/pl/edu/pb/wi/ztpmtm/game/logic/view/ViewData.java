package pl.edu.pb.wi.ztpmtm.game.logic.view;

import com.badlogic.gdx.Gdx;

public class ViewData {
	private static final float SPAWN_OFFSET = 40;
	private final float width, height, spawnHeight;

	private ViewData(final float width, final float height, final float spawnHeight) {
		this.width = width;
		this.height = height;
		this.spawnHeight = spawnHeight;
	}

	public static ViewData prepare(final float ppm) {
		return new ViewData(Gdx.graphics.getWidth() / ppm, Gdx.graphics.getHeight() / ppm,
				Gdx.graphics.getHeight() / ppm + SPAWN_OFFSET / ppm);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getSpawnHeight() {
		return spawnHeight;
	}

}
