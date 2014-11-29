package pl.edu.pb.wi.ztpmtm.preferences;

import pl.edu.pb.wi.ztpmtm.gui.assets.MultilineText;
import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.TableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.CenteredTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.CheckBoxStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LanguageButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.SliderStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.managers.MusicManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/** Contains utilities for game settings.
 * 
 * @author MJ */
public class GameSettings {
	private GameSettings() {
	}

	public static Dialog prepareSettingsWindow() {
		return new Dialog(LocalizationManager.INSTANCE.getText(Text.SETTINGS),
				InterfaceManager.INSTANCE.getInterfaceStyle(), WindowStyle.DIALOG.getStyleName()) {
			{
				DialogManager.applyDefaultDialogPadding(this);
				getContentTable().add(prepareContentWidgets());
				addButtons();
			}

			private void addButtons() {
				button(WidgetFactory.createTextButton(Text.CANCEL, TextButtonStyle.DEFAULT),
						prepareCancelAction());
				button(WidgetFactory.createTextButton(Text.OK, TextButtonStyle.DEFAULT), prepareExitAction());
				button(WidgetFactory.createTextButton(Text.SAVE, TextButtonStyle.DEFAULT),
						prepareSavingAction());
			}

			private Runnable prepareCancelAction() {
				return new Runnable() {
					@Override
					public void run() {
						showCancelPromptDialog();
					}
				};
			}

			private void showCancelPromptDialog() {
				DialogManager.INSTANCE.showSimpleChoicePopUp(Text.WARNING_TITLE, new Runnable() {
					@Override
					public void run() {
						reloadPreferences();
						hide();
					}
				}, DialogManager.NO_ACTION, MultilineText.CANCEL_SETTINGS_PROMPT);
			}

			private Runnable prepareExitAction() {
				return new Runnable() {
					@Override
					public void run() {
						showExitPromptDialog();
					}
				};
			}

			private void showExitPromptDialog() {
				DialogManager.INSTANCE.showSimpleChoicePopUp(Text.WARNING_TITLE, new Runnable() {
					@Override
					public void run() {
						hide();
					}
				}, DialogManager.NO_ACTION, MultilineText.EXIT_SETTINGS_WITHOUT_SAVING_PROMPT);
			}

			private Runnable prepareSavingAction() {
				return new Runnable() {
					@Override
					public void run() {
						saveSettingsInPreferences();
						hide();
					}
				};
			}

			private Actor prepareContentWidgets() {
				TableBuilder tableBuilder = new CenteredTableBuilder(Padding.PAD_2);
				appendLanguageChoiceButtons(tableBuilder);
				appendMusicRelatedWidgets(tableBuilder);
				appendSoundsRelatedWidgets(tableBuilder);
				return tableBuilder.build();
			}

			private void appendLanguageChoiceButtons(TableBuilder tableBuilder) {
				tableBuilder.append(WidgetFactory.createLabel(Text.LANGUAGE_CHOICE_LABEL,
						LabelStyle.DEFAULT_BLACK));
				for (LanguageButtonStyle languageButtonStyle : LanguageButtonStyle.values()) {
					if (languageButtonStyle.getLanguageLocale() != LocalizationManager.INSTANCE
							.getCurrentLocale()) {
						tableBuilder.append(WidgetFactory.createLanguageChoiceButton(languageButtonStyle));
					}
				}
				tableBuilder.row();
			}

			private void appendMusicRelatedWidgets(TableBuilder tableBuilder) {
				tableBuilder.append(
						WidgetFactory.createLabel(Text.MUSIC_VOLUME_LABEL, LabelStyle.DEFAULT_DARK_BLUE))
						.row();
				tableBuilder.append(WidgetFactory.createSlider(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						MusicManager.INSTANCE.setMusicVolume(((Slider) actor).getValue());
					}
				}, MusicManager.INSTANCE.getMusicVolume(), SliderStyle.DEFAULT), true, false).row();
				tableBuilder.append(WidgetFactory.createCheckBox(Text.MUSIC_ON_LABEL, new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						MusicManager.INSTANCE.setMusicOn(((CheckBox) actor).isChecked());
					}
				}, CheckBoxStyle.DEFAULT, MusicManager.INSTANCE.isMusicOn())).row();
			}

			private void appendSoundsRelatedWidgets(TableBuilder tableBuilder) {
				tableBuilder.append(
						WidgetFactory.createLabel(Text.SOUNDS_VOLUME_LABEL, LabelStyle.DEFAULT_DARK_BLUE))
						.row();
				tableBuilder.append(WidgetFactory.createSlider(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						MusicManager.INSTANCE.setSoundsVolume(((Slider) actor).getValue());
					}
				}, MusicManager.INSTANCE.getSoundsVolume(), SliderStyle.DEFAULT), true, false).row();
				tableBuilder.append(WidgetFactory.createCheckBox(Text.SOUNDS_ON_LABEL, new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						MusicManager.INSTANCE.setSoundsOn(((CheckBox) actor).isChecked());
					}
				}, CheckBoxStyle.DEFAULT, MusicManager.INSTANCE.areSoundsOn())).row();
			}

			@Override
			protected void result(Object object) {
				if (object instanceof Runnable) {
					cancel();
					((Runnable) object).run();
				}
			}
		};
	}

	private static void reloadPreferences() {
		MusicManager.INSTANCE.loadMusicSettingsFromPreferences();
	}

	private static void saveSettingsInPreferences() {
		MusicManager.INSTANCE.saveSettingsInPreferences();
	}
}