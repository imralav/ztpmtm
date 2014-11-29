package pl.edu.pb.wi.ztpmtm.gui.assets;

public enum Text {
	/** General. */
	BACK("back"),
	YES("yes"),
	NO("no"),
	OR("or"),
	SAVE("save"),
	OK("ok"),
	CANCEL("cancel"),
	EXIT("exit"),
	EXIT_TITLE("exitTitle"),
	EXIT_PROMPT("exitPrompt"),
	CONNECTING_TITLE("connectingTitle"),
	CONNECTING_LABEL0("connectingLabel0"),
	CONNECTING_LABEL1("connectingLabel1"),
	SUCCESS_TITLE("successTitle"),
	WARNING_TITLE("warningTitle"),
	ERROR_TITLE("errorTitle"),
	TITLE0("title0"),
	TITLE1("title1"),

	/** Settings dialog. */
	SETTINGS("settings"),
	LANGUAGE_CHOICE_LABEL("languageChoiceLabel"),
	MUSIC_VOLUME_LABEL("musicVolumeLabel"),
	MUSIC_ON_LABEL("musicOnLabel"),
	SOUNDS_VOLUME_LABEL("soundsVolumeLabel"),
	SOUNDS_ON_LABEL("soundsOnLabel"),
	CANCEL_SETTINGS_PROMPT0("cancelSettingsPrompt0"),
	CANCEL_SETTINGS_PROMPT1("cancelSettingsPrompt1"),
	CANCEL_SETTINGS_PROMPT2("cancelSettingsPrompt2"),
	LANGUAGE_CHANGE_PROMPT0("languageChangePrompt0"),
	LANGUAGE_CHANGE_PROMPT1("languageChangePrompt1"),
	LANGUAGE_CHANGE_PROMPT2("languageChangePrompt2"),
	EXIT_SETTINGS_WITHOUT_SAVING_PROMPT0("exitSettingsWithoutSavingPrompt0"),
	EXIT_SETTINGS_WITHOUT_SAVING_PROMPT1("exitSettingsWithoutSavingPrompt1"),
	EXIT_SETTINGS_WITHOUT_SAVING_PROMPT2("exitSettingsWithoutSavingPrompt2"),

	/** Loading screen. */
	LOADING_TITLE("loadingTitle"),
	LOADING_PROGRESS_LABEL("loadingProgress"), 
	
	/** Menu screen. */
	MENU_TITLE("menuTitle"),
	START_BUTTON("startButton"),
	SETTINGS_BUTTON("settingsButton"),
	
	/** Difficulty choosing dialog. */
	DIFF_DIALOG_TITLE("diffDialogTitle"),
	DIFF_DIALOG_PROMPT("diffDialogPrompt"),
	DIFF_EASY("diffEasy"),
	DIFF_HARD("diffHard");

	private final String textName;

	private Text(String textName) {
		this.textName = textName;
	}

	public String getName() {
		return textName;
	}

}
