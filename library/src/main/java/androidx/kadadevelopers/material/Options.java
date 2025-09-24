package androidx.kadadevelopers.material;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Keep;

@Keep
public class Options {

    public Interpolator angleInterpolator;
    public Interpolator sweepInterpolator;
    public float borderWidth;
    public int[] colors;
    public float sweepSpeed;
    public float rotationSpeed;
    public int minSweepAngle;
    public int maxSweepAngle;
    public int style;

    // Constructor الرئيسي
    public Options(Interpolator angleInterpolator,
                   Interpolator sweepInterpolator,
                   float borderWidth,
                   int[] colors,
                   float sweepSpeed,
                   float rotationSpeed,
                   int minSweepAngle,
                   int maxSweepAngle,
                   int style) {

        this.angleInterpolator = angleInterpolator;
        this.sweepInterpolator = sweepInterpolator;
        this.borderWidth = borderWidth;
        this.colors = colors;
        this.sweepSpeed = sweepSpeed;
        this.rotationSpeed = rotationSpeed;
        this.minSweepAngle = minSweepAngle;
        this.maxSweepAngle = maxSweepAngle;
        this.style = style;
    }

    // Constructor مساعد
    public Options(int[] colors, float borderWidth, float sweepSpeed, float rotationSpeed, int style) {
        this(
                new LinearInterpolator(),
                new LinearInterpolator(),
                borderWidth,
                colors,
                sweepSpeed,
                rotationSpeed,
                20,
                300,
                style
        );
    }
}
