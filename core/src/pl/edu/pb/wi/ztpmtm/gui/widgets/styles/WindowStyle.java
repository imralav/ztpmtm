package pl.edu.pb.wi.ztpmtm.gui.widgets.styles;

import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;

public enum WindowStyle implements WidgetStyle {
	DEFAULT("default", new Padding(30, 10, 10, 10)),
	DIALOG("dialog", new Padding(42, 16, 32, 16)),
	TRANSPARENT("transparent", Padding.PAD_0);

	private final String styleName;
	private final Padding defaultPadding;

	private WindowStyle(String styleName, Padding defaultPadding) {
		this.styleName = styleName;
		this.defaultPadding = defaultPadding;
	}

	@Override
	public String getStyleName() {
		return styleName;
	}

	/** @return padding of the window that should be applied upon creation. */
	public Padding getDefaultPadding() {
		return defaultPadding;
	}

}
