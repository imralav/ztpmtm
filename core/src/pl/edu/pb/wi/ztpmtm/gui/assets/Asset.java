package pl.edu.pb.wi.ztpmtm.gui.assets;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

public enum Asset {
	/** Application icons. */
	GAME_ICON_SMALL("icons/icon_small.png", Image.class, Constants.LOADED_MANUALLY),
	GAME_ICON_MEDIUM("icons/icon_medium.png", Image.class, Constants.LOADED_MANUALLY),
	GAME_ICON_BIG("icons/icon_big.png", Image.class, Constants.LOADED_MANUALLY),
	/** General assets. */
	I18N_BUNDLE("i18n/bundle", I18NBundle.class, Constants.LOADED_MANUALLY),
	PREFERENCES("pl.edu.pb.wi.ztpmtm.pref", Preferences.class, Constants.LOADED_MANUALLY),
	/** Interface assets. */
	INTERFACE_STYLE("gui/gui.json", Skin.class, Constants.LOADED_MANUALLY),
	PLAYER("jumper/jumper.pack", TextureAtlas.class, Constants.LOADED_AUTOMATICALLY),
	PLATFORMS("platforms/platforms.pack", TextureAtlas.class, Constants.LOADED_AUTOMATICALLY);

	private final String path;
	private final Class<?> assetClass;
	private final boolean loadedManually;

	public final AssetManager manager = new AssetManager();

	private Asset(final String path, final Class<?> assetClass, final boolean loadedManually) {
		this.path = path;
		this.assetClass = assetClass;
		this.loadedManually = loadedManually;
	}

	public String getPath() {
		return path;
	}

	public Class<?> getAssetClass() {
		return assetClass;
	}

	public boolean isLoadedManually() {
		return loadedManually;
	}

	private final static class Constants {
		private final static boolean LOADED_AUTOMATICALLY = false, LOADED_MANUALLY = !LOADED_AUTOMATICALLY;
	}
}
