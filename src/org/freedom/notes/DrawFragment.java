package org.freedom.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DrawFragment extends AbstractNoteFragment {

	private DrawView drawView;
	private Paint paint;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		drawView = new DrawView(getActivity());
		drawView.setDrawingCacheEnabled(true);
		initPaint();
		return drawView;
	}

	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(0xFFFF0000);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(10);
	}

	public void colorChanged(final int color) {
		paint.setColor(color);
	}

	public class DrawView extends View {

		private Bitmap bitmap;
		private Canvas bitmapCanvas;
		private final List<Path> paths = new ArrayList<>();

		private Path currentPath;
		private Paint mBitmapPaint;

		public DrawView(final Context context) {
			super(context);
			currentPath = new Path();

		}

		@Override
		protected void onSizeChanged(final int w, final int h, final int oldw,
				final int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			bitmapCanvas = new Canvas(bitmap);
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);

		}

		@Override
		protected void onDraw(final Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
			for (Path path : paths) {
				canvas.drawPath(path, paint);
			}
			canvas.drawPath(currentPath, paint);
		}

		private void touch_start(final float x, final float y) {
			currentPath = new Path();
			currentPath.moveTo(x, y);
			return;
		}

		private void touch_move(final float x, final float y) {
			currentPath.lineTo(x, y);
		}

		private void touch_up() {
			paths.add(currentPath);
			if (paths.size() == 2) {
				Path first = paths.remove(0);
				bitmapCanvas.drawPath(first, paint);
			}
		}

		@Override
		public boolean onTouchEvent(final MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}
	}

	@Override
	public boolean allowSwipe() {
		return false;
	}

}
