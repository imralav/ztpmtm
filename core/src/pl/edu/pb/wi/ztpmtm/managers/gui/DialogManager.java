package pl.edu.pb.wi.ztpmtm.managers.gui;

import pl.edu.pb.wi.ztpmtm.Core;
import pl.edu.pb.wi.ztpmtm.gui.assets.MultilineText;
import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.utilities.InterfaceUtilities;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.ApplicationManager;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.preferences.GameSettings;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public enum DialogManager implements ApplicationManager {
	INSTANCE;

	public final static float DEFAULT_DIALOG_FADING_TIME = 0.4f;
	public final static Runnable NO_ACTION = null;

	private Dialog popUpDialog, windowDialog;

	@Override
	public void create() {
		// Scene2D widgets can be created without any preparations, provided that assets are ready.
	}

	@Override
	public void dispose() {
		// Scene2D widgets don't need to be disposed of.
	}

	public static boolean isDialogShown(Dialog dialog) {
		if (dialog != null && dialog.getStage() != null) {
			return true;
		}
		return false;
	}

	public boolean hideAllDialogs() {
		boolean wasDialogHidden = false;

		if (isDialogShown(windowDialog)) {
			windowDialog.hide();
			wasDialogHidden = true;
		}
		if (isDialogShown(popUpDialog)) {
			popUpDialog.hide();
			wasDialogHidden = true;
		}

		return wasDialogHidden;
	}

	public void resize(int width, int height) {
		updateDialogPosition(windowDialog, width, height);
		updateDialogPosition(popUpDialog, width, height);
	}

	private void updateDialogPosition(Dialog dialog, int width, int height) {
		if (isDialogShown(dialog)) {
			InterfaceUtilities.updateWindowPosition(dialog, dialog.getStage().getWidth(), dialog.getStage()
					.getHeight(), width, height);
		}
	}

	private void hideOldDialog(Dialog dialog) {
		if (isDialogShown(dialog)) {
			dialog.hide();
		}
	}

	public void showWindowDialog(Dialog dialog) {
		hideOldDialog(windowDialog);
		this.windowDialog = dialog.show(InterfaceManager.INSTANCE.getCurrentScreen().getStage());
	}

	public void showPopUpDialog(Text title, final Text... messageLines) {
		switchPopUpDialog(new Dialog(LocalizationManager.INSTANCE.getText(title),
				InterfaceManager.INSTANCE.getInterfaceStyle(), WindowStyle.DIALOG.getStyleName()) {
			{
				applyDefaultDialogPadding(this);
				addDialogLabels(this, messageLines);

				button(WidgetFactory.createTextButton(Text.BACK, TextButtonStyle.DEFAULT));
			}
		});
	}

	public void showErrorPopUp(Text... messageLines) {
		showPopUpDialog(Text.ERROR_TITLE, messageLines);
	}

	public void showErrorPopUp(MultilineText multilineText) {
		showErrorPopUp(multilineText.getLines());
	}

	private void switchPopUpDialog(Dialog popUpDialog) {
		hideOldDialog(this.popUpDialog);
		this.popUpDialog = popUpDialog.show(InterfaceManager.INSTANCE.getCurrentScreen().getStage());
	}

	public static void applyDefaultDialogPadding(Dialog dialog) {
		WindowStyle.DIALOG.getDefaultPadding().applyPadding(dialog);
		Padding.PAD_4.applyPadding(dialog.getContentTable());
	}

	public void hideConnectingPopUp() {
		clearPopUpDialog();
	}

	public void clearPopUpDialog() {
		hideOldDialog(popUpDialog);
	}

	public void showSimpleChoicePopUp(Text title, final Runnable actionOnConfirm,
			final Runnable actionOnDecline, final Text... messageLines) {
		switchPopUpDialog(new Dialog(LocalizationManager.INSTANCE.getText(title),
				InterfaceManager.INSTANCE.getInterfaceStyle(), WindowStyle.DIALOG.getStyleName()) {
			{
				applyDefaultDialogPadding(this);
				addDialogLabels(this, messageLines);

				button(WidgetFactory.createTextButton(Text.YES, TextButtonStyle.DEFAULT), actionOnConfirm);
				button(WidgetFactory.createTextButton(Text.NO, TextButtonStyle.DEFAULT), actionOnDecline);
			}

			@Override
			protected void result(Object object) {
				if (object instanceof Runnable) {
					((Runnable) object).run();
				}
			}
		});
	}

	private static void addDialogLabels(Dialog dialog, Text... messageLines) {
		for (Text messageLine : messageLines) {
			dialog.text(WidgetFactory.createLabel(messageLine, LabelStyle.DEFAULT_BLUE));
			dialog.getContentTable().row();
		}
	}

	public void showSimpleChoicePopUp(Text title, Runnable actionOnConfirm, Runnable actionOnDecline,
			MultilineText multilineText) {
		showSimpleChoicePopUp(title, actionOnConfirm, actionOnDecline, multilineText.getLines());
	}

	public void showExitPromptPopUp() {
		showSimpleChoicePopUp(Text.EXIT_TITLE, new Runnable() {
			@Override
			public void run() {
				Core.INSTANCE.exit();
			}
		}, DialogManager.NO_ACTION, Text.EXIT_PROMPT);
	}

	public void showSettingsDialog() {
		showWindowDialog(GameSettings.prepareSettingsWindow());
	}

	// private final static int CREDITS_PANE_HEIGHT = 200;
	// public void showCreditsDialog() {
	// showWindowDialog(new Dialog(LocalizationManager.INSTANCE.getText(Text.CREDITS_TITLE),
	// InterfaceManager.INSTANCE.getInterfaceStyle(), WindowStyle.DIALOG.getStyleName()) {
	//
	// {
	// applyDefaultDialogPadding(this);
	// getContentTable().add(
	// WidgetFactory.createScrollPane(createDialogLabelsTable(), ScrollPaneStyle.DEFAULT))
	// .height(CREDITS_PANE_HEIGHT);
	//
	// button(WidgetFactory.createTextButton(Text.BACK, TextButtonStyle.DEFAULT));
	// }
	//
	// private Table createDialogLabelsTable() {
	// Table labelsTable = new Table();
	// for (Text line : MultilineText.CREDITS_LABEL.getLines()) {
	// labelsTable.add(WidgetFactory.createLabel(line, LabelStyle.DEFAULT_BLUE));
	// labelsTable.row();
	// }
	// return labelsTable;
	// }
	// });
	// }
}
