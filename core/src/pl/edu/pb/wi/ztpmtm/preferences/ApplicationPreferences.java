package pl.edu.pb.wi.ztpmtm.preferences;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ApplicationPreferences {
	private ApplicationPreferences() {
	}

	private final static Map<String, Preferences> PREFERENCES = new HashMap<String, Preferences>();

	public static Preferences getPreferences() {
		return getPreferences(Asset.PREFERENCES.getPath());
	}

	public static Preferences getPreferences(String name) {
		if (PREFERENCES.containsKey(name)) {
			return PREFERENCES.get(name);
		} else {
			Preferences preferences = Gdx.app.getPreferences(name);
			PREFERENCES.put(name, preferences);
			return preferences;
		}
	}

	public static void setPreferenceWithFlush(Preference preferenceKey, String preferenceValue) {
		Preferences preferences = getPreferences();
		preferences.putString(preferenceKey.getName(), preferenceValue);
		preferences.flush();
	}

	public static void setPreference(Preferences preferences, Preference preferenceKey, String preferenceValue) {
		preferences.putString(preferenceKey.getName(), preferenceValue);
	}

}
