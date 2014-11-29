package pl.edu.pb.wi.ztpmtm.game;

import pl.edu.pb.wi.ztpmtm.gui.assets.Text;

public enum GameDifficulty {
	EASY(Text.DIFF_EASY),
	HARD(Text.DIFF_HARD);

	private final Text text;

	private GameDifficulty(Text text) {
		this.text = text;
	}

	public Text getText() {
		return text;
	}

	public int getIndex() {
		return ordinal();
	}
}
