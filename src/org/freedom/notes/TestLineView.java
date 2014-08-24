package org.freedom.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TestLineView extends View {

	private Paint paint;
	private PointF startPoint, endPoint;
	private boolean isDrawing;

	public TestLineView(final Context context) {
		super(context);
		init();
	}

	public TestLineView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TestLineView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		if (isDrawing) {
			canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y,
					paint);
		}
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startPoint = new PointF(event.getX(), event.getY());
			endPoint = new PointF();
			isDrawing = true;
			break;
		case MotionEvent.ACTION_MOVE:
			if (isDrawing) {
				endPoint.x = event.getX();
				endPoint.y = event.getY();
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isDrawing) {
				endPoint.x = event.getX();
				endPoint.y = event.getY();
				isDrawing = false;
				invalidate();
			}
			break;
		default:
			break;
		}
		return true;
	}
}