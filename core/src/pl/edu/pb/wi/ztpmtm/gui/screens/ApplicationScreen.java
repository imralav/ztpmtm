package pl.edu.pb.wi.ztpmtm.gui.screens;

import pl.edu.pb.wi.ztpmtm.gui.widgets.Background;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public interface ApplicationScreen extends Disposable {
	public void create();

	public void resize(int width, int height);

	public void show();

	public void hide();

	public void dispose();

	public void render(float delta);

	public Stage getStage();

	public Background getBackground();
}
