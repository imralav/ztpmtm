package pl.edu.pb.wi.ztpmtm.gui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class Background extends Widget {
	// Background settings:
	private final TiledDrawable backgroundImage;
	private final boolean isMoving, isNeverTransparent;
	// Control variables:
	private float scrollingSpeed, backgroundOffset;
	private final float backgroundSize;
	private boolean isStopped;

	public Background(Skin skin) {
		this(skin, "default");
	}

	public Background(Skin skin, String styleName) {
		this(skin.get(styleName, BackgroundStyle.class));
	}

	public Background(BackgroundStyle style) {
		// Settings:
		backgroundImage = new TiledDrawable(style.background);
		isMoving = style.isMoving;
		isNeverTransparent = style.isNeverTransparent;

		// Control variables:
		backgroundSize = style.background.getRegionWidth();
		scrollingSpeed = style.scrollingSpeed;
		backgroundOffset = 0f;
		isStopped = false;
	}

	public void setScrollingSpeed(float scrollingSpeed) {
		this.scrollingSpeed = scrollingSpeed;
	}

	public void stopMoving(boolean isStopped) {
		if (isMoving) {
			this.isStopped = isStopped;
		}
	}

	public float getBackgroundOffset() {
		return backgroundOffset;
	}

	public void setBackgroundOffset(float backgroundOffset) {
		this.backgroundOffset = backgroundOffset;
	}

	@Override
	public void act(float delta) {
		if (isMoving && !isStopped) {
			backgroundOffset += delta * scrollingSpeed;
			backgroundOffset %= backgroundSize;
		}

		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isNeverTransparent) {
			batch.setColor(Color.WHITE);
		} else {
			Color color = getColor();
			batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		}

		if (isMoving) {
			backgroundImage.draw(batch, (int) -backgroundOffset, (int) -backgroundOffset, getStage()
					.getWidth() + backgroundSize, getStage().getHeight() + backgroundSize);
		} else {
			backgroundImage.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	public static class BackgroundStyle {
		public TextureRegion background;
		public boolean isMoving;
		public boolean isNeverTransparent;
		public float scrollingSpeed;

		public BackgroundStyle() {
			isMoving = false;
		}

		public BackgroundStyle(TextureRegion background, boolean isMoving, boolean isNeverTransparent,
				float scrollingSpeed) {
			this.background = background;
			this.isMoving = isMoving;
			this.isNeverTransparent = isNeverTransparent;
			this.scrollingSpeed = scrollingSpeed;
		}
	}
}
