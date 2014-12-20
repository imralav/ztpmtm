package pl.edu.pb.wi.ztpmtm.game;

import pl.edu.pb.wi.ztpmtm.entity.B2DEntity;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameRenderer {
	private final GameWorld gameWorld;

	private final Box2DDebugRenderer debugRenderer;
	private final OrthographicCamera b2dCamera;

	public GameRenderer(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		debugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Gdx.graphics.getWidth() / Game.PPM, Gdx.graphics.getHeight() / Game.PPM);
	}

	public void render(final Batch batch) {
		debugRenderer.render(gameWorld.getWorld(), b2dCamera.combined);
		batch.begin();
		batch.setColor(1, 1, 1, 1);
		renderPlatforms(batch);
		gameWorld.getPlayer().render(batch);
		batch.end();
	}

	private void renderPlatforms(final Batch batch) {
		for (final B2DEntity entity : gameWorld.getPlatforms()) {
			entity.render(batch);
		}
	}
}
