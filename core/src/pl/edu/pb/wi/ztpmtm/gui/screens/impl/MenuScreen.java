package pl.edu.pb.wi.ztpmtm.gui.screens.impl;

import pl.edu.pb.wi.ztpmtm.game.GameDifficulty;
import pl.edu.pb.wi.ztpmtm.game.logic.Game;
import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.screens.AbstractApplicationScreen;
import pl.edu.pb.wi.ztpmtm.gui.utilities.InterfaceUtilities;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.TableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.OneColumnTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ListStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.AssetsManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
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
		final TableBuilder tableBuilder = new OneColumnTableBuilder(Padding.PAD_4);

		tableBuilder.append(InterfaceUtilities.createGameTitle());
		tableBuilder.append(createStartButton(), true, false);
		tableBuilder.append(createSettingsButton(), true, false);
		tableBuilder.append(createExitButton(), true, false);

		getWindow().add(tableBuilder.build());
	}

	private Actor createStartButton() {
		return WidgetFactory.createTextButton(Text.START_BUTTON, new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				showDifficultyChoosingDialog();
			}
		}, TextButtonStyle.DEFAULT);
	}

	private void showDifficultyChoosingDialog() {
		DialogManager.INSTANCE.showWindowDialog(new Dialog(LocalizationManager.INSTANCE
				.getText(Text.DIFF_DIALOG_TITLE), InterfaceManager.INSTANCE.getInterfaceStyle(),
				WindowStyle.DIALOG.getStyleName()) {
			private List<String> difficultiesList;
			{
				DialogManager.applyDefaultDialogPadding(this);
				getContentTable().add(prepareDialogWidgets());
				addButtons();
			}

			private void addButtons() {
				button(WidgetFactory.createTextButton(Text.CANCEL, TextButtonStyle.DEFAULT));
				button(WidgetFactory.createTextButton(Text.OK, TextButtonStyle.DEFAULT),
						prepareStartGameAction());
			}

			private Object prepareStartGameAction() {
				return new Runnable() {
					@Override
					public void run() {
						Game.prepareGame(getDifficultyLevel());
						InterfaceManager.INSTANCE.setScreen(GameScreen.getInstance());
					}
				};
			}

			private GameDifficulty getDifficultyLevel() {
				for (final GameDifficulty difficulty : GameDifficulty.values()) {
					if (difficultiesList.getSelectedIndex() == difficulty.getIndex()) {
						return difficulty;
					}
				}
				return GameDifficulty.EASY;
			}

			protected Actor prepareDialogWidgets() {
				final TableBuilder tableBuilder = new OneColumnTableBuilder(Padding.PAD_4);

				tableBuilder.append(WidgetFactory.createLabel(Text.DIFF_DIALOG_PROMPT, LabelStyle.DEFAULT));
				tableBuilder.append(prepareOptionList());

				return tableBuilder.build();
			}

			private Actor prepareOptionList() {
				difficultiesList = WidgetFactory.createList(ListStyle.AWESOME);
				difficultiesList.getItems().addAll(
						LocalizationManager.INSTANCE.getTexts(Text.DIFF_EASY, Text.DIFF_HARD));
				difficultiesList.invalidateHierarchy();
				difficultiesList.setSelectedIndex(GameDifficulty.EASY.getIndex());
				return difficultiesList;
			}

			@Override
			protected void result(final Object object) {
				if (object instanceof Runnable) {
					((Runnable) object).run();
				}
			}
		});
	}

	private TextButton createSettingsButton() {
		return WidgetFactory.createTextButton(Text.SETTINGS, new ClickListener() {
			// Wzorzec projektowy Polecenie - kod zawarty w zmiennej.
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				DialogManager.INSTANCE.showSettingsDialog();
			};
		}, TextButtonStyle.DEFAULT);
	}

	private TextButton createExitButton() {
		return WidgetFactory.createTextButton(Text.EXIT, new ClickListener() {
			// Wzorzec projektowy Polecenie - kod zawarty w zmiennej.
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				DialogManager.INSTANCE.showExitPromptPopUp();
			};
		}, TextButtonStyle.DEFAULT);
	}

}