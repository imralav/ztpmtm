package pl.edu.pb.wi.ztpmtm.gui.builders;

import pl.edu.pb.wi.ztpmtm.gui.Padding;
import pl.edu.pb.wi.ztpmtm.gui.WidgetData;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class StandardTableBuilder extends AbstractTableBuilder {
	public StandardTableBuilder() {
		super();
	}

	public StandardTableBuilder(Padding defaultWidgetPadding) {
		super(defaultWidgetPadding);
	}

	public StandardTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount) {
		this(estimatedWidgetsAmount, estimatedRowsAmount, Padding.PAD_0);
	}

	public StandardTableBuilder(int estimatedWidgetsAmount, int estimatedRowsAmount,
			Padding defaultWidgetPadding) {
		widgets = new Array<WidgetData>(estimatedWidgetsAmount);
		rowSizes = new IntArray(estimatedRowsAmount);
		widgetPadding = defaultWidgetPadding;
	}

}
