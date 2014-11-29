package pl.edu.pb.wi.ztpmtm.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import pl.edu.pb.wi.ztpmtm.Core;

public class HtmlLauncher extends GwtApplication {
	private final static int DEFAULT_WIDTH = 1024, DEFAULT_HEIGHT = 512;

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return Core.INSTANCE;
	}
}