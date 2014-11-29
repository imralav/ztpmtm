package pl.edu.pb.wi.ztpmtm.gui.utilities.builders;

import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.WidgetData;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OneColumnTableBuilder extends AbstractTableBuilder {
	public OneColumnTableBuilder() {
		super();
	}

	public OneColumnTableBuilder(Padding defaultWidgetPadding) {
		super(defaultWidgetPadding);
	}

	public OneColumnTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount) {
		super(estimatedWidgetsAmount, estimatedRowsAmount);
	}

	public OneColumnTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount,
			Padding defaultWidgetPadding) {
		super(estimatedWidgetsAmount, estimatedRowsAmount, defaultWidgetPadding);
	}

	public Table build() {
		Table table = prepareTable();

		for (WidgetData widget : getWidgets()) {
			addWidgetToTable(table, widget, 1);
			table.row();
		}

		return prepareBuiltTable(table);
	}
}
