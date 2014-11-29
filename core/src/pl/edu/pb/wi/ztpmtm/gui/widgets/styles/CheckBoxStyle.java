package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum CheckBoxStyle implements WidgetStyle {
	DEFAULT("default");

	private final String styleName;

	private CheckBoxStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}
