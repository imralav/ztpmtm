package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum ProgressBarStyle implements WidgetStyle {
	DEFAULT("default", 0.02f);

	private final String styleName;
	private final float initialValue;

	private ProgressBarStyle(String styleName, float initialValue) {
		this.styleName = styleName;
		this.initialValue = initialValue;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

	/** If used as a loading bar, this value should be set as the starting value to make sure the widget won't
	 * look deformed. */
	public float getInitialValue() {
		return initialValue;
	}
}
