package pl.edu.pb.wi.ztpmtm.gui.screens.impl;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.screens.AbstractApplicationScreen;
import pl.edu.pb.wi.ztpmtm.gui.utilities.InterfaceUtilities;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.TableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.OneColumnTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractApplicationScreen {
	private static MenuScreen CURRENT_INSTANCE;

	public static void initiateInstance() {
		destroyInstance();
		CURRENT_INSTANCE = new MenuScreen();
	}

	public static void destroyInstance() {
		AssetsManager.disposeOf(CURRENT_INSTANCE);
		CURRENT_INSTANCE = null;
	}

	public static MenuScreen getInstance() {
		return CURRENT_INSTANCE;
	}

	private MenuScreen() {
		super();
	}

	@Override
	public void create() {
		createBackground();
		createWindow(Text.MENU_TITLE, WindowStyle.DEFAULT);
		createWidgets();
		prepareWindow();
	}

	@Override
	protected void createWidgets() {
		TableBuilder tableBuilder = new OneColumnTableBuilder(Padding.PAD_4);

		tableBuilder.append(InterfaceUtilities.createGameTitle());
		tableBuilder.append(createStartButton(), true, false);
		tableBuilder.append(createSettingsButton(), true, false);
		tableBuilder.append(createExitButton(), true, false);

		getWindow().add(tableBuilder.build());
	}

	private Actor createStartButton() {
		return WidgetFactory.createTextButton(Text.START_BUTTON, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO :
			}
		}, TextButtonStyle.DEFAULT);
	}

	private TextButton createSettingsButton() {
		return WidgetFactory.createTextButton(Text.SETTINGS, new ClickListener() {
			// Wzorzec projektowy Polecenie - kod zawarty w zmiennej.
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DialogManager.INSTANCE.showSettingsDialog();
			};
		}, TextButtonStyle.DEFAULT);
	}

	private TextButton createExitButton() {
		return WidgetFactory.createTextButton(Text.EXIT, new ClickListener() {
			// Wzorzec projektowy Polecenie - kod zawarty w zmiennej.
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DialogManager.INSTANCE.showExitPromptPopUp();
			};
		}, TextButtonStyle.DEFAULT);
	}

}