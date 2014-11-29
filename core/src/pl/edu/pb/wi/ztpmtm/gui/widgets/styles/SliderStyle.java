package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum SliderStyle implements WidgetStyle {
	DEFAULT("default");

	private final String styleName;

	private SliderStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}
