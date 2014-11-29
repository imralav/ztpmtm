package pl.edu.pb.wi.ztpmtm.gui.utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class WidgetData {
	public final static int IGNORED_SIZE_VALUE = -1;
	public final static boolean NO_EXPAND = false, NO_FILL = false;
	private final Actor widget;
	private final Padding padding;
	private final int width, height;
	private final boolean expandX, expandY, fillX, fillY;

	private WidgetData(Actor widget, Padding padding, int width, int height, boolean expandX,
			boolean expandY, boolean fillX, boolean fillY) {
		this.widget = widget;
		this.padding = padding;
		this.width = width;
		this.height = height;
		this.expandX = expandX;
		this.expandY = expandY;
		this.fillX = fillX;
		this.fillY = fillY;
	}

	// Wzorzec kreacyjny: metody fabrykujace. WidgetData przechowuje dane o aktorach (kontrolkach), ktore maja
	// byc dodane do interfejsu, ale nie moga byc ustawione od razu (w chwili dodawania do buildera).
	// Dzieki wielu metodom of(...), mozna szybko utworzyc dane o obiektu przy uzyciu domyslnych parametrow.
	public static WidgetData of(Actor widget) {
		return new WidgetData(widget, Padding.PAD_0, IGNORED_SIZE_VALUE, IGNORED_SIZE_VALUE, NO_EXPAND,
				NO_EXPAND, NO_FILL, NO_FILL);
	}

	public static WidgetData of(Actor widget, Padding padding) {
		return new WidgetData(widget, padding == null ? Padding.PAD_0 : padding, IGNORED_SIZE_VALUE,
				IGNORED_SIZE_VALUE, NO_EXPAND, NO_EXPAND, NO_FILL, NO_FILL);
	}

	public static WidgetData of(Actor widget, Padding padding, boolean expandX, boolean expandY,
			boolean fillX, boolean fillY) {
		return new WidgetData(widget, padding, IGNORED_SIZE_VALUE, IGNORED_SIZE_VALUE, expandX, expandY,
				fillX, fillY);
	}

	public static WidgetData of(Actor widget, Padding padding, int width, int height) {
		return new WidgetData(widget, padding == null ? Padding.PAD_0 : padding, width, height, NO_EXPAND,
				NO_EXPAND, NO_FILL, NO_FILL);
	}

	public Actor getWidget() {
		return widget;
	}

	public Padding getPadding() {
		return padding;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean expandX() {
		return expandX;
	}

	public boolean expandY() {
		return expandY;
	}

	public boolean fillX() {
		return fillX;
	}

	public boolean fillY() {
		return fillY;
	}

	public boolean isWidthSpecified() {
		return width >= 0;
	}

	public boolean isHeightSpecified() {
		return height >= 0;
	}
}
