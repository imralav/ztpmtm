package pl.edu.pb.wi.ztpmtm.managers;

import java.util.Locale;

import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;
import pl.edu.pb.wi.ztpmtm.gui.assets.MultilineText;
import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.managers.gui.DialogManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;
import pl.edu.pb.wi.ztpmtm.preferences.ApplicationPreferences;
import pl.edu.pb.wi.ztpmtm.preferences.LanguageLocale;
import pl.edu.pb.wi.ztpmtm.preferences.Preference;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;

/** Deals with the internationalization and localization. Allows to format messages specified in the bundles.
 * Provides tool for easy game translating, without having to replace hard-coded strings or making ridiculous
 * if-elses.
 * 
 * @author MJ */
public enum LocalizationManager implements ApplicationManager {
	INSTANCE;

	private static final LanguageLocale DEFAULT_LOCALE = LanguageLocale.ENGLISH;
	private LanguageLocale currentLocale;
	private I18NBundle i18NBundle;

	@Override
	public void create() {
		I18NBundle.setSimpleFormatter(true);
		loadBundle();
	}

	/** Loads game texts with the locale defined in the preferences file or with the default locale. */
	private void loadBundle() {
		Preferences preferences = ApplicationPreferences.getPreferences();
		if (preferences.contains(Preference.LANGUAGE_LOCALE.getName())) {
			String localeName = preferences.getString(Preference.LANGUAGE_LOCALE.getName());
			currentLocale = LanguageLocale.getByName(localeName);
			reloadBundle(new Locale(localeName));
		} else {
			saveDefaultLocalePreferences(preferences);
			currentLocale = DEFAULT_LOCALE;
			reloadBundle(DEFAULT_LOCALE.getLocale());
		}
	}

	private void saveLocalePreferences(Preferences preferences, Locale locale) {
		ApplicationPreferences.setPreference(preferences, Preference.LANGUAGE_LOCALE, locale.getLanguage());
		preferences.flush();
	}

	private void saveDefaultLocalePreferences(Preferences preferences) {
		saveLocalePreferences(preferences, DEFAULT_LOCALE.getLocale());
	}

	/** @return currently used language locale. */
	public LanguageLocale getCurrentLocale() {
		return currentLocale;
	}

	/** @param languageLocale will be set as the current game's language. Reloads all screens. */
	private void changeLanguage(LanguageLocale languageLocale) {
		saveLocalePreferences(ApplicationPreferences.getPreferences(), languageLocale.getLocale());
		currentLocale = languageLocale;
		reloadBundle(languageLocale.getLocale());
		InterfaceManager.INSTANCE.reloadScreens();
	}

	@Override
	public void dispose() {
		// I18NBundle does not need to be disposed of.
		i18NBundle = null;
	}

	private void reloadBundle(Locale locale) {
		i18NBundle = I18NBundle.createBundle(Gdx.files.internal(Asset.I18N_BUNDLE.getPath()), locale);
	}

	public String getText(Text text) {
		return i18NBundle.format(text.getName());
	}

	public String[] getTexts(Text... texts) {
		String[] resultText = new String[texts.length];
		for (int index = 0; index < texts.length; index++) {
			resultText[index] = getText(texts[index]);
		}

		return resultText;
	}

	public String getText(Text text, Object... arguments) {
		return i18NBundle.format(text.getName(), arguments);
	}

	public static ClickListener getLanguageChangingClickListener(final LanguageLocale languageLocale) {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DialogManager.INSTANCE.showSimpleChoicePopUp(Text.WARNING_TITLE, new Runnable() {
					@Override
					public void run() {
						LocalizationManager.INSTANCE.changeLanguage(languageLocale);
					}
				}, DialogManager.NO_ACTION, MultilineText.LANGUAGE_CHANGE_PROMPT);
			}
		};
	}
}
