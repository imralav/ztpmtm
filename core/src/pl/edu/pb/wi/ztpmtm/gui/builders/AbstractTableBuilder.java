package pl.edu.pb.wi.ztpmtm.gui.builders;

import pl.edu.pb.wi.ztpmtm.gui.Padding;
import pl.edu.pb.wi.ztpmtm.gui.WidgetData;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class AbstractTableBuilder {
	private final static int DEFAULT_WIDGETS_AMOUNT = 10, DEFAULT_ROWS_AMOUNT = 3;

	private final Array<WidgetData> widgets;
	// Settings.
	private Padding widgetPadding, tablePadding;
	// Control variables.
	private final IntArray rowSizes;
	private int currentRowSize;

	public AbstractTableBuilder() {
		this(DEFAULT_WIDGETS_AMOUNT, DEFAULT_ROWS_AMOUNT, Padding.PAD_0);
	}

	public AbstractTableBuilder(Padding defaultWidgetPadding) {
		this(DEFAULT_WIDGETS_AMOUNT, DEFAULT_ROWS_AMOUNT, defaultWidgetPadding);
	}

	public AbstractTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount) {
		this(estimatedWidgetsAmount, estimatedRowsAmount, Padding.PAD_0);
	}

	public AbstractTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount,
			Padding defaultWidgetPadding) {
		widgets = new Array<WidgetData>(estimatedWidgetsAmount);
		rowSizes = new IntArray(estimatedRowsAmount);
		widgetPadding = defaultWidgetPadding;
	}

	public void setTablePadding(Padding tablePadding) {
		this.tablePadding = tablePadding;
	}

	public AbstractTableBuilder append(Actor widget) {
		widgets.add(WidgetData.of(widget, widgetPadding));
		currentRowSize++;

		return this;
	}

	public AbstractTableBuilder append(Actor widget, Padding padding) {
		widgets.add(WidgetData.of(widget, padding));
		currentRowSize++;

		return this;
	}

	public AbstractTableBuilder append(Actor widget, boolean expandAndFillXAxis, boolean expandAndFillYAxis) {
		widgets.add(WidgetData.of(widget, widgetPadding, expandAndFillXAxis, expandAndFillXAxis,
				expandAndFillXAxis, expandAndFillYAxis));
		currentRowSize++;

		return this;
	}

	public AbstractTableBuilder append(Actor widget, int width, int height) {
		widgets.add(WidgetData.of(widget, widgetPadding, width, height));
		currentRowSize++;

		return this;
	}

	public AbstractTableBuilder append(Actor widget, Padding padding, int width, int height) {
		widgets.add(WidgetData.of(widget, padding, width, height));
		currentRowSize++;

		return this;
	}

	public AbstractTableBuilder row() {
		rowSizes.add(currentRowSize);
		currentRowSize = 0;

		return this;
	}

	protected Table prepareTable() {
		validateRowSize();
		if (tablePadding != null) {
			return tablePadding.applyPadding(new Table());
		}
		return new Table();
	}

	private void validateRowSize() {
		if (currentRowSize != 0) {
			row();
		}
	}

	protected static Cell<?> addWidgetToTable(Table table, WidgetData widgetData, int widgetColspan) {
		Cell<?> widgetCell =
				widgetData.getPadding().applyPadding(table.add(widgetData.getWidget()))
						.colspan(widgetColspan);

		if (widgetData.isWidthSpecified()) {
			widgetCell.width(widgetData.getWidth());
		}
		if (widgetData.isHeightSpecified()) {
			widgetCell.width(widgetData.getHeight());
		}
		widgetCell.expand(widgetData.expandX(), widgetData.expandY());
		widgetCell.fill(widgetData.fillX(), widgetData.fillY());

		return widgetCell;
	}

	public static int getGreatestCommonDenominator(int valueA, int valueB) {
		return valueB == 0 ? valueA : getGreatestCommonDenominator(valueB, valueA % valueB);
	}

	public static int getLowestCommonMultiple(int valueA, int valueB) {
		return valueA * (valueB / getGreatestCommonDenominator(valueA, valueB));
	}

	public static int getLowestCommonMultiple(IntArray values) {
		int lowestCommonMultiple = values.first();
		for (int index = 1; index < values.size; index++) {
			lowestCommonMultiple = getLowestCommonMultiple(lowestCommonMultiple, values.get(index));
		}
		return lowestCommonMultiple;
	}

}
