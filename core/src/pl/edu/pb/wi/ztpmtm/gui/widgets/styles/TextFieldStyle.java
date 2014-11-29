package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum TextFieldStyle implements WidgetStyle {
	DEFAULT("default", '*');

	private final String styleName;
	private final char passwordCharacter;

	private TextFieldStyle(String styleName, char passwordCharacter) {
		this.styleName = styleName;
		this.passwordCharacter = passwordCharacter;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

	/** @return character which replaces every other if the widget is set to password mode. */
	public char getPasswordCharacter() {
		return passwordCharacter;
	}

}
