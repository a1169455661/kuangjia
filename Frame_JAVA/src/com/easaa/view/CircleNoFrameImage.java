package com.easaa.view;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
/**圆形Imageview控件*/
public class CircleNoFrameImage extends MaskedImage {
	public CircleNoFrameImage(Context paramContext) {
		super(paramContext);
	}

	public CircleNoFrameImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CircleNoFrameImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);


		//		/**压缩Bitmap*/
		//		localBitmap = BitmapUtil.compressImage(localBitmap, 100);

		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(localBitmap);
		Canvas localCanvas = new Canvas(weak.get());
		Paint localPaint = new Paint(1);
		localPaint.setColor(-16777216);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
		localCanvas.drawOval(localRectF, localPaint);
		return weak.get();
	}
}
