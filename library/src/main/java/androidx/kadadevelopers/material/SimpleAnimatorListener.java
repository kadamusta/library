package androidx.kadadevelopers.material;

import android.animation.Animator;

import androidx.annotation.NonNull;

abstract class SimpleAnimatorListener implements Animator.AnimatorListener {
    private boolean mStarted = false;
    private boolean mCancelled = false;

    @Override
    public void onAnimationStart(@NonNull Animator animation) {
        mStarted = true;
        mCancelled = false;
    }

    @Override
    public void onAnimationCancel(@NonNull Animator animation) {
        mCancelled = true;
    }

    @Override
    public void onAnimationEnd(@NonNull Animator animation) {
        onPreAnimationEnd(animation);
    }

    @Override
    public void onAnimationRepeat(@NonNull Animator animation) {
    }
    public void onPreAnimationEnd(Animator animation) {
    }

    public boolean isStartedAndNotCancelled() {
        return mStarted && !mCancelled;
    }
}
