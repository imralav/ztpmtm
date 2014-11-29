package pl.edu.pb.wi.ztpmtm.gui.assets;

public enum MultilineText {
	/** Settings dialog. */
	CANCEL_SETTINGS_PROMPT(
			Text.CANCEL_SETTINGS_PROMPT0,
			Text.CANCEL_SETTINGS_PROMPT1,
			Text.CANCEL_SETTINGS_PROMPT2),
	LANGUAGE_CHANGE_PROMPT(
			Text.LANGUAGE_CHANGE_PROMPT0,
			Text.LANGUAGE_CHANGE_PROMPT1,
			Text.LANGUAGE_CHANGE_PROMPT2),
	EXIT_SETTINGS_WITHOUT_SAVING_PROMPT(
			Text.EXIT_SETTINGS_WITHOUT_SAVING_PROMPT0,
			Text.EXIT_SETTINGS_WITHOUT_SAVING_PROMPT1,
			Text.EXIT_SETTINGS_WITHOUT_SAVING_PROMPT2);

	private final Text[] textLines;

	private MultilineText(Text... textLines) {
		this.textLines = textLines;
	}

	public Text[] getLines() {
		return textLines;
	}
}
