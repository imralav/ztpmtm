package pl.edu.pb.wi.ztpmtm.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/** Wzorczec kreacyjny: budowniczy. TableBuilder pozwala prosto utworzyc interfejs gry, korzystajac z
 * biblioteki Scene2D. */
public interface TableBuilder {
	public void setTablePadding(Padding tablePadding);

	public TableBuilder append(Actor widget);

	public TableBuilder append(Actor widget, Padding padding);

	public TableBuilder append(Actor widget, boolean expandAndFillXAxis, boolean expandAndFillYAxis);

	public TableBuilder append(Actor widget, int width, int height);

	public TableBuilder append(Actor widget, Padding padding, int width, int height);

	public TableBuilder row();

	/** Wzorzec czynnosciowy: metoda szablonowa. build() buduje tabele interfejsu w zaleznosci od
	 * zainicjowanego typu budowniczego. */
	public Table build();

}
