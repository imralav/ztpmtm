package pl.edu.pb.wi.ztpmtm.preferences;

public enum Preference {
	LANGUAGE_LOCALE("locale"),
	MUSIC_ON("musicon"),
	SOUNDS_ON("soundson"),
	MUSIC_VOLUME("musicvolume"),
	SOUNDS_VOLUME("soundsvolume");

	private final String name;

	private Preference(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
