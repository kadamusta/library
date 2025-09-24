package androidx.kadadevelopers.material;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.kadadevelopers.R;

public class ProgressBarView extends View {

    private CircularProgressDrawable mDrawable;
    private boolean autoAnimate = true;
    private boolean isAnimating = false;

    public ProgressBarView(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        int[] colors = new int[]{
                ContextCompat.getColor(context, R.color.color_setting_1),
                ContextCompat.getColor(context, R.color.color_setting_2),
                ContextCompat.getColor(context, R.color.color_setting_3),
                ContextCompat.getColor(context, R.color.color_setting_4),
                ContextCompat.getColor(context, R.color.color_setting_5),
                ContextCompat.getColor(context, R.color.color_setting_6),
                ContextCompat.getColor(context, R.color.color_setting_7)
        };
        Options options = new Options(
        colors,
        10f,
        1.5f,
        1.0f,
        CircularProgressDrawable.STYLE_ROUNDED
        );
        mDrawable = new CircularProgressDrawable(options);
        mDrawable.setCallback(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (autoAnimate) {
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.setBounds(0, 0, getWidth(), getHeight());
        mDrawable.draw(canvas);

        if (mDrawable.isRunning()) {
            invalidate();
        }
    }

    public void start() {
        if (!mDrawable.isRunning()) {
            mDrawable.start();
            isAnimating = true;
            invalidate();
        }
    }

    public void stop() {
        if (mDrawable.isRunning()) {
            mDrawable.stop();
            isAnimating = false;
            invalidate();
        }
    }
}
