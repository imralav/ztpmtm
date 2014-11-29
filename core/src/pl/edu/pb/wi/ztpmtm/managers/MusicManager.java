package pl.edu.pb.wi.ztpmtm.managers;

import pl.edu.pb.wi.ztpmtm.preferences.ApplicationPreferences;
import pl.edu.pb.wi.ztpmtm.preferences.Preference;

import com.badlogic.gdx.Preferences;

public enum MusicManager implements ApplicationManager {
	INSTANCE;

	private final static float DEFAULT_VOLUME = 1f, MIN_VOLUME = 0f, MAX_VOLUME = 1f;
	private final static boolean DEFAULT_ON_SETTING = true;

	// Control variables:
	private float musicVolume, soundsVolume;
	private boolean musicOn, soundsOn;

	@Override
	public void create() {
		loadMusicSettingsFromPreferences();
	}

	public void loadMusicSettingsFromPreferences() {
		Preferences preferences = ApplicationPreferences.getPreferences();
		musicOn = preferences.getBoolean(Preference.MUSIC_ON.getName(), DEFAULT_ON_SETTING);
		soundsOn = preferences.getBoolean(Preference.SOUNDS_ON.getName(), DEFAULT_ON_SETTING);
		musicVolume = preferences.getFloat(Preference.MUSIC_VOLUME.getName(), DEFAULT_VOLUME);
		soundsVolume = preferences.getFloat(Preference.SOUNDS_VOLUME.getName(), DEFAULT_VOLUME);
	}

	@Override
	public void dispose() {
		// Music files are disposed by asset manager.
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		// TODO modify current music volume
		this.musicVolume = Math.max(Math.min(musicVolume, MAX_VOLUME), MIN_VOLUME);
	}

	public float getSoundsVolume() {
		return soundsVolume;
	}

	public void setSoundsVolume(float soundsVolume) {
		this.soundsVolume = Math.max(Math.min(soundsVolume, MAX_VOLUME), MIN_VOLUME);
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public void setMusicOn(boolean musicOn) {
		// TODO stop/start playing music
		this.musicOn = musicOn;
	}

	public boolean areSoundsOn() {
		return soundsOn;
	}

	public void setSoundsOn(boolean soundsOn) {
		this.soundsOn = soundsOn;

	}

	public void saveSettingsInPreferences() {
		Preferences preferences = ApplicationPreferences.getPreferences();
		preferences.putBoolean(Preference.MUSIC_ON.getName(), musicOn);
		preferences.putBoolean(Preference.SOUNDS_ON.getName(), soundsOn);
		preferences.putFloat(Preference.MUSIC_VOLUME.getName(), musicVolume);
		preferences.putFloat(Preference.SOUNDS_VOLUME.getName(), soundsVolume);
		preferences.flush();
	}

}
