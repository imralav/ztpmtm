package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum FontStyle implements WidgetStyle {
	REGULAR("regular"),
	BIG("big"),
	HUGE("huge");

	private final String styleName;

	private FontStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}