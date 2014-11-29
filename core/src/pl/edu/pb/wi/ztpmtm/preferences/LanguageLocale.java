package pl.edu.pb.wi.ztpmtm.preferences;

import java.util.Locale;

public enum LanguageLocale {
	ENGLISH("en"),
	POLISH("pl");

	private final Locale locale;
	private final String localeName;

	private LanguageLocale(String localeName) {
		this.locale = new Locale(localeName);
		this.localeName = localeName;
	}

	public Locale getLocale() {
		return locale;
	}

	public static LanguageLocale getByName(String localeName) throws IllegalArgumentException {
		for (LanguageLocale languageLocale : LanguageLocale.values()) {
			if (languageLocale.localeName.equals(localeName)) {
				return languageLocale;
			}
		}
		throw new IllegalArgumentException("Unknown locale name.");
	}
}
