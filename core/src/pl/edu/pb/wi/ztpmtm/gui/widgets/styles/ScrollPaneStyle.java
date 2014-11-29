package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum ScrollPaneStyle implements WidgetStyle {
	DEFAULT("default", true, false),
	NO_KNOB_FADING("default", false, false),
	NO_SCROLLS("no-scrolls", false, false);

	private final String styleName;
	private final boolean hideKnobs, enableOverscroll;

	private ScrollPaneStyle(String styleName, boolean hideKnobs, boolean enableOverscroll) {
		this.styleName = styleName;
		this.hideKnobs = hideKnobs;
		this.enableOverscroll = enableOverscroll;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

	/** @return true if the style should hide knobs after inactivity. */
	public boolean hideKnobs() {
		return hideKnobs;
	}

	/** @return true if the scroll pane should allow overscrolling. */
	public boolean enableOverscroll() {
		return enableOverscroll;
	}

}
