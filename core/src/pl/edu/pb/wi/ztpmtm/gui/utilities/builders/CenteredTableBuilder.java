package pl.edu.pb.wi.ztpmtm.gui.utilities.builders;

import pl.edu.pb.wi.ztpmtm.gui.utilities.Padding;
import pl.edu.pb.wi.ztpmtm.gui.utilities.WidgetData;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CenteredTableBuilder extends AbstractTableBuilder {
	public CenteredTableBuilder() {
		super();
	}

	public CenteredTableBuilder(Padding defaultWidgetPadding) {
		super(defaultWidgetPadding);
	}

	public CenteredTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount) {
		super(estimatedWidgetsAmount, estimatedRowsAmount);
	}

	public CenteredTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount,
			Padding defaultWidgetPadding) {
		super(estimatedWidgetsAmount, estimatedRowsAmount, defaultWidgetPadding);
	}

	@Override
	public Table build() {
		Table table = prepareTable();
		int widgetsInRow = getLowestCommonMultiple(getRowSizes());

		for (int rowIndex = 0, widgetIndex = 0; rowIndex < getRowSizes().size; rowIndex++) {
			int rowSize = getRowSizes().get(rowIndex);
			int currentWidgetColspan = widgetsInRow / rowSize;
			boolean isFirst = shouldExpand(rowSize);

			for (int totalWidgets = widgetIndex + rowSize; widgetIndex < totalWidgets; widgetIndex++) {
				// Trying to keep the widgets together - expanding X for first and last widget, setting
				// alignments:
				if (isFirst) {
					isFirst = false;
					addRightAlignedWidgetToTable(table, getWidgets().get(widgetIndex), currentWidgetColspan);
				} else if (isLast(widgetIndex, rowSize, totalWidgets)) {
					addLeftAlignedWidgetToTable(table, getWidgets().get(widgetIndex), currentWidgetColspan);
				} else {
					addWidgetToTable(table, getWidgets().get(widgetIndex), currentWidgetColspan);
				}
			}

			table.row();
		}

		return prepareBuiltTable(table);
	}

	private boolean shouldExpand(int rowSize) {
		if (rowSize != 1) {
			return true;
		}
		return false;
	}

	private boolean isLast(int widgetIndex, int rowSize, int totalWidgets) {
		return rowSize != 1 && widgetIndex == totalWidgets - 1;
	}

	private Cell<?> addRightAlignedWidgetToTable(Table table, WidgetData widgetData, int widgetColspan) {
		return addWidgetToTable(table, widgetData, widgetColspan).expandX().right();
	}

	private Cell<?> addLeftAlignedWidgetToTable(Table table, WidgetData widgetData, int widgetColspan) {
		return addWidgetToTable(table, widgetData, widgetColspan).expandX().left();
	}
}
