package androidx.kadadevelopers.material;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Keep
public class CircularProgressDrawable extends Drawable implements Runnable {

    public static final int STYLE_ROUNDED = 1;
    private final Paint mPaint;
    private final RectF mDrawableBounds = new RectF();
    private final PBDelegate mDelegate;
    private boolean mRunning = false;
    public interface OnEndListener {
        void onEnd(CircularProgressDrawable drawable);
    }

    public CircularProgressDrawable(@NonNull Options options) {
        mDelegate = new DefaultDelegate(this, options);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(options.borderWidth);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mDelegate.draw(canvas, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable android.graphics.ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }

    @Override
    protected void onBoundsChange(@NonNull android.graphics.Rect bounds) {
        super.onBoundsChange(bounds);
        float inset = mPaint.getStrokeWidth() / 2f;
        mDrawableBounds.set(bounds.left + inset, bounds.top + inset,
                bounds.right - inset, bounds.bottom - inset);
    }

    public RectF getDrawableBounds() {
        return mDrawableBounds;
    }

    public Paint getCurrentPaint() {
        return mPaint;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public void start() {
        if (!isRunning()) {
            mRunning = true;
            mDelegate.start();
            scheduleSelf(this, System.currentTimeMillis() + 16L);
        }
    }

    public void invalidate() {
        invalidateSelf();
    }

    public void stop() {
        if (isRunning()) {
            mRunning = false;
            mDelegate.stop();
            unscheduleSelf(this);
        }
    }

    @Override
    public void run() {
        if (mRunning) {
            invalidateSelf();
            scheduleSelf(this, System.currentTimeMillis() + 16L);
        }
    }
}
