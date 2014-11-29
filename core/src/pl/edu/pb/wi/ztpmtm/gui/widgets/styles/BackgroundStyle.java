package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum BackgroundStyle implements WidgetStyle {
	DEFAULT("default");

	private final String styleName;

	private BackgroundStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

}
