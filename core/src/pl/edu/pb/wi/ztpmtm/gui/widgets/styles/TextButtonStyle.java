package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum TextButtonStyle implements WidgetStyle {
	DEFAULT("default"),
	TEXT_ONLY("text-only");

	private final String styleName;

	private TextButtonStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}
