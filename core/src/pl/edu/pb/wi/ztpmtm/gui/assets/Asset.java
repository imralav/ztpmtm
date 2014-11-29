package pl.edu.pb.wi.ztpmtm.gui.assets;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

public enum Asset {
	/** Application icons. */
	GAME_ICON_SMALL("icons/icon_small.png", Image.class, Contants.LOADED_MANUALLY),
	GAME_ICON_MEDIUM("icons/icon_medium.png", Image.class, Contants.LOADED_MANUALLY),
	GAME_ICON_BIG("icons/icon_big.png", Image.class, Contants.LOADED_MANUALLY),
	/** General assets. */
	I18N_BUNDLE("i18n/bundle", I18NBundle.class, Contants.LOADED_MANUALLY),
	PREFERENCES("pl.edu.pb.wi.ztpmtm.pref", Preferences.class, Contants.LOADED_MANUALLY),
	/** Interface assets. */
	INTERFACE_STYLE("gui/gui.json", Skin.class, Contants.LOADED_MANUALLY);

	private final String path;
	private final Class<?> assetClass;
	private final boolean loadedManually;

	private Asset(String path, Class<?> assetClass, boolean loadedManually) {
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

	private final static class Contants {
		private final static boolean LOADED_AUTOMATICALLY = false, LOADED_MANUALLY = !LOADED_AUTOMATICALLY;
	}
}
