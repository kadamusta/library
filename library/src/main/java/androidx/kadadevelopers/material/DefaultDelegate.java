package androidx.kadadevelopers.material;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
class DefaultDelegate implements PBDelegate {
    private static final ArgbEvaluator COLOR_EVALUATOR = new ArgbEvaluator();
    private static final Interpolator END_INTERPOLATOR = new LinearInterpolator();
    private ValueAnimator mSweepAppearingAnimator;
    private ValueAnimator mSweepDisappearingAnimator;
    private ValueAnimator mRotationAnimator;
    private ValueAnimator mEndAnimator;
    private boolean mModeAppearing;
    private int mCurrentColor;
    private int mCurrentIndexColor;
    private float mCurrentSweepAngle;
    private float mCurrentRotationAngleOffset = 0.0F;
    private float mCurrentRotationAngle = 0.0F;
    private float mCurrentEndRatio = 1.0F;
    private final Interpolator mAngleInterpolator;
    private final Interpolator mSweepInterpolator;
    private final int[] mColors;
    private final float mSweepSpeed;
    private final float mRotationSpeed;
    private final int mMinSweepAngle;
    private final int mMaxSweepAngle;
    private final CircularProgressDrawable mParent;
    private CircularProgressDrawable.OnEndListener mOnEndListener;

    public DefaultDelegate(@NonNull CircularProgressDrawable parent, @NonNull Options options) {
        this.mParent = parent;
        this.mSweepInterpolator = options.sweepInterpolator;
        this.mAngleInterpolator = options.angleInterpolator;
        this.mColors = options.colors;
        this.mCurrentIndexColor = 0;
        this.mCurrentColor = this.mColors[0];
        this.mSweepSpeed = options.sweepSpeed;
        this.mRotationSpeed = options.rotationSpeed;
        this.mMinSweepAngle = options.minSweepAngle;
        this.mMaxSweepAngle = options.maxSweepAngle;
        this.setupAnimations();
    }

    private void reinitValues() {
        this.mCurrentEndRatio = 1.0F;
        this.mCurrentSweepAngle = this.mMinSweepAngle;
        this.mParent.getCurrentPaint().setColor(this.mCurrentColor);
    }

    private void stopAnimators() {
        this.mRotationAnimator.cancel();
        this.mSweepAppearingAnimator.cancel();
        this.mSweepDisappearingAnimator.cancel();
        this.mEndAnimator.cancel();
    }

    private void setAppearing() {
        this.mModeAppearing = true;
        this.mCurrentRotationAngleOffset += (float) this.mMinSweepAngle;
    }

    private void setDisappearing() {
        this.mModeAppearing = false;
        this.mCurrentRotationAngleOffset += (float) (360 - this.mMaxSweepAngle);
    }

    private void setCurrentRotationAngle(float currentRotationAngle) {
        this.mCurrentRotationAngle = currentRotationAngle;
        this.mParent.invalidate();
    }

    private void setCurrentSweepAngle(float currentSweepAngle) {
        this.mCurrentSweepAngle = currentSweepAngle;
        this.mParent.invalidate();
    }

    private void setEndRatio(float ratio) {
        this.mCurrentEndRatio = ratio;
        this.mParent.invalidate();
    }

    private void setupAnimations() {
        this.mRotationAnimator = ValueAnimator.ofFloat(0.0F, 360.0F);
        this.mRotationAnimator.setInterpolator(this.mAngleInterpolator);
        this.mRotationAnimator.setDuration((long) (2000.0F / this.mRotationSpeed));
        this.mRotationAnimator.addUpdateListener(
        animation -> this.setCurrentRotationAngle(Utils.getAnimatedFraction(animation) * 360.0F));
        this.mRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        this.mRotationAnimator.setRepeatMode(ValueAnimator.RESTART);
        this.mSweepAppearingAnimator = ValueAnimator.ofFloat(this.mMinSweepAngle, this.mMaxSweepAngle);
        this.mSweepAppearingAnimator.setInterpolator(this.mSweepInterpolator);
        this.mSweepAppearingAnimator.setDuration((long) (600.0F / this.mSweepSpeed));
        this.mSweepAppearingAnimator.addUpdateListener(animation -> {
            float fraction = Utils.getAnimatedFraction(animation);
            float value = fraction * (this.mMaxSweepAngle - this.mMinSweepAngle) + this.mMinSweepAngle;
            this.setCurrentSweepAngle(value);
        });
        this.mSweepAppearingAnimator.addListener(new DefaultDelegate$1(this));
        this.mSweepDisappearingAnimator = ValueAnimator.ofFloat(this.mMaxSweepAngle, this.mMinSweepAngle);
        this.mSweepDisappearingAnimator.setInterpolator(this.mSweepInterpolator);
        this.mSweepDisappearingAnimator.setDuration((long) (600.0F / this.mSweepSpeed));
        this.mSweepDisappearingAnimator.addUpdateListener(animation -> {
            float fraction = Utils.getAnimatedFraction(animation);
            this.setCurrentSweepAngle(
                    (float) this.mMaxSweepAngle - fraction * (float) (this.mMaxSweepAngle - this.mMinSweepAngle)
            );

            float playTimeFraction = (float) animation.getCurrentPlayTime() / (float) animation.getDuration();
            if (this.mColors.length > 1 && playTimeFraction > 0.7F) {
                int nextColor = this.mColors[(this.mCurrentIndexColor + 1) % this.mColors.length];
                int blendedColor = (Integer) COLOR_EVALUATOR.evaluate(
                        (playTimeFraction - 0.7F) / 0.3F,
                        this.mCurrentColor,
                        nextColor
                );
                this.mParent.getCurrentPaint().setColor(blendedColor);
            }
        });

        this.mSweepDisappearingAnimator.addListener(new DefaultDelegate$2(this));
        this.mEndAnimator = ValueAnimator.ofFloat(1.0F, 0.0F);
        this.mEndAnimator.setInterpolator(END_INTERPOLATOR);
        this.mEndAnimator.setDuration(200L);
        this.mEndAnimator.addUpdateListener(
                animation -> this.setEndRatio(1.0F - Utils.getAnimatedFraction(animation))
        );
    }

    public void draw(Canvas canvas, Paint paint) {
        float startAngle = this.mCurrentRotationAngle - this.mCurrentRotationAngleOffset;
        float sweepAngle = this.mCurrentSweepAngle;

        if (!this.mModeAppearing) {
            startAngle += 360.0F - sweepAngle;
        }
        startAngle %= 360.0F;

        if (this.mCurrentEndRatio < 1.0F) {
            sweepAngle *= this.mCurrentEndRatio;
            startAngle = (this.mCurrentRotationAngle - this.mCurrentRotationAngleOffset + (this.mCurrentSweepAngle - sweepAngle)) % 360.0F;
        }

        canvas.drawArc(this.mParent.getDrawableBounds(), startAngle, sweepAngle, false, paint);
    }

    public void start() {
        this.mEndAnimator.cancel();
        this.mCurrentRotationAngle = 0f;
        this.mCurrentRotationAngleOffset = 0f;
        this.mModeAppearing = true;
        this.mCurrentSweepAngle = this.mMinSweepAngle;
        this.mCurrentEndRatio = 1.0f;
        this.mCurrentColor = this.mColors[this.mCurrentIndexColor];
        this.mParent.getCurrentPaint().setColor(this.mCurrentColor);

        this.mRotationAnimator.start();
        this.mSweepAppearingAnimator.start();
    }

    public void stop() {
        this.stopAnimators();
    }

    public void progressiveStop(CircularProgressDrawable.OnEndListener listener) {
        if (this.mParent.isRunning() && !this.mEndAnimator.isRunning()) {
            this.mOnEndListener = listener;
            this.mEndAnimator.addListener(new DefaultDelegate$3(this));
            this.mEndAnimator.start();
        }
    }

    private static class DefaultDelegate$1 extends SimpleAnimatorListener {
        private final DefaultDelegate delegate;
        DefaultDelegate$1(DefaultDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            delegate.setDisappearing();
            delegate.mSweepDisappearingAnimator.start();
        }
    }

    private static class DefaultDelegate$2 extends SimpleAnimatorListener {
        private final DefaultDelegate delegate;

        DefaultDelegate$2(DefaultDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            delegate.setAppearing();
            delegate.mCurrentIndexColor = (delegate.mCurrentIndexColor + 1) % delegate.mColors.length;
            delegate.mCurrentColor = delegate.mColors[delegate.mCurrentIndexColor];
            delegate.mParent.getCurrentPaint().setColor(delegate.mCurrentColor);
            delegate.mSweepAppearingAnimator.start();
        }
    }

    private static class DefaultDelegate$3 extends SimpleAnimatorListener {
        private final DefaultDelegate delegate;

        DefaultDelegate$3(DefaultDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onPreAnimationEnd(Animator animation) {
            delegate.mEndAnimator.removeListener(this);
            CircularProgressDrawable.OnEndListener tmpListener = delegate.mOnEndListener;
            delegate.mOnEndListener = null;

            if (isStartedAndNotCancelled()) {
                delegate.setEndRatio(0.0F);
                delegate.mParent.stop();
                if (tmpListener != null) {
                    tmpListener.onEnd(delegate.mParent);
                }
            }
        }
    }
}
