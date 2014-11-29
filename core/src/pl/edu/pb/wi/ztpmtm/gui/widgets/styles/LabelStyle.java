package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

public enum LabelStyle implements WidgetStyle {
	DEFAULT("default"),
	DEFAULT_WHITE("default-white"),
	DEFAULT_BLACK("default-black"),
	DEFAULT_LIGHT_BLUE("default-light-blue"),
	DEFAULT_BLUE("default-blue"),
	DEFAULT_DARK_BLUE("default-dark-blue"),
	DEFAULT_PURPLE("default-purple"),
	DEFAULT_RED("default-red"),
	BIG("big"),
	BIG_WHITE("big-white"),
	BIG_BLACK("big-black"),
	BIG_BLUE("big-blue"),
	BIG_DARK_BLUE("big-dark-blue"),
	HUGE("huge"),
	HUGE_WHITE("huge-white"),
	HUGE_BLACK("huge-black"),
	HUGE_BLUE("huge-blue"),
	HUGE_DARK_BLUE("huge-dark-blue"), ;

	private final String styleName;

	private LabelStyle(String styleName) {
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}
}
