package pl.edu.pb.wi.ztpmtm.gui.utilities;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.utilities.builders.OneColumnTableBuilder;
import pl.edu.pb.wi.ztpmtm.gui.utilities.factory.WidgetFactory;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/** Utility class with some methods that make interface management and creation a bit easier.
 * 
 * @author MJ */
public class InterfaceUtilities {
	private InterfaceUtilities() {
	}

	public static final String EMPTY_TITLE = "";

	public static void centerWindow(Window window) {
		window.setPosition((int) (window.getStage().getWidth() / 2f - window.getWidth() / 2f), (int) (window
				.getStage().getHeight() / 2f - window.getHeight() / 2f));
	}

	public static void updateWindowPosition(Window window, float oldWidth, float oldHeight, float newWidth,
			float newHeight) {
		window.setPosition((int) ((((window.getX() + window.getWidth() / 2f) / oldWidth) * newWidth) - window
				.getWidth() / 2f),
				(int) ((((window.getY() + window.getHeight() / 2f) / oldHeight) * newHeight) - window
						.getHeight() / 2f));
	}

	public static Actor createGameTitle() {
		TableBuilder tableBuilder = new OneColumnTableBuilder();
		tableBuilder.append(WidgetFactory.createLabel(Text.TITLE0, LabelStyle.BIG_DARK_BLUE));
		tableBuilder.append(WidgetFactory.createLabel(Text.TITLE1, LabelStyle.BIG_BLUE));
		return tableBuilder.build();
	}
}
