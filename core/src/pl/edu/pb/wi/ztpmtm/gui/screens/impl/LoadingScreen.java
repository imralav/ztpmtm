package pl.edu.pb.wi.ztpmtm.gui.screens.impl;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.screens.AbstractApplicationScreen;
import pl.edu.pb.wi.ztpmtm.gui.utilities.InterfaceUtilities;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.TableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.WidgetData;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.OneColumnTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ProgressBarStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/** The first screen, shown just after the game has started. Shows current asset loading progress and allows to
 * choose a server's address.
 * 
 * @author MJ */
public class LoadingScreen extends AbstractApplicationScreen {
	private static LoadingScreen CURRENT_INSTANCE;
	private static final int DEFAULT_LOADING_BAR_WIDTH = 200;

	private ProgressBar loadingBar;
	private Label loadingLabel;
	private boolean isLoaded;

	public static void initiateInstance() {
		destroyInstance();
		CURRENT_INSTANCE = new LoadingScreen();
	}

	public static void destroyInstance() {
		AssetsManager.disposeOf(CURRENT_INSTANCE);
		CURRENT_INSTANCE = null;
	}

	public static LoadingScreen getInstance() {
		return CURRENT_INSTANCE;
	}

	private LoadingScreen() {
		super();
	}

	@Override
	public void create() {
		createBackground();
		createWindow(Text.LOADING_TITLE, WindowStyle.DEFAULT);
		createWidgets();
		prepareWindow();
	}

	@Override
	protected void createWidgets() {
		getWindow().add(buildTable());
	}

	private Table buildTable() {
		TableBuilder tableBuilder = new OneColumnTableBuilder(Padding.PAD_4);

		tableBuilder.append(InterfaceUtilities.createGameTitle());
		tableBuilder.append(loadingBar = WidgetFactory.createLoadingBar(ProgressBarStyle.DEFAULT),
				DEFAULT_LOADING_BAR_WIDTH, WidgetData.IGNORED_SIZE_VALUE);
		tableBuilder.append(loadingLabel =
				WidgetFactory.createLabel(Text.LOADING_PROGRESS_LABEL, LabelStyle.DEFAULT_DARK_BLUE,
						Integer.valueOf(100)));

		return tableBuilder.build();
	}

	@Override
	public void render(float delta) {
		updateAssetsLoading();
		super.render(delta);
	}

	private void updateAssetsLoading() {
		if (!isLoaded) {
			if (AssetsManager.INSTANCE.updateAssetManager()) {
				setAsLoaded();
			}
			updateLoadingWidgets();
		}
	}

	private void updateLoadingWidgets() {
		float progressValue = AssetsManager.INSTANCE.getAssetManagerProgress();
		loadingBar.setValue(progressValue);
		loadingLabel.setText(LocalizationManager.INSTANCE.getText(Text.LOADING_PROGRESS_LABEL,
				Integer.valueOf((int) (progressValue * 100f))));
	}

	private void setAsLoaded() {
		isLoaded = true;
		InterfaceManager.INSTANCE.processPostLoadingActions();
	}
}
