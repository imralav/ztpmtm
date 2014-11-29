package pl.edu.pb.wi.ztpmtm.managers.gui;

import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;
import pl.edu.pb.wi.ztpmtm.managers.ApplicationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

/** Manages libGDX AssetManager object, making the asset access a bit easier thanks to Asset enum.
 * 
 * @author MJ */
public enum AssetsManager implements ApplicationManager {
	INSTANCE;

	private AssetManager assetManager;

	@Override
	public void create() {
		createAssetManager();
		scheduleAssetLoading();
	}

	private void createAssetManager() {
		assetManager = new AssetManager();
	}

	private void scheduleAssetLoading() {
		for (Asset asset : Asset.values()) {
			// Loading all non-manually loaded assets:
			if (!asset.isLoadedManually()) {
				assetManager.load(asset.getPath(), asset.getAssetClass());
			}
		}
	}

	public boolean updateAssetManager() {
		return assetManager.update();
	}

	public float getAssetManagerProgress() {
		return assetManager.getProgress();
	}

	public <T> T getAsset(Asset asset, Class<T> type) {
		return assetManager.get(asset.getPath(), type);
	}

	public Skin loadInterfaceSkin() {
		return new Skin(Gdx.files.internal(Asset.INTERFACE_STYLE.getPath()));
	}

	@Override
	public void dispose() {
		disposeOf(assetManager);
	}

	public static void disposeOf(Disposable disposable) {
		if (disposable != null) {
			disposable.dispose();
		}
	}
}
