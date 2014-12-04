package pl.edu.pb.wi.ztpmtm.desktop;

import pl.edu.pb.wi.ztpmtm.Core;
import pl.edu.pb.wi.ztpmtm.gui.assets.Asset;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

/** Launches desktop application.
 * 
 * @author MJ */
public class DesktopLauncher {
	private static final String KONFIGURATS = "Konfigurats";
	private final static int DEFAULT_WIDTH = 1024/2, DEFAULT_HEIGHT = 512/2;

	public static void main(String[] arg) {
		new LwjglApplication(Core.INSTANCE, getDefaultConfiguration());
	}

	private final static LwjglApplicationConfiguration getDefaultConfiguration() {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = KONFIGURATS;
		configuration.initialBackgroundColor = new Color(0.0f, 0.0f, 0.1f, 1f);
		setSize(configuration);
		addIcons(configuration);
		return configuration;
	}

	private static void setSize(LwjglApplicationConfiguration configuration) {
		configuration.width = DEFAULT_WIDTH;
		configuration.height = DEFAULT_HEIGHT;
	}

	private static void addIcons(LwjglApplicationConfiguration configuration) {
		configuration.addIcon(Asset.GAME_ICON_BIG.getPath(), FileType.Internal);
		configuration.addIcon(Asset.GAME_ICON_MEDIUM.getPath(), FileType.Internal);
		configuration.addIcon(Asset.GAME_ICON_SMALL.getPath(), FileType.Internal);
	}
}