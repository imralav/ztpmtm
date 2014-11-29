package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum ListStyle implements WidgetStyle {
	DEFAULT("default");

	private final String styleName;

	private ListStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}
