package androidx.kadadevelopers.material;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

@Keep
public class ImageHelperView extends AppCompatImageView {

    public static final String TAG = "ImageHelperView";
    public static final float DEFAULT_RADIUS = 0.0f;
    public static final float DEFAULT_BORDER_WIDTH = 0.0f;

    private final float[] mCornerRadii = new float[8];
    private float mBorderWidth = DEFAULT_BORDER_WIDTH;
    private ColorStateList mBorderColor = ColorStateList.valueOf(0xFFFFFFFF);
    private boolean mIsOval = false;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private final RectF mRectF = new RectF();

    public ImageHelperView(Context context) {
        super(context);
        init();
    }

    public ImageHelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageHelperView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setColor(mBorderColor.getDefaultColor());
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        mRectF.set(0, 0, getWidth(), getHeight());
        mPath.reset();

        if (mIsOval) {
            mPath.addOval(mRectF, Path.Direction.CW);
        } else {
            mPath.addRoundRect(mRectF, mCornerRadii, Path.Direction.CW);
        }

        int save = canvas.save();
        canvas.clipPath(mPath);
        super.onDraw(canvas);
        canvas.restoreToCount(save);

        // draw border
        if (mBorderWidth > 0) {
            mBorderPaint.setStrokeWidth(mBorderWidth);
            mBorderPaint.setColor(mBorderColor.getDefaultColor());
            canvas.drawPath(mPath, mBorderPaint);
        }
    }

    // === Corner Radius ===
    public void setCornerRadius(float radius) {
        for (int i = 0; i < mCornerRadii.length; i++) {
            mCornerRadii[i] = radius;
        }
        invalidate();
    }

    public void setCornerRadius(int corner, float radius) {
        if (corner >= 0 && corner < 4) {
            mCornerRadii[corner * 2] = radius;
            mCornerRadii[corner * 2 + 1] = radius;
            invalidate();
        }
    }

    public void setCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        mCornerRadii[0] = mCornerRadii[1] = topLeft;
        mCornerRadii[2] = mCornerRadii[3] = topRight;
        mCornerRadii[4] = mCornerRadii[5] = bottomRight;
        mCornerRadii[6] = mCornerRadii[7] = bottomLeft;
        invalidate();
    }

    // === Border ===
    public void setBorderWidth(float width) {
        mBorderWidth = width;
        invalidate();
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderColor(@ColorInt int color) {
        mBorderColor = ColorStateList.valueOf(color);
        invalidate();
    }

    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public void setBorderColor(ColorStateList colors) {
        if (colors != null) {
            mBorderColor = colors;
            invalidate();
        }
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    // === Oval ===
    public boolean isOval() {
        return mIsOval;
    }

    public void setOval(boolean oval) {
        mIsOval = oval;
        invalidate();
    }

    // === Override for image sources ===
    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        invalidate();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        invalidate();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        invalidate();
    }

    // === Optional Dimen setters ===
    public void setCornerRadiusDimen(@DimenRes int resId) {
        float radius = getResources().getDimension(resId);
        setCornerRadius(radius);
    }

    public void setBorderWidth(@DimenRes int resId) {
        float width = getResources().getDimension(resId);
        setBorderWidth(width);
    }
}
