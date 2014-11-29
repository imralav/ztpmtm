package pl.edu.pb.wi.ztpmtm.gui.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Padding {
	public static final Padding PAD_0 = new Padding(0f), PAD_2 = new Padding(2f), PAD_4 = new Padding(4f),
			PAD_8 = new Padding(8f);

	private final float top, left, bottom, right;

	public Padding(float padding) {
		this(padding, padding, padding, padding);
	}

	public Padding(float horizontal, float vertical) {
		this(vertical, horizontal, vertical, horizontal);
	}

	public Padding(float top, float left, float bottom, float right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}

	public float getTop() {
		return top;
	}

	public float getLeft() {
		return left;
	}

	public float getBottom() {
		return bottom;
	}

	public float getRight() {
		return right;
	}

	public Padding add(Padding padding) {
		return new Padding(top + padding.getTop(), left + padding.getLeft(), bottom + padding.getBottom(),
				right + padding.getRight());
	}

	public Padding subtract(Padding padding) {
		return new Padding(top - padding.getTop(), left - padding.getLeft(), bottom - padding.getBottom(),
				right - padding.getRight());
	}

	public Padding reverse() {
		return new Padding(-top, -left, -bottom, -right);
	}

	public Table applyPadding(Table table) {
		table.pad(top, left, bottom, right);
		return table;
	}

	public Cell<?> applyPadding(Cell<?> cell) {
		cell.pad(top, left, bottom, right);
		return cell;
	}

	public Cell<?> applySpacing(Cell<?> cell) {
		cell.space(top, left, bottom, right);
		return cell;
	}

	// Padding utilities:
	public static Table setPadding(Padding padding, Table table) {
		table.pad(padding.getTop(), padding.getLeft(), padding.getBottom(), padding.getRight());
		return table;
	}

	public static Cell<?> setPadding(Padding padding, Cell<?> cell) {
		return cell.pad(padding.getTop(), padding.getLeft(), padding.getBottom(), padding.getRight());
	}

	public static Cell<?> setSpacing(Padding spacing, Cell<?> cell) {
		return cell.space(spacing.getTop(), spacing.getLeft(), spacing.getBottom(), spacing.getRight());
	}

}
