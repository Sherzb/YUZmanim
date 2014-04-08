package com.example.yuzmanim;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MinyanTable extends TableLayout
{
	public MinyanTable(Context context) {
		super(context);
		
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

		TableLayout tableLayout = new TableLayout(context);
		tableLayout.setLayoutParams(tableParams);

		TableRow tableRow = new TableRow(context);
		tableRow.setLayoutParams(tableParams);

		TextView textView = new TextView(context);
		textView.setLayoutParams(rowParams);
		textView.setText("Hello");

		tableRow.addView(textView);
	}
}
