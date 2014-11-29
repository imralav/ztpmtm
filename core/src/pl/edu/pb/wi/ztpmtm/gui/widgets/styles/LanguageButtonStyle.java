package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

import pl.edu.pb.wi.ztpmtm.preferences.LanguageLocale;

/** Contains all styles of Scene2D button widget used to represent country flags.
 * 
 * @author MJ */
public enum LanguageButtonStyle implements WidgetStyle {
	POLISH_FLAG("flag-pl", LanguageLocale.POLISH),
	ENGLISH_FLAG("flag-en", LanguageLocale.ENGLISH);

	private final String styleName;
	private final LanguageLocale languageLocale;

	private LanguageButtonStyle(String styleName, LanguageLocale languageLocale) {
		this.styleName = styleName;
		this.languageLocale = languageLocale;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

	/** @return language locale connected with the button. */
	public LanguageLocale getLanguageLocale() {
		return languageLocale;
	}

}