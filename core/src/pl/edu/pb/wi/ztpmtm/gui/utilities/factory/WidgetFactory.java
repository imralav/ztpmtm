package pl.edu.pb.wi.ztpmtm.gui.utilities.factory;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;
import pl.edu.pb.wi.ztpmtm.gui.widgets.Background;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.BackgroundStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.CheckBoxStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LabelStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.LanguageButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ListStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ProgressBarStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.ScrollPaneStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.SliderStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextButtonStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.TextFieldStyle;
import pl.edu.pb.wi.ztpmtm.gui.widgets.styles.WindowStyle;
import pl.edu.pb.wi.ztpmtm.managers.LocalizationManager;
import pl.edu.pb.wi.ztpmtm.managers.gui.InterfaceManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Utility class. Provides static methods for easier widget creation.
 * 
 * @author MJ */
public class WidgetFactory {
	private WidgetFactory() {
	}

	public static Background createBackground(BackgroundStyle backgroundStyle) {
		return new Background(InterfaceManager.INSTANCE.getInterfaceStyle(), backgroundStyle.getStyleName());
	}

	public static Button createLanguageChoiceButton(LanguageButtonStyle buttonStyle) {
		Button button = new Button(InterfaceManager.INSTANCE.getInterfaceStyle(), buttonStyle.getStyleName());

		button.addListener(LocalizationManager.getLanguageChangingClickListener(buttonStyle
				.getLanguageLocale()));

		return button;
	}

	public static CheckBox createCheckBox(Text label, ChangeListener changeListener,
			CheckBoxStyle checkBoxStyle) {
		return createCheckBox(LocalizationManager.INSTANCE.getText(label), changeListener, checkBoxStyle,
				false);
	}

	public static CheckBox createCheckBox(String label, ChangeListener changeListener,
			CheckBoxStyle checkBoxStyle) {
		return createCheckBox(label, changeListener, checkBoxStyle, false);
	}

	public static CheckBox createCheckBox(Text label, ChangeListener changeListener,
			CheckBoxStyle checkBoxStyle, boolean isChecked) {
		return createCheckBox(LocalizationManager.INSTANCE.getText(label), changeListener, checkBoxStyle,
				isChecked);
	}

	public static CheckBox createCheckBox(String label, ChangeListener changeListener,
			CheckBoxStyle checkBoxStyle, boolean isChecked) {
		CheckBox checkBox =
				new CheckBox(label, InterfaceManager.INSTANCE.getInterfaceStyle(),
						checkBoxStyle.getStyleName());
		checkBox.setChecked(isChecked);
		checkBox.addListener(changeListener);

		return checkBox;
	}

	public static Label createLabel(Text label, LabelStyle labelStyle) {
		return new Label(LocalizationManager.INSTANCE.getText(label),
				InterfaceManager.INSTANCE.getInterfaceStyle(), labelStyle.getStyleName());
	}

	public static Label createLabel(Text label, LabelStyle labelStyle, Object... arguments) {
		return new Label(LocalizationManager.INSTANCE.getText(label, arguments),
				InterfaceManager.INSTANCE.getInterfaceStyle(), labelStyle.getStyleName());
	}

	public static Label createLabel(String label, LabelStyle labelStyle) {
		return new Label(label, InterfaceManager.INSTANCE.getInterfaceStyle(), labelStyle.getStyleName());
	}

	public static List<String> createList(ListStyle listStyle) {
		return new List<String>(InterfaceManager.INSTANCE.getInterfaceStyle(), listStyle.getStyleName());
	}

	public static ProgressBar createLoadingBar(ProgressBarStyle progressBarStyle) {
		ProgressBar progressBar = createProgressBar(0f, 1f, 0.005f, progressBarStyle);

		progressBar.setValue(progressBarStyle.getInitialValue());
		progressBar.setAnimateDuration(0.25f);

		return progressBar;
	}

	public static ProgressBar createProgressBar(float min, float max, float stepSize,
			ProgressBarStyle progressBarStyle) {
		ProgressBar progressBar =
				new ProgressBar(min, max, stepSize, false, InterfaceManager.INSTANCE.getInterfaceStyle(),
						progressBarStyle.getStyleName());

		return progressBar;
	}

	public static Slider createSlider(ChangeListener changeListener, float initialValue,
			SliderStyle sliderStyle) {
		return createSlider(0f, 1f, 0.01f, initialValue, changeListener, sliderStyle);
	}

	public static Slider createSlider(float min, float max, float stepSize, float initialValue,
			ChangeListener changeListener, SliderStyle sliderStyle) {
		Slider slider =
				new Slider(min, max, stepSize, false, InterfaceManager.INSTANCE.getInterfaceStyle(),
						sliderStyle.getStyleName());
		slider.setValue(initialValue);
		if (changeListener != null) {
			slider.addListener(changeListener);
		}

		return slider;
	}

	public static ScrollPane createScrollPane(Actor widget, ScrollPaneStyle scrollPaneStyle) {
		ScrollPane scrollPane =
				new ScrollPane(widget, InterfaceManager.INSTANCE.getInterfaceStyle(),
						scrollPaneStyle.getStyleName());
		scrollPane.setFadeScrollBars(scrollPaneStyle.hideKnobs());
		scrollPane.setOverscroll(scrollPaneStyle.enableOverscroll(), scrollPaneStyle.enableOverscroll());

		return scrollPane;
	}

	public static TextButton createTextButton(Text label, TextButtonStyle textButtonStyle) {
		return createTextButton(LocalizationManager.INSTANCE.getText(label), null, textButtonStyle);
	}

	public static TextButton createTextButton(String label, TextButtonStyle textButtonStyle) {
		return createTextButton(label, null, textButtonStyle);
	}

	public static TextButton createTextButton(Text label, ClickListener clickListener,
			TextButtonStyle textButtonStyle) {
		return createTextButton(LocalizationManager.INSTANCE.getText(label), clickListener, textButtonStyle);
	}

	public static TextButton createTextButton(String label, ClickListener clickListener,
			TextButtonStyle textButtonStyle) {
		TextButton textButton =
				new TextButton(label, InterfaceManager.INSTANCE.getInterfaceStyle(),
						textButtonStyle.getStyleName());
		if (clickListener != null) {
			textButton.addListener(clickListener);
		}

		return textButton;
	}

	public static TextField createTextField(Text initialText, int characterLimit,
			InputListener inputListener, TextFieldStyle textFieldStyle) {
		return createTextField(LocalizationManager.INSTANCE.getText(initialText), characterLimit,
				inputListener, textFieldStyle);
	}

	public static TextField createTextField(String initialText, int characterLimit,
			InputListener inputListener, TextFieldStyle textFieldStyle) {
		TextField textField =
				new TextField(initialText, InterfaceManager.INSTANCE.getInterfaceStyle(),
						textFieldStyle.getStyleName());

		textField.setOnlyFontChars(true);
		textField.setMaxLength(characterLimit);
		textField.setCursorPosition(initialText.length());
		textField.setFocusTraversal(true);

		if (inputListener != null) {
			textField.addListener(inputListener);
		}

		return textField;
	}

	public static Window createWindow(Text title, WindowStyle windowStyle) {
		return createWindow(LocalizationManager.INSTANCE.getText(title), true, windowStyle);
	}

	public static Window createWindow(String title, WindowStyle windowStyle) {
		return createWindow(title, true, windowStyle);
	}

	public static Window createWindow(Text title, boolean isMovable, WindowStyle windowStyle) {
		return createWindow(LocalizationManager.INSTANCE.getText(title), isMovable, windowStyle);
	}

	public static Window createWindow(String title, boolean isMovalbe, WindowStyle windowStyle) {
		Window window =
				new Window(title, InterfaceManager.INSTANCE.getInterfaceStyle(), windowStyle.getStyleName());
		window.setMovable(isMovalbe);
		windowStyle.getDefaultPadding().applyPadding(window);

		return window;
	}

}
