package pl.edu.pb.wi.ztpmtm.gui.utilities.builders;

import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StandardTableBuilder extends AbstractTableBuilder {
	public StandardTableBuilder() {
		super();
	}

	public StandardTableBuilder(Padding defaultWidgetPadding) {
		super(defaultWidgetPadding);
	}

	public StandardTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount) {
		super(estimatedWidgetsAmount, estimatedRowsAmount);
	}

	public StandardTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount,
			Padding defaultWidgetPadding) {
		super(estimatedWidgetsAmount, estimatedRowsAmount, defaultWidgetPadding);
	}

	public Table build() {
		Table table = prepareTable();

		int widgetsInRow = getLowestCommonMultiple(getRowSizes());
		for (int rowIndex = 0, widgetIndex = 0; rowIndex < getRowSizes().size; rowIndex++) {
			int rowSize = getRowSizes().get(rowIndex);
			int currentWidgetColspan = widgetsInRow / rowSize;
			for (int totalWidgets = widgetIndex + rowSize; widgetIndex < totalWidgets; widgetIndex++) {
				addWidgetToTable(table, getWidgets().get(widgetIndex), currentWidgetColspan);
			}
			table.row();
		}

		return prepareBuiltTable(table);
	}
}
